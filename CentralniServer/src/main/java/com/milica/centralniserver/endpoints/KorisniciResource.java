package com.milica.centralniserver.endpoints;

import com.milica.centralniserver.AuthHelper;
import com.milica.centralniserver.JMSHelper;
import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("korisnici")
public class KorisniciResource {

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

    private boolean jeAdmin(String authHeader) {
        if (authHeader == null) return false;
        String[] k = AuthHelper.parseBasicAuth(authHeader);
        if (k == null) return false;
        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1,
            "PROVERA_KORISNIKA|" + k[0] + "|" + k[1]);
        return rezultat != null && rezultat.contains("\"administrator\"");
    }

    @GET
    @Path("prijava")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prijava(@Context HttpHeaders headers) {
        String authHeader = headers.getHeaderString("Authorization");
        String[] k = AuthHelper.parseBasicAuth(authHeader);
        if (k == null)
            return Response.status(401).entity("Nedostaju kredencijali").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1,
            "PROVERA_KORISNIKA|" + k[0] + "|" + k[1]);

        if (rezultat == null || rezultat.startsWith("GRESKA") || rezultat.equals("NEPOSTOJI"))
            return Response.status(401).entity("Pogresno korisnicko ime ili sifra").build();

        return Response.ok(rezultat).build();
    }

    @POST
    @Path("gradovi")
    @Produces(MediaType.APPLICATION_JSON)
    public Response kreirajGrad(
            @FormParam("naziv") String naziv,
            @Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1, "KREIRAJ_GRAD|" + naziv);
        return Response.ok(rezultat).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response kreirajKorisnika(
            @FormParam("korisnickoIme") String korisnickoIme,
            @FormParam("sifra") String sifra,
            @FormParam("ime") String ime,
            @FormParam("prezime") String prezime,
            @FormParam("adresa") String adresa,
            @FormParam("idGrada") int idGrada,
            @Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1,
            "KREIRAJ_KORISNIKA|" + korisnickoIme + "|" + sifra + "|" +
            ime + "|" + prezime + "|" + adresa + "|" + idGrada);
        
        if (rezultat != null && !rezultat.startsWith("GRESKA")) {
            JMSHelper.posaljiIcekaj(cf2, q2, "DODAJ_KORISNIKA|" + korisnickoIme);
            JMSHelper.posaljiIcekaj(cf3, q3, "DODAJ_KORISNIKA|" + korisnickoIme);
        }
        
        return Response.ok(rezultat).build();
    }

    @PUT
    @Path("novac")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dodajNovac(
            @FormParam("korisnickoIme") String korisnickoIme,
            @FormParam("iznos") String iznos,
            @Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1,
            "DODAJ_NOVAC|" + korisnickoIme + "|" + iznos);
        return Response.ok(rezultat).build();
    }

    @PUT
    @Path("adresa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response promeniAdresu(
            @FormParam("korisnickoIme") String korisnickoIme,
            @FormParam("adresa") String adresa,
            @FormParam("idGrada") int idGrada,
            @Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1,
            "PROMENI_ADRESU|" + korisnickoIme + "|" + adresa + "|" + idGrada);
        return Response.ok(rezultat).build();
    }

    @GET
    @Path("gradovi")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiGradove(@Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1, "DOHVATI_GRADOVE");
        return Response.ok(rezultat).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiKorisnike(@Context HttpHeaders headers) {

        if (!jeAdmin(headers.getHeaderString("Authorization")))
            return Response.status(403).entity("Potrebne admin privilegije").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf1, q1, "DOHVATI_KORISNIKE");
        return Response.ok(rezultat).build();
    }
}