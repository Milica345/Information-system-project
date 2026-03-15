package com.milica.klijentskaaplikacija;

import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;

public class Main {

    static final String SERVER = "http://localhost:8080/CentralniServer/api";
    static String ulogovanKorisnik = null;
    static String ulogovanaSifra = null;
    static Scanner scanner = new Scanner(System.in);

    static String basicAuth(String korisnik, String sifra) {
        String kredencijali = korisnik + ":" + sifra;
        return "Basic " + Base64.getEncoder().encodeToString(kredencijali.getBytes());
    }

    static String formatirajJson(String odgovor) {
        int idx = odgovor.indexOf(']');
        String status = odgovor.substring(0, idx + 1);
        String json = odgovor.substring(idx + 1).trim();

        StringBuilder sb = new StringBuilder();
        int indent = 0;
        for (char c : json.toCharArray()) {
            switch (c) {
                case '{': case '[':
                    sb.append(c).append("\n");
                    indent++;
                    for (int i = 0; i < indent; i++) sb.append("  ");
                    break;
                case '}': case ']':
                    sb.append("\n");
                    indent--;
                    for (int i = 0; i < indent; i++) sb.append("  ");
                    sb.append(c);
                    break;
                case ',':
                    sb.append(c).append("\n");
                    for (int i = 0; i < indent; i++) sb.append("  ");
                    break;
                case ':':
                    sb.append(" : ");
                    break;
                default:
                    sb.append(c);
            }
        }
        return status + "\n" + sb.toString();
    }

    static String get(String putanja) throws Exception {
        URL url = new URL(SERVER + putanja);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        if (ulogovanKorisnik != null)
            con.setRequestProperty("Authorization", basicAuth(ulogovanKorisnik, ulogovanaSifra));
        return procitajOdgovor(con);
    }

    static String post(String putanja, String params) throws Exception {
        URL url = new URL(SERVER + putanja);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (ulogovanKorisnik != null)
            con.setRequestProperty("Authorization", basicAuth(ulogovanKorisnik, ulogovanaSifra));
        try (OutputStream os = con.getOutputStream()) {
            os.write(params.getBytes());
        }
        return procitajOdgovor(con);
    }

    static String put(String putanja, String params) throws Exception {
        URL url = new URL(SERVER + putanja);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        if (ulogovanKorisnik != null)
            con.setRequestProperty("Authorization", basicAuth(ulogovanKorisnik, ulogovanaSifra));
        try (OutputStream os = con.getOutputStream()) {
            os.write(params.getBytes());
        }
        return procitajOdgovor(con);
    }

    static String delete(String putanja) throws Exception {
        URL url = new URL(SERVER + putanja);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");
        if (ulogovanKorisnik != null)
            con.setRequestProperty("Authorization", basicAuth(ulogovanKorisnik, ulogovanaSifra));
        return procitajOdgovor(con);
    }

    static String procitajOdgovor(HttpURLConnection con) throws Exception {
        int status = con.getResponseCode();
        InputStream is = (status >= 400) ? con.getErrorStream() : con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        return "[" + status + "] " + sb.toString();
    }

    static void prijava() throws Exception {
        System.out.println("Korisnicko ime: ");
        String ime = scanner.nextLine();
        System.out.println("Sifra: ");
        String sifra = scanner.nextLine();

        ulogovanKorisnik = ime;
        ulogovanaSifra = sifra;

        String odgovor = formatirajJson(get("/korisnici/prijava"));
        System.out.println("Odgovor: " + odgovor);

        if (odgovor.startsWith("[200]")) {
            System.out.println("Uspesna prijava!");
        } else {
            ulogovanKorisnik = null;
            ulogovanaSifra = null;
            System.out.println("Prijava neuspesna.");
        }
    }

    static void odjava() {
        ulogovanKorisnik = null;
        ulogovanaSifra = null;
        System.out.println("Odjavili ste se.");
    }

    static void kreirajGrad() throws Exception {
        System.out.println("Naziv grada: ");
        String naziv = scanner.nextLine();
        System.out.println(formatirajJson(post("/korisnici/gradovi", "naziv=" + naziv)));
    }

