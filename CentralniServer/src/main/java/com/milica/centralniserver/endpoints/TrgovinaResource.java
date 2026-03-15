package com.milica.centralniserver.endpoints;

import com.milica.centralniserver.AuthHelper;
import com.milica.centralniserver.JMSHelper;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("trgovina")
public class TrgovinaResource {
    @Resource(lookup = "jms/podsistem1ConnectionFactory")
    private ConnectionFactory cf1;
    
    @Resource(lookup = "jms/podsistem1Queue")
    private Queue q1;

    @Resource(lookup = "jms/podsistem2ConnectionFactory")
    private ConnectionFactory cf2;
    
    @Resource(lookup = "jms/podsistem2Queue")
    private Queue q2;
    
    @Resource(lookup = "jms/podsistem3ConnectionFactory")
    private ConnectionFactory cf3;

    @Resource(lookup = "jms/podsistem3Queue")
    private Queue q3;
    
    private String dohvatiIdKorisnika(String authHeader) {
        if (authHeader == null) return "-1";
        String[] k = AuthHelper.parseBasicAuth(authHeader);
        if (k == null) return "-1";
        String rezultat = JMSHelper.posaljiIcekaj(cf3, q3,
            "DOHVATI_ID_KORISNIKA|" + k[0]);
        return rezultat;
    }
    
    @POST
    @Path("placanje")
    @Produces(MediaType.APPLICATION_JSON)
    public Response placanje(
            @FormParam("adresa") String adresa,
            @FormParam("gradZaDostavu") String gradZaDostavu,
            @Context HttpHeaders headers) {

        String authHeader = headers.getHeaderString("Authorization");
        String[] k = AuthHelper.parseBasicAuth(authHeader);
        String idKorisnika = dohvatiIdKorisnika(authHeader);
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String korpa = JMSHelper.posaljiIcekaj(cf2, q2, "DOHVATI_KORPU|" + idKorisnika);
        if(korpa == null || korpa.startsWith("GRESKA"))
            return Response.status(500).entity("Greska pri dohvatanju korpe").build();

        String narudzbina = JMSHelper.posaljiIcekaj(cf3, q3, "KREIRAJ_NARUDZBINU|" + idKorisnika + "|" + adresa + "|" + gradZaDostavu + "|" + korpa);
        if(narudzbina == null || narudzbina.startsWith("GRESKA"))
            return Response.status(500).entity("Greska pri kreiranju narudzbine").build();

        BigDecimal ukupnaCena = new BigDecimal(narudzbina.split("\"ukupnaCena\":")[1].split(",")[0].trim());
        String idNarudzbine = narudzbina.split("\"idNarudzbine\":")[1].split(",")[0].trim();

        String korisnikJson = JMSHelper.posaljiIcekaj(cf1, q1, "PROVERA_KORISNIKA|" + k[0] + "|" + k[1]);
        BigDecimal stanjeNovca = new BigDecimal(korisnikJson.split("\"stanjeNovca\":")[1].split(",")[0].trim());

        if(stanjeNovca.compareTo(ukupnaCena) < 0)
            return Response.status(400).entity("Nemate dovoljno sredstava. Narudzbina je sacuvana.").build();

        String transakcija = JMSHelper.posaljiIcekaj(cf3, q3, "NAPRAVI_TRANSAKCIJU|" + idNarudzbine + "|" + ukupnaCena);
        if(transakcija == null || transakcija.startsWith("GRESKA"))
            return Response.status(500).entity("Greska pri kreiranju transakcije").build();
        
        JMSHelper.posaljiIcekaj(cf1, q1, "DODAJ_NOVAC|" + k[0] + "|" + ukupnaCena.negate());

        JMSHelper.posaljiIcekaj(cf2, q2, "ISPRAZNI_KORPU|" + idKorisnika);

        return Response.ok(transakcija).build();
    }
    
    @GET
    @Path("narudzbine/korisnik")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiNarudzbineKorisnika(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf3, q3, "DOHVATI_NARUDZBINE_KORISNIKA|" + idKorisnika);
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Path("narudzbine")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiSveNarudzbine(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf3, q3, "DOHVATI_NARUDZBINE");
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Path("transakcije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiSveTransakcije(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf3, q3, "DOHVATI_TRANSAKCIJE");
        return Response.ok(rezultat).build();
    }
}
