package podsistem2;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import javax.persistence.Persistence;
import servis.Podsistem2Servis;

public class Main {

    @Resource(lookup = "jms/podsistem2ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/podsistem2Queue")
    private static Queue queue;

    public static void main(String[] args) {
        Podsistem2Servis servis = new Podsistem2Servis(
                Persistence.createEntityManagerFactory("Podsistem2PU")
        );

        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);

        System.out.println("Podsistem 2 pokrenut, ceka poruke...");

        while (true) {
            Message msg = consumer.receive();
            if (msg instanceof TextMessage) {
                try {
                    TextMessage txtMsg = (TextMessage) msg;
                    String tekst = txtMsg.getText();
                    System.out.println("Primljena poruka: " + tekst);

                    String[] delovi = tekst.split("\\|");
                    String akcija = delovi[0];
                    String rezultat = null;

                    switch (akcija) {
                        case "DOHVATI_ID_KORISNIKA":
                            //param1 = korisnickoIme
                            rezultat = String.valueOf(servis.dohvatiIdKorisnika(delovi[1]));
                            break;
                        case "KREIRAJ_KATEGORIJU":
                            // param1 = naziv, param2 = idNadkategorije
                            rezultat = servis.kreirajKategoriju(
                                delovi[1], Integer.parseInt(delovi[2])
                            );
                            break;
                        case "KREIRAJ_ARTIKAL":
                            // param1=naziv, param2=opis, param3=cena
                            // param4=popust, param5=idKategorije, param6=idProizvodjaca
                            rezultat = servis.kreirajArtikal(
                                delovi[1], delovi[2],
                                new BigDecimal(delovi[3]), new BigDecimal(delovi[4]),
                                Integer.parseInt(delovi[5]), Integer.parseInt(delovi[6])
                            );
                            break;
                        case "PROMENI_CENU":
                            // param1 = idArtikla, param2 = cena, param3 = idKorisnika
                            rezultat = servis.promeniCenuArtikla(
                                Integer.parseInt(delovi[1]), new BigDecimal(delovi[2]),
                                Integer.parseInt(delovi[3])
                            );
                            break;
                        case "POSTAVI_POPUST":
                            // param1 = idArtikla, param2 = popust, param3 = idKorisnika
                            rezultat = servis.postaviPopust(
                                Integer.parseInt(delovi[1]), new BigDecimal(delovi[2]),
                                Integer.parseInt(delovi[3])
                            );
                            break;
                        case "DODAJ_U_KORPU":
                            // param1 = idKorisnika, param2 = idArtikla, param3 = kolicina
                            rezultat = servis.dodajUKorpu(
                                Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]),
                                Integer.parseInt(delovi[3])
                            );
                            break;
                        case "OBRISI_IZ_KORPE":
                            // param1 = idKorisnika, param2 = idArtikla, param3 = kolicina
                            rezultat = servis.obrisiIzKorpe(
                                Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2]),
                                Integer.parseInt(delovi[3])
                            );
                            break;
                        case "DODAJ_U_LISTU_ZELJA":
                            // param1 = idKorisnika, param2 = idArtikla
                            rezultat = servis.dodajUListuZelja(
                                Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2])
                            );
                            break;
                        case "OBRISI_IZ_LISTE_ZELJA":
                            // param1 = idKorisnika, param2 = idArtikla
                            rezultat = servis.obrisiIzListeZelja(
                                Integer.parseInt(delovi[1]), Integer.parseInt(delovi[2])
                            );
                            break;
                        case "DOHVATI_KATEGORIJE":
                            rezultat = servis.dohvatiSveKategorije();
                            break;
                        case "DOHVATI_ARTIKLE_KORISNIKA":
                            // param1 = idKorisnika
                            rezultat = servis.dohvatiArtikleKorisnika(
                                Integer.parseInt(delovi[1])
                            );
                            break;
                        case "DOHVATI_KORPU":
                            // param1 = idKorisnika
                            rezultat = servis.dohvatiKorpu(
                                Integer.parseInt(delovi[1])
                            );
                            break;
                        case "DOHVATI_LISTU_ZELJA":
                            // param1 = idKorisnika
                            rezultat = servis.dohvatiListuZelja(
                                Integer.parseInt(delovi[1])
                            );
                            break;
                        case "ISPRAZNI_KORPU":
                            // param1 = idKorisnika
                            rezultat = servis.ispraznIKorpu(Integer.parseInt(delovi[1]));
                            break;    
                        case "DODAJ_KORISNIKA":
                            // param1 = korisnickoIme
                            rezultat = servis.dodajKorisnika(delovi[1]);
                            break;    
                        default:
                            rezultat = "GRESKA|Nepoznata akcija: " + akcija;
                    }

                    Destination replyTo = msg.getJMSReplyTo();
                    if (replyTo != null) {
                        JMSProducer producer = context.createProducer();
                        TextMessage odgovor = context.createTextMessage(rezultat);
                        odgovor.setJMSCorrelationID(msg.getJMSMessageID());
                        producer.send(replyTo, odgovor);
                        System.out.println("Poslat odgovor: " + rezultat);
                    }

                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}