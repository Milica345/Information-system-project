package com.milica.centralniserver.endpoints;

import com.milica.centralniserver.AuthHelper;
import com.milica.centralniserver.JMSHelper;
import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.jms.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("artikli")
public class ArtikliResource{

    @Resource(lookup = "jms/podsistem2ConnectionFactory")
    private ConnectionFactory cf2;

    @Resource(lookup = "jms/podsistem2Queue")
    private Queue q2;

    private String dohvatiIdKorisnika(String authHeader) {
        if (authHeader == null) return "-1";
        String[] k = AuthHelper.parseBasicAuth(authHeader);
        if (k == null) return "-1";
        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2,
            "DOHVATI_ID_KORISNIKA|" + k[0]);
        return rezultat;
    }
    
    @POST
    @Path("kategorije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response kreirajKategoriju(
            @FormParam("naziv") String naziv,
            @FormParam("idNadkategorije") int idNadkategorije,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "KREIRAJ_KATEGORIJU|" + naziv + "|" + idNadkategorije);
        return Response.ok(rezultat).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response kreirajArtikal(
            @FormParam("naziv") String naziv,
            @FormParam("opis") String opis,
            @FormParam("cena") BigDecimal cena,
            @FormParam("popust") BigDecimal popust,
            @FormParam("idKategorije") int idKategorije,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "KREIRAJ_ARTIKAL|" + naziv + "|" + opis + "|" + cena + "|" + popust + "|" + idKategorije + "|" + idKorisnika);
        return Response.ok(rezultat).build();
    }
    
    @PUT
    @Path("cena")
    @Produces(MediaType.APPLICATION_JSON)
    public Response promeniCenu(
            @FormParam("idArtikla") int idArtikla,
            @FormParam("cena") BigDecimal cena,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "PROMENI_CENU|" + idArtikla + "|" + cena + "|" + idKorisnika);
        if (rezultat == null || rezultat.startsWith("GRESKA"))
            return Response.status(400).entity(rezultat).build();
        return Response.ok(rezultat).build();
    }
    
    @PUT
    @Path("popust")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postaviPopust(
            @FormParam("idArtikla") int idArtikla,
            @FormParam("popust") BigDecimal popust,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "POSTAVI_POPUST|" + idArtikla + "|" + popust + "|" + idKorisnika);
        if (rezultat == null || rezultat.startsWith("GRESKA"))
            return Response.status(400).entity(rezultat).build();
        return Response.ok(rezultat).build();
    }
    
    @POST
    @Path("korpa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dodajArtikalUKorupu(
            @FormParam("idArtikla") int idArtikla,
            @FormParam("kolicina") int kolicina,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DODAJ_U_KORPU|" + idKorisnika + "|" + idArtikla + "|" + kolicina);
        return Response.ok(rezultat).build();
    }
    
    @DELETE
    @Path("korpa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obrisiArtikalIzKorpe(
            @QueryParam("idArtikla") int idArtikla,
            @QueryParam("kolicina") int kolicina,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "OBRISI_IZ_KORPE|" + idKorisnika + "|" + idArtikla + "|" + kolicina);
        return Response.ok(rezultat).build();
    }
    
    @POST
    @Path("lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dodajArtikalUListuZelja(
            @FormParam("idArtikla") int idArtikla,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DODAJ_U_LISTU_ZELJA|" + idKorisnika + "|" + idArtikla);
        return Response.ok(rezultat).build();
    }
    
    @DELETE
    @Path("lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obrisiArtikalIzListeZelja(
            @QueryParam("idArtikla") int idArtikla,
            @Context HttpHeaders headers) {

        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "OBRISI_IZ_LISTE_ZELJA|" + idKorisnika + "|" + idArtikla);
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Path("kategorije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiKategorije(@Context HttpHeaders headers) {

        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DOHVATI_KATEGORIJE");
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiArtikleKorisnika(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DOHVATI_ARTIKLE_KORISNIKA|" + idKorisnika);
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Path("korpa")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiSadrzajKorpeKorisnika(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DOHVATI_KORPU|" + idKorisnika);
        return Response.ok(rezultat).build();
    }
    
    @GET
    @Path("lista")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiSadrzajListeZeljaKorisnika(@Context HttpHeaders headers) {
        String idKorisnika = dohvatiIdKorisnika(headers.getHeaderString("Authorization"));
        int id = Integer.parseInt(idKorisnika);
        if(id == -1)
            return Response.status(401).entity("Morate biti prijavljeni").build();
       
        String rezultat = JMSHelper.posaljiIcekaj(cf2, q2, "DOHVATI_LISTU_ZELJA|" + idKorisnika);
        return Response.ok(rezultat).build();
    }
}