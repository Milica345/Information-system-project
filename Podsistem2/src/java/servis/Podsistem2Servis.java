package servis;

import entiteti.Artikal;
import entiteti.Kategorija;
import entiteti.Korisnik;
import entiteti.Korpa;
import entiteti.KorpaSadrzi;
import entiteti.KorpaSadrziPK;
import entiteti.ListaZelja;
import entiteti.NaListi;
import entiteti.NaListiPK;
import java.math.BigDecimal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class Podsistem2Servis {
    private EntityManagerFactory emf;

    public Podsistem2Servis(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    private String artikalUJson(Artikal a) {
        return "{" +
            "\"idArtikla\":" + a.getIdArtikla() + "," +
            "\"naziv\":\"" + a.getNaziv() + "\"," +
            "\"opis\":\"" + a.getOpis() + "\"," +
            "\"cena\":" + a.getCena() + "," +
            "\"popustUProcentima\":" + a.getPopustUProcentima() + "," +
            "\"idKategorije\":" + (a.getIdKategorije() != null ? a.getIdKategorije().getIdKategorije() : "null") + "," +
            "\"kategorija\":\"" + (a.getIdKategorije() != null ? a.getIdKategorije().getNaziv() : "") + "\"," +
            "\"idProizvodjaca\":" + a.getIdProizvodjaca().getIdKorisnika() + "," +
            "\"proizvodjac\":\"" + a.getIdProizvodjaca().getKorisnickoIme() + "\"" +
            "}";
    }
    
    public String ispraznIKorpu(int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korpa korpa = em.createQuery(
                "SELECT k FROM Korpa k WHERE k.idKorisnika.idKorisnika = :id", Korpa.class)
                .setParameter("id", idKorisnika)
                .getSingleResult();

            em.createQuery("DELETE FROM KorpaSadrzi ks WHERE ks.korpa = :korpa")
                .setParameter("korpa", korpa)
                .executeUpdate();
            
            // Bulk delete zaobilazi L2 keš — mora se rucno invalidovati
            emf.getCache().evict(KorpaSadrzi.class);

            korpa.setUkupnaCena(BigDecimal.ZERO);
            em.merge(korpa);

            em.getTransaction().commit();
            return "OK";
        } catch (Exception e) {
            em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
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
    
    public String kreirajKategoriju(String naziv, int idNadkategorije) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            Kategorija kat = new Kategorija();
            
            if(idNadkategorije != -1){
                Kategorija nk = em.find(Kategorija.class, idNadkategorije);
                if (nk == null) {
                    throw new IllegalArgumentException("Nadkategorija sa ID " + idNadkategorije + " ne postoji.");
                }
                kat.setIdNadkategorije(nk);
            } else {
                kat.setIdNadkategorije(null);
            }

            kat.setNaziv(naziv);
            
            em.persist(kat);

            em.getTransaction().commit();
            return "{\"idKategorije\":" + kat.getIdKategorije() + ",\"naziv\":\"" + kat.getNaziv() + "\"" + ",\"idNadkategorije\":" + (kat.getIdNadkategorije() != null ? kat.getIdNadkategorije().getIdKategorije() : "null") + "}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String kreirajArtikal(String naziv, String opis, BigDecimal cena, BigDecimal popustUProcentima, int idKategorije, int idProizvodjaca) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            if (cena.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Cena mora biti veca od 0.");
            }

            if (popustUProcentima.compareTo(BigDecimal.ZERO) < 0 ||
                popustUProcentima.compareTo(new BigDecimal(100)) > 0) {
                throw new IllegalArgumentException("Popust mora biti izmedju 0 i 100.");
            }

            Kategorija kat = em.find(Kategorija.class, idKategorije);
            if (kat == null) {
                throw new IllegalArgumentException("Kategorija sa ID " + idKategorije + " ne postoji.");
            }
            
            Korisnik proizvo = em.find(Korisnik.class, idProizvodjaca);
            if (proizvo == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idProizvodjaca + " ne postoji.");
            }
            
            Artikal a = new Artikal();
            a.setCena(cena);
            a.setNaziv(naziv);
            a.setOpis(opis);
            a.setPopustUProcentima(popustUProcentima);
            a.setIdProizvodjaca(proizvo);
            a.setIdKategorije(kat);
          
            em.persist(a);

            em.getTransaction().commit();
            return artikalUJson(a);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String promeniCenuArtikla(int idArtikla, BigDecimal cena, int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Artikal art = em.find(Artikal.class, idArtikla);
            if (art == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " ne postoji.");
            }

            if (art.getIdProizvodjaca().getIdKorisnika() != idKorisnika) {
                throw new IllegalArgumentException("Nemate pravo da menjate cenu ovog artikla.");
            }

            if (cena.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Cena mora biti veca od 0.");
            }

            art.setCena(cena); 
            em.getTransaction().commit();
            return artikalUJson(art);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String postaviPopust(int idArtikla, BigDecimal popustUProcentima, int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Artikal art = em.find(Artikal.class, idArtikla);
            if (art == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " ne postoji.");
            }

            if (art.getIdProizvodjaca().getIdKorisnika() != idKorisnika) {
                throw new IllegalArgumentException("Nemate pravo da postavljate popust na ovaj artikal.");
            }

            if (popustUProcentima.compareTo(BigDecimal.ZERO) < 0 ||
                popustUProcentima.compareTo(new BigDecimal(100)) > 0) {
                throw new IllegalArgumentException("Popust mora biti izmedju 0 i 100.");
            }

            art.setPopustUProcentima(popustUProcentima);
            em.getTransaction().commit();
            return artikalUJson(art);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    
    public String dodajUKorpu(int idKorisnika, int idArtikla, int kolicina) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            Artikal a = em.find(Artikal.class, idArtikla);
            if (a == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " ne postoji.");
            }

            List<Korpa> korpaResult = em.createQuery(
                "SELECT k2 FROM Korpa k2 WHERE k2.idKorisnika.idKorisnika = :id", Korpa.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            Korpa korpa = korpaResult.isEmpty() ? null : korpaResult.get(0);
            if (korpa == null) {
                korpa = new Korpa();
                korpa.setIdKorisnika(k);
                korpa.setUkupnaCena(BigDecimal.ZERO);
                em.persist(korpa);
                em.flush();
            }

            KorpaSadrziPK pk = new KorpaSadrziPK(korpa.getIdKorpe(), idArtikla);
            KorpaSadrzi ks = em.find(KorpaSadrzi.class, pk);

            if (ks != null) {
                ks.setKolicina(ks.getKolicina() + kolicina);
            } else {
                ks = new KorpaSadrzi(pk, kolicina);
                em.persist(ks);
            }

//            BigDecimal cenaArtikla = a.getCena();
//            korpa.setUkupnaCena(korpa.getUkupnaCena().add(cenaArtikla.multiply(new BigDecimal(kolicina))));
            BigDecimal cenaArtikla = a.getCena();
            BigDecimal popust = a.getPopustUProcentima();
            BigDecimal cenaSaPopustom = cenaArtikla.multiply(BigDecimal.ONE.subtract(popust.divide(new BigDecimal(100))));
            korpa.setUkupnaCena(korpa.getUkupnaCena().add(cenaSaPopustom.multiply(new BigDecimal(kolicina))));

            em.getTransaction().commit();
            return "{\"status\":\"ok\",\"idKorpe\":" + korpa.getIdKorpe() + ",\"ukupnaCena\":" + korpa.getUkupnaCena() + "}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    
    public String obrisiIzKorpe(int idKorisnika, int idArtikla, int kolicina) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<Korpa> korpaResult = em.createQuery(
                "SELECT k2 FROM Korpa k2 WHERE k2.idKorisnika.idKorisnika = :id", Korpa.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            Korpa korpa = korpaResult.isEmpty() ? null : korpaResult.get(0);
            if (korpa == null) {
                throw new IllegalArgumentException("Korisnik nema korpu.");
            }

            KorpaSadrziPK pk = new KorpaSadrziPK(korpa.getIdKorpe(), idArtikla);
            KorpaSadrzi ks = em.find(KorpaSadrzi.class, pk);
            if (ks == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " nije u korpi.");
            }

            Artikal a = em.find(Artikal.class, idArtikla);
//            BigDecimal cenaArtikla = a.getCena();
            BigDecimal cenaArtikla = a.getCena();
            BigDecimal popust = a.getPopustUProcentima();
            BigDecimal cenaSaPopustom = cenaArtikla.multiply(BigDecimal.ONE.subtract(popust.divide(new BigDecimal(100))));

            if (ks.getKolicina() <= kolicina) {
//                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaArtikla.multiply(new BigDecimal(ks.getKolicina()))));
                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaSaPopustom.multiply(new BigDecimal(ks.getKolicina()))));
                em.createQuery("DELETE FROM KorpaSadrzi ks2 WHERE ks2.korpaSadrziPK.idKorpe = :idKorpe AND ks2.korpaSadrziPK.idArtikla = :idArtikla").setParameter("idKorpe", korpa.getIdKorpe()).setParameter("idArtikla", idArtikla).executeUpdate();
                // bulk delete zaobilazi L2 keš
                emf.getCache().evict(KorpaSadrzi.class);
            } else {
                ks.setKolicina(ks.getKolicina() - kolicina);
//                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaArtikla.multiply(new BigDecimal(kolicina))));
                korpa.setUkupnaCena(korpa.getUkupnaCena().subtract(cenaSaPopustom.multiply(new BigDecimal(kolicina))));
            }

            em.getTransaction().commit();
            return "{\"status\":\"ok\",\"idKorpe\":" + korpa.getIdKorpe() + ",\"ukupnaCena\":" + korpa.getUkupnaCena() + "}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }

    public String dodajUListuZelja(int idKorisnika, int idArtikla) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            Artikal a = em.find(Artikal.class, idArtikla);
            if (a == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " ne postoji.");
            }

            List<ListaZelja> listaResult = em.createQuery(
                "SELECT l FROM ListaZelja l WHERE l.idKorisnika.idKorisnika = :id", ListaZelja.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            ListaZelja lista = listaResult.isEmpty() ? null : listaResult.get(0);

            if (lista == null) {
                lista = new ListaZelja();
                lista.setIdKorisnika(k);
                lista.setDatumKreiranja(new java.util.Date());
                em.persist(lista);
                em.flush();
            }

            NaListiPK pk = new NaListiPK(lista.getIdListe(), idArtikla);
            NaListi nl = em.find(NaListi.class, pk);
            if (nl != null) {
                throw new IllegalArgumentException("Artikal je vec na listi zelja.");
            }

            nl = new NaListi(pk, new java.util.Date());
            em.persist(nl);

            em.getTransaction().commit();
            return "{\"status\":\"ok\",\"idListe\":" + lista.getIdListe() + "}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String obrisiIzListeZelja(int idKorisnika, int idArtikla) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<ListaZelja> listaResult = em.createQuery(
                "SELECT l FROM ListaZelja l WHERE l.idKorisnika.idKorisnika = :id", ListaZelja.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            ListaZelja lista = listaResult.isEmpty() ? null : listaResult.get(0);

            if (lista == null) {
                throw new IllegalArgumentException("Korisnik nema listu zelja.");
            }

            NaListiPK pk = new NaListiPK(lista.getIdListe(), idArtikla);
            NaListi nl = em.find(NaListi.class, pk);
            if (nl == null) {
                throw new IllegalArgumentException("Artikal sa ID " + idArtikla + " nije na listi zelja.");
            }

            em.remove(nl);
            em.getTransaction().commit();
            return "{\"status\":\"ok\",\"idListe\":" + lista.getIdListe() + "}";
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return "GRESKA|" + e.getMessage();
        } finally {
            em.close();
        }
    }
    
    public String dohvatiSveKategorije() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Kategorija> kategorije = em.createQuery("SELECT k FROM Kategorija k", Kategorija.class).getResultList();

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < kategorije.size(); i++) {
                Kategorija k = kategorije.get(i);
                sb.append("{\"idKategorije\":").append(k.getIdKategorije())
                  .append(",\"naziv\":\"").append(k.getNaziv()).append("\"")
                  .append(",\"idNadkategorije\":").append(k.getIdNadkategorije() != null ? k.getIdNadkategorije().getIdKategorije() : "null")
                  .append("}");
                if (i < kategorije.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }


    public String dohvatiArtikleKorisnika(int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<Artikal> artikli = em.createQuery(
                "SELECT a FROM Artikal a WHERE a.idProizvodjaca.idKorisnika = :id", Artikal.class)
                .setParameter("id", idKorisnika)
                .getResultList();

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < artikli.size(); i++) {
                sb.append(artikalUJson(artikli.get(i)));
                if (i < artikli.size() - 1)
                    sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        } finally {
            em.close();
        }
    }

    public String dohvatiKorpu(int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<Korpa> korpaResult = em.createQuery(
                "SELECT k2 FROM Korpa k2 WHERE k2.idKorisnika.idKorisnika = :id", Korpa.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            Korpa korpa = korpaResult.isEmpty() ? null : korpaResult.get(0);
            if (korpa == null) {
                return "{\"idKorpe\":null,\"ukupnaCena\":0,\"stavke\":[]}";
            }

            List<KorpaSadrzi> lista = em.createQuery(
                "SELECT ks FROM KorpaSadrzi ks WHERE ks.korpaSadrziPK.idKorpe = :idKorpe", KorpaSadrzi.class)
                .setParameter("idKorpe", korpa.getIdKorpe())
                .getResultList();

            StringBuilder stavke = new StringBuilder("[");
            for (int i = 0; i < lista.size(); i++) {
                KorpaSadrzi ks = lista.get(i);
                Artikal art = em.find(Artikal.class, ks.getKorpaSadrziPK().getIdArtikla());
                BigDecimal cenaSaPopustom = BigDecimal.ZERO;
                if (art != null) {
                    BigDecimal popust = art.getPopustUProcentima();
                    cenaSaPopustom = art.getCena().multiply(BigDecimal.ONE.subtract(popust.divide(new BigDecimal(100))));
                }
                
                stavke.append("{")
                    .append("\"idArtikla\":").append(ks.getKorpaSadrziPK().getIdArtikla()).append(",")
                    .append("\"naziv\":\"").append(art != null ? art.getNaziv() : "").append("\",")
                    .append("\"cena\":").append(art != null ? art.getCena() : 0).append(",")
                    .append("\"cenaSaPopustom\":").append(cenaSaPopustom).append(",")
                    .append("\"kolicina\":").append(ks.getKolicina()).append(",")
                    .append("\"idProdavca\":").append(art != null ? art.getIdProizvodjaca().getIdKorisnika() : 0)
                    .append("}");
                if (i < lista.size() - 1)
                    stavke.append(",");
            }
            stavke.append("]");

            return "{\"idKorpe\":" + korpa.getIdKorpe() + 
                   ",\"ukupnaCena\":" + korpa.getUkupnaCena() + 
                   ",\"stavke\":" + stavke.toString() + "}";
        } finally {
            em.close();
        }
    }

    public String dohvatiListuZelja(int idKorisnika) {
        EntityManager em = emf.createEntityManager();
        try {
            Korisnik k = em.find(Korisnik.class, idKorisnika);
            if (k == null) {
                throw new IllegalArgumentException("Korisnik sa ID " + idKorisnika + " ne postoji.");
            }

            List<ListaZelja> listaResult = em.createQuery(
                "SELECT l FROM ListaZelja l WHERE l.idKorisnika.idKorisnika = :id", ListaZelja.class)
                .setParameter("id", idKorisnika)
                .getResultList();
            ListaZelja lista = listaResult.isEmpty() ? null : listaResult.get(0);

            if (lista == null) {
                return "{\"idListe\":null,\"datumKreiranja\":null,\"artikli\":[]}";
            }

            List<NaListi> naListi = em.createQuery(
                "SELECT n FROM NaListi n WHERE n.naListiPK.idListe = :idListe", NaListi.class)
                .setParameter("idListe", lista.getIdListe())
                .getResultList();

            StringBuilder artikli = new StringBuilder("[");
            for (int i = 0; i < naListi.size(); i++) {
                NaListi nl = naListi.get(i);
                Artikal art = em.find(Artikal.class, nl.getNaListiPK().getIdArtikla());
                artikli.append("{")
                       .append("\"idArtikla\":").append(nl.getNaListiPK().getIdArtikla()).append(",")
                       .append("\"naziv\":\"").append(art != null ? art.getNaziv() : "").append("\",")
                       .append("\"cena\":").append(art != null ? art.getCena() : 0).append(",")
                       .append("\"vremeDodavanja\":\"").append(nl.getVremeDodavanja()).append("\"")
                       .append("}");
                if (i < naListi.size() - 1)
                    artikli.append(",");
            }
            artikli.append("]");

            return "{\"idListe\":" + lista.getIdListe() + 
                   ",\"datumKreiranja\":\"" + lista.getDatumKreiranja() + "\"" +
                   ",\"artikli\":" + artikli.toString() + "}";
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