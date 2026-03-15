package podsistem1;

import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.*;
import javax.persistence.Persistence;
import servis.Podsistem1Servis;

public class Main {

    @Resource(lookup = "jms/podsistem1ConnectionFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/podsistem1Queue")
    private static Queue queue;

    public static void main(String[] args) {

        Podsistem1Servis servis = new Podsistem1Servis(
                Persistence.createEntityManagerFactory("Podsistem1PU")
        );

        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);

        System.out.println("Podsistem 1 pokrenut, ceka poruke...");

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
                        case "PROVERA_KORISNIKA":
                            // param1 = korisnickoIme, param2 = sifra
                            rezultat = servis.proveraKorisnika(delovi[1], delovi[2]);
                            break;
                        case "KREIRAJ_GRAD":
                            // param1 = nazivGrada
                            rezultat = servis.kreirajGrad(delovi[1]);
                            break;
                        case "KREIRAJ_KORISNIKA":
                            // param1 = korisnickoIme, param2 = sifra, param3 = ime
                            // param4 = prezime, param5 = adresa, param6 = idGrada 
                            rezultat = servis.kreirajKorisnika(
                                delovi[1], delovi[2], delovi[3],
                                delovi[4], delovi[5], Integer.parseInt(delovi[6])
                            );
                            break;
                        case "DODAJ_NOVAC":
                            // param1 = korisnickoIme, param2 = iznos
                            rezultat = servis.dodajNovac(
                                delovi[1], new BigDecimal(delovi[2])
                            );
                            break;
                        case "PROMENI_ADRESU":
                            // param1 = korisnickoIme, param2 = adresa, param3 = idGrada
                            rezultat = servis.promeniAdresuIGrad(
                                delovi[1], delovi[2], Integer.parseInt(delovi[3])
                            );
                            break;
                        case "DOHVATI_GRADOVE":
                            rezultat = servis.dohvatiSveGradove();
                            break;
                        case "DOHVATI_KORISNIKE":
                            rezultat = servis.dohvatiSveKorisnike();
                            break;
                        default:
                            rezultat = "GRESKA|Nepoznata akcija: " + akcija;
                    }
                    
                    if (rezultat == null) rezultat = "NEPOSTOJI";

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