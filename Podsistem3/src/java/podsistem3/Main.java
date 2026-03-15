package podsistem3;

import java.math.BigDecimal;
import javax.annotation.Resource;
import javax.jms.*;
import javax.persistence.Persistence;
import servis.Podsistem3Servis;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    
    @Resource(lookup = "jms/podsistem3ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/podsistem3Queue")
    private static Queue queue;

    public static void main(String[] args) {
        Podsistem3Servis servis = new Podsistem3Servis(
                Persistence.createEntityManagerFactory("Podsistem3PU")
        );

        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);

        System.out.println("Podsistem 3 pokrenut, ceka poruke...");

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
                        case "DOHVATI_NARUDZBINE_KORISNIKA":
                            // param1 = idKorisnika
                            rezultat = servis.dohvatiSveNarudzbineKorisnika(
                                Integer.parseInt(delovi[1])
                            );
                            break;
                        case "DOHVATI_NARUDZBINE":
                            rezultat = servis.dohvatiSveNarudzbine();
                            break;
                        case "DOHVATI_TRANSAKCIJE":
                            rezultat = servis.dohvatiSveTransakcije();
                            break;
                        case "KREIRAJ_NARUDZBINU":
                            // param1 = idKorisnika, param2 = adresa, param3 = gradZaDostavu, param4 = stavkeJson
                            rezultat = servis.kreirajNarudzbinu(
                                Integer.parseInt(delovi[1]), delovi[2], delovi[3], delovi[4]
                            );
                            break;
                        case "NAPRAVI_TRANSAKCIJU":
                            // param1 = idNarudzbine, param2 = suma
                            rezultat = servis.napraviTransakciju(
                                Integer.parseInt(delovi[1]), new BigDecimal(delovi[2])
                            );
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