    static void kreirajKorisnika() throws Exception {
        System.out.println("Korisnicko ime: ");
        String ki = scanner.nextLine();
        System.out.println("Sifra: ");
        String s = scanner.nextLine();
        System.out.println("Ime: ");
        String ime = scanner.nextLine();
        System.out.println("Prezime: ");
        String prez = scanner.nextLine();
        System.out.println("Adresa: ");
        String adr = scanner.nextLine();
        System.out.println("ID grada: ");
        String idG = scanner.nextLine();
        System.out.println(formatirajJson(post("/korisnici",
            "korisnickoIme=" + ki + "&sifra=" + s + "&ime=" + ime +
            "&prezime=" + prez + "&adresa=" + adr + "&idGrada=" + idG)));
    }

    static void dodajNovac() throws Exception {
        System.out.println("Korisnicko ime: ");
        String ki = scanner.nextLine();
        System.out.println("Iznos: ");
        String iznos = scanner.nextLine();
        System.out.println(formatirajJson(put("/korisnici/novac",
            "korisnickoIme=" + ki + "&iznos=" + iznos)));
    }

    static void promeniAdresu() throws Exception {
        System.out.println("Korisnicko ime: ");
        String ki = scanner.nextLine();
        System.out.println("Nova adresa: ");
        String adr = scanner.nextLine();
        System.out.println("ID novog grada: ");
        String idG = scanner.nextLine();
        System.out.println(formatirajJson(put("/korisnici/adresa",
            "korisnickoIme=" + ki + "&adresa=" + adr + "&idGrada=" + idG)));
    }

    static void dohvatiGradove() throws Exception {
        System.out.println(formatirajJson(get("/korisnici/gradovi")));
    }

    static void dohvatiKorisnike() throws Exception {
        System.out.println(formatirajJson(get("/korisnici")));
    }

    // podsistem 2

    static void kreirajKategoriju() throws Exception {
        System.out.println("Naziv kategorije: ");
        String naziv = scanner.nextLine();
        System.out.println("ID nadkategorije (-1 ako nema): ");
        String idNad = scanner.nextLine();
        System.out.println(formatirajJson(post("/artikli/kategorije",
            "naziv=" + naziv + "&idNadkategorije=" + idNad)));
    }

    static void kreirajArtikal() throws Exception {
        System.out.println("Naziv artikla: ");
        String naziv = scanner.nextLine();
        System.out.println("Opis: ");
        String opis = scanner.nextLine();
        System.out.println("Cena: ");
        String cena = scanner.nextLine();
        System.out.println("Popust (0 ako nema): ");
        String popust = scanner.nextLine();
        System.out.println("ID kategorije: ");
        String idKat = scanner.nextLine();
        System.out.println(formatirajJson(post("/artikli",
            "naziv=" + naziv + "&opis=" + opis + "&cena=" + cena +
            "&popust=" + popust + "&idKategorije=" + idKat)));
    }

