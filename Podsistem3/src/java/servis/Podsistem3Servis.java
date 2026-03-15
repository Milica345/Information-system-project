package servis;

import entiteti.Korisnik;
import entiteti.Narudzbina;
import entiteti.Stavka;
import entiteti.Transakcija;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;


public class Podsistem3Servis {
    private EntityManagerFactory emf;

    public Podsistem3Servis(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private String transakcijaUJson(Transakcija t, EntityManager em) {
        Narudzbina n = em.find(Narudzbina.class, t.getIdNarudzbine().getIdNarudzbine());
        return "{" +
            "\"idTransakcije\":" + t.getIdTransakcije() + "," +
            "\"suma\":" + t.getSuma() + "," +
            "\"vremePlacanja\":\"" + t.getVremePlacanja() + "\"," +
            "\"idNarudzbine\":" + (n != null ? n.getIdNarudzbine() : "null") +
            "}";
    }
    
    private String narudzbinaUJson(Narudzbina n, EntityManager em) {
        Korisnik k = em.find(Korisnik.class, n.getIdKorisnika().getIdKorisnika());
        return "{" +
            "\"idNarudzbine\":" + n.getIdNarudzbine() + "," +
            "\"ukupnaCena\":" + n.getUkupnaCena() + "," +
            "\"vremeKreiranja\":\"" + n.getVremeKreiranja() + "\"," +
            "\"adresa\":\"" + n.getAdresa() + "\"," +
            "\"gradZaDostavu\":\"" + n.getGradZaDostavu() + "\"," +
            "\"idKorisnika\":" + (k != null ? k.getIdKorisnika() : "null") +
            "}";
    }
    
    public int dohvatiIdKorisnika(String korisnickoIme) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.createQuery(
                "SELECT k FROM Korisnik k WHERE k.korisnickoIme = :ime",
                Korisnik.class)
                .setParameter("ime", korisnickoIme)
                .getSingleResult();
            return k.getIdKorisnika();
        } catch (Exception e) {
            return -1;
        } finally {
            em.close();
        }
    } 
    
    public String dohvatiSveNarudzbineKorisnika(int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<Narudzbina> narudzbine = em.createQuery(
                "SELECT n FROM Narudzbina n WHERE n.idKorisnika = :id", Narudzbina.class)
                .setParameter("id", k)
                .getResultList();

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < narudzbine.size(); i++) {
                sb.append(narudzbinaUJson(narudzbine.get(i), em));
                if (i < narudzbine.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }
    
    public String dohvatiSveNarudzbine() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Narudzbina> narudzbine = em.createQuery("SELECT n FROM Narudzbina n", Narudzbina.class).getResultList();

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < narudzbine.size(); i++) {
                sb.append(narudzbinaUJson(narudzbine.get(i), em));
                if (i < narudzbine.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }    
    
    public String dohvatiSveTransakcije() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Transakcija> transakcije = em.createQuery("SELECT t FROM Transakcija t", Transakcija.class).getResultList();

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < transakcije.size(); i++) {
                sb.append(transakcijaUJson(transakcije.get(i), em));
                if (i < transakcije.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }
    
    public String kreirajNarudzbinu(int idKorisnika, String adresa, String gradZaDostavu, String stavkeJson) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            Narudzbina narudzbina = new Narudzbina();
            narudzbina.setIdKorisnika(k);
            narudzbina.setAdresa(adresa);
            narudzbina.setGradZaDostavu(gradZaDostavu);
            narudzbina.setVremeKreiranja(new java.util.Date());
            narudzbina.setUkupnaCena(BigDecimal.ZERO);
            em.persist(narudzbina);
            em.flush();

            BigDecimal ukupnaCena = BigDecimal.ZERO;
            stavkeJson = stavkeJson.trim().replaceAll("[\\[\\]]", "");
            String[] stavkeNiz = stavkeJson.split("\\},\\{");

            for (String stavkaStr : stavkeNiz) {
                stavkaStr = stavkaStr.replaceAll("[\\{\\}]", "");
                String[] polja = stavkaStr.split(",");

                int idArtikla = 0;
                int kolicina = 0;
                BigDecimal jedinicnaCena = BigDecimal.ZERO;
                BigDecimal cenaa = BigDecimal.ZERO;
                int idProdavca = -1;

                for (String polje : polja) {
                    String[] kv = polje.split(":");
                    String kljuc = kv[0].trim().replaceAll("\"", "");
                    String vrednost = kv[1].trim().replaceAll("\"", "");

                    switch (kljuc) {
                        case "idArtikla":
                            idArtikla = Integer.parseInt(vrednost);
                            break;
                        case "kolicina":
                            kolicina = Integer.parseInt(vrednost);
                            break;
                        case "cena":
                            cenaa = new BigDecimal(vrednost);
                            break;
                        case "cenaSaPopustom":
                            jedinicnaCena = new BigDecimal(vrednost);
                            break;
                        case "idProdavca":
                            idProdavca = Integer.parseInt(vrednost);
                            break;
                    }
                }

                Stavka stavka = new Stavka();
                stavka.setIdNarudzbine(narudzbina);
                stavka.setIdArtikla(idArtikla);
                stavka.setKolicinaArtikla(kolicina);
                stavka.setJedinicnaCena(jedinicnaCena);

                if (idProdavca != -1) {
                    Korisnik prodavac = em.find(Korisnik.class, idProdavca);
                    stavka.setIdProdavca(prodavac);
                }

                em.persist(stavka);
                ukupnaCena = ukupnaCena.add(jedinicnaCena.multiply(new BigDecimal(kolicina)));
            }

            narudzbina.setUkupnaCena(ukupnaCena);
            em.getTransaction().commit();
            return narudzbinaUJson(narudzbina, em);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String napraviTransakciju(int idNarudzbine, BigDecimal suma) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Narudzbina n = em.find(Narudzbina.class, idNarudzbine);
            if (n == null) {
                throw new IllegalArgumentException("Narudzbina sa ID " + idNarudzbine + " ne postoji.");
            }
           
            Transakcija transakcija = new Transakcija();
            transakcija.setIdNarudzbine(n);
            transakcija.setSuma(suma);
            transakcija.setVremePlacanja(new java.util.Date());
            em.persist(transakcija);

            em.getTransaction().commit();
            return transakcijaUJson(transakcija, em);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    public String dodajKorisnika(String korisnickoIme) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Korisnik k = new Korisnik();
            k.setKorisnickoIme(korisnickoIme);
            em.persist(k);
            em.getTransaction().commit();
            return "OK";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }    
}