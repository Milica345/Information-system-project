package servis;

import entiteti.Grad;
import entiteti.ImaUlogu;
import entiteti.Korisnik;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

public class Podsistem1Servis {
    
    private EntityManagerFactory emf;

    public Podsistem1Servis(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private String korisnikUJson(Korisnik k, EntityManager em) {
        List<ImaUlogu> imaUloguList = em.createQuery(
            "SELECT u FROM ImaUlogu u WHERE u.idKorisnika.idKorisnika = :id", ImaUlogu.class)
            .setParameter("id", k.getIdKorisnika())
            .getResultList();

        StringBuilder uloge = new StringBuilder("[");
        if (imaUloguList != null) {
            for (int i = 0; i < imaUloguList.size(); i++) {
                uloge.append("\"").append(imaUloguList.get(i).getIdUloge().getNaziv()).append("\"");
                if (i < imaUloguList.size() - 1)
                    uloge.append(",");
            }
        }
        uloge.append("]");

        Grad grad = em.find(Grad.class, k.getIdGrada().getIdGrada());

        return "{" +
            "\"idKorisnika\":" + k.getIdKorisnika() + "," +
            "\"korisnickoIme\":\"" + k.getKorisnickoIme() + "\"," +
            "\"ime\":\"" + k.getIme() + "\"," +
            "\"prezime\":\"" + k.getPrezime() + "\"," +
            "\"adresa\":\"" + k.getAdresa() + "\"," +
            "\"stanjeNovca\":" + k.getStanjeNovca() + "," +
            "\"idGrada\":" + (grad != null ? grad.getIdGrada() : "null") + "," +
            "\"grad\":\"" + (grad != null ? grad.getNaziv() : "") + "\"," +
            "\"uloge\":" + uloge +
            "}";
    }
    

    public String proveraKorisnika(String korisnickoIme, String sifra) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.createQuery(
                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime AND k.sifra = :sifra",
                Korisnik.class)
                .setParameter("ime", korisnickoIme)
                .setParameter("sifra", sifra)
                .getSingleResult();

            return korisnikUJson(k, em);
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public String kreirajGrad(String naziv) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Grad g = new Grad();
            g.setNaziv(naziv);
            em.persist(g);

            em.getTransaction().commit();
            return "{\"idGrada\":" + g.getIdGrada() + ",\"naziv\":\"" + g.getNaziv() + "\"}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    public String kreirajKorisnika(String korisnickoIme, String sifra, String ime, String prezime, String adresa, int idGrada) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Grad grad = em.find(Grad.class, idGrada);
            if (grad == null) {
                throw new IllegalArgumentException("Grad sa ID " + idGrada + " ne postoji.");
            }

            Korisnik k = new Korisnik();
            k.setKorisnickoIme(korisnickoIme);
            k.setSifra(sifra);
            k.setIme(ime);
            k.setPrezime(prezime);
            k.setAdresa(adresa);
            k.setIdGrada(grad);                     
            k.setStanjeNovca(BigDecimal.ZERO);
            em.persist(k);

            em.getTransaction().commit();
            return korisnikUJson(k, em);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    public String dodajNovac(String korisnickoIme, BigDecimal iznos) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.createQuery(
                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime",
                Korisnik.class)
                .setParameter("ime", korisnickoIme)
                .getSingleResult();

            k.setStanjeNovca(k.getStanjeNovca().add(iznos));
            em.merge(k);

            em.getTransaction().commit();
            return korisnikUJson(k, em);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String promeniAdresuIGrad(String korisnickoIme, String adresa, int idGrada) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            Grad grad = em.find(Grad.class, idGrada);
            if (grad == null) {
                throw new IllegalArgumentException("Grad sa ID " + idGrada + " ne postoji.");
            }

            Korisnik k = em.createQuery(
                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime",
                Korisnik.class)
                .setParameter("ime", korisnickoIme)
                .getSingleResult();

            k.setAdresa(adresa);
            k.setIdGrada(grad);
            em.merge(k);

            em.getTransaction().commit();
            return korisnikUJson(k, em);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String dohvatiSveGradove() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Grad> gradovi = em.createQuery("SELECT g FROM Grad g", Grad.class).getResultList();
            
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < gradovi.size(); i++) {
                Grad g = gradovi.get(i);
                sb.append("{\"idGrada\":").append(g.getIdGrada())
                  .append(",\"naziv\":\"").append(g.getNaziv()).append("\"}");
                if (i < gradovi.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }
    
    public String dohvatiSveKorisnike() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Korisnik> korisnici = em.createQuery("SELECT k FROM Korisnik k", Korisnik.class).getResultList();
            
            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < korisnici.size(); i++) {
                sb.append(korisnikUJson(korisnici.get(i), em));
                if (i < korisnici.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }
}