    static void promeniCenuArtikla() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println("Nova cena: ");
        String cena = scanner.nextLine();
        System.out.println(formatirajJson(put("/artikli/cena",
            "idArtikla=" + idArt + "&cena=" + cena)));
    }

    static void postaviPopust() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println("Popust (%): ");
        String popust = scanner.nextLine();
        System.out.println(formatirajJson(put("/artikli/popust",
            "idArtikla=" + idArt + "&popust=" + popust)));
    }

    static void dodajArtikalUKorpu() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println("Kolicina: ");
        String kolicina = scanner.nextLine();
        System.out.println(formatirajJson(post("/artikli/korpa",
            "idArtikla=" + idArt + "&kolicina=" + kolicina)));
    }

    static void obrisiArtikalIzKorpe() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println("Kolicina: ");
        String kolicina = scanner.nextLine();
        System.out.println(formatirajJson(delete(
            "/artikli/korpa?idArtikla=" + idArt + "&kolicina=" + kolicina)));
    }

    static void dodajArtikalUListuZelja() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println(formatirajJson(post("/artikli/lista",
            "idArtikla=" + idArt)));
    }

    static void obrisiArtikalSaListeZelja() throws Exception {
        System.out.println("ID artikla: ");
        String idArt = scanner.nextLine();
        System.out.println(formatirajJson(delete(
            "/artikli/lista?idArtikla=" + idArt)));
    }

    static void dohvatiKategorije() throws Exception {
        System.out.println(formatirajJson(get("/artikli/kategorije")));
    }

    static void dohvatiArtikleKojeProdajeKorisnik() throws Exception {
        System.out.println(formatirajJson(get("/artikli")));
    }

    static void dohvatiSadrzajKorpeKorisnika() throws Exception {
        System.out.println(formatirajJson(get("/artikli/korpa")));
    }

    static void dohvatiSadrzajListeZeljaKorisnika() throws Exception {
        System.out.println(formatirajJson(get("/artikli/lista")));
    }

    // podsistem 3

    static void plati() throws Exception {
        System.out.println("Adresa za dostavu: ");
        String adresa = scanner.nextLine();
        System.out.println("Grad za dostavu: ");
        String grad = scanner.nextLine();
        System.out.println(formatirajJson(post("/trgovina/placanje",
            "adresa=" + adresa + "&gradZaDostavu=" + grad)));
    }

    static void dohvatiSveNarudzbineKorisnika() throws Exception {
        System.out.println(formatirajJson(get("/trgovina/narudzbine/korisnik")));
    }

    static void dohvatiSveNarudzbine() throws Exception {
        System.out.println(formatirajJson(get("/trgovina/narudzbine")));
    }

    static void dohvatiSveTransakcije() throws Exception {
        System.out.println(formatirajJson(get("/trgovina/transakcije")));
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MENI =====");
            System.out.println(ulogovanKorisnik == null ? "Niste prijavljeni." : "Prijavljeni kao: " + ulogovanKorisnik);
            System.out.println("0. Odjava");
            System.out.println("1. Prijava");
            System.out.println("2. Kreiraj grad");
            System.out.println("3. Kreiraj korisnika");
            System.out.println("4. Dodaj novac korisniku");
            System.out.println("5. Promeni adresu i grad");
            System.out.println("6. Kreiraj kategoriju");
            System.out.println("7. Kreiraj artikal");
            System.out.println("8. Promeni cenu artikla");
            System.out.println("9. Postavi popust na artikal");
            System.out.println("10. Dodaj artikal u korpu");
            System.out.println("11. Obrisi odredjenu kolicinu artikla iz korpe");
            System.out.println("12. Dodaj artikal u listu zelja");
            System.out.println("13. Obrisi artikal iz liste zelja");
            System.out.println("14. Plati");
            System.out.println("15. Dohvati sve gradove");
            System.out.println("16. Dohvati sve korisnike");
            System.out.println("17. Dohvati sve kategorije");
            System.out.println("18. Dohvati sve artikle koje prodajes");
            System.out.println("19. Dohvati sadrzaj korpe");
            System.out.println("20. Dohvati sadrzaj liste zelja");
            System.out.println("21. Dohvati sve svoje narudzbine");
            System.out.println("22. Dohvati sve narudzbine");
            System.out.println("23. Dohvati sve transakcije");
            System.out.println("-1. Izlaz");
            System.out.println("Izbor: ");

            String izbor = scanner.nextLine();
            try {
                switch (izbor) {
                    case "0": odjava(); break;
                    case "1": prijava(); break;
                    case "2": kreirajGrad(); break;
                    case "3": kreirajKorisnika(); break;
                    case "4": dodajNovac(); break;
                    case "5": promeniAdresu(); break;
                    case "6": kreirajKategoriju(); break;
                    case "7": kreirajArtikal(); break;
                    case "8": promeniCenuArtikla(); break;
                    case "9": postaviPopust(); break;
                    case "10": dodajArtikalUKorpu(); break;
                    case "11": obrisiArtikalIzKorpe(); break;
                    case "12": dodajArtikalUListuZelja(); break;
                    case "13": obrisiArtikalSaListeZelja(); break;
                    case "14": plati(); break;
                    case "15": dohvatiGradove(); break;
                    case "16": dohvatiKorisnike(); break;
                    case "17": dohvatiKategorije(); break;
                    case "18": dohvatiArtikleKojeProdajeKorisnik(); break;
                    case "19": dohvatiSadrzajKorpeKorisnika(); break;
                    case "20": dohvatiSadrzajListeZeljaKorisnika(); break;
                    case "21": dohvatiSveNarudzbineKorisnika(); break;
                    case "22": dohvatiSveNarudzbine(); break;
                    case "23": dohvatiSveTransakcije(); break;
                    case "-1": System.out.println("Dovidjenja!"); return;
                    default: System.out.println("Nepoznat izbor.");
                }
            } catch (Exception e) {
                System.out.println("Greska: " + e.getMessage());
            }
        }
    }
}