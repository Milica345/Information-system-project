package com.milica.centralniserver;

import javax.jms.*;

public class JMSHelper {

    public static String posaljiIcekaj(ConnectionFactory cf, Queue queue, String poruka) {
        try (JMSContext ctx = cf.createContext()) {
            TemporaryQueue replyQueue = ctx.createTemporaryQueue();
            TextMessage msg = ctx.createTextMessage(poruka);
            msg.setJMSReplyTo(replyQueue);
            ctx.createProducer().send(queue, msg);
            String odgovor = ctx.createConsumer(replyQueue)
                               .receiveBody(String.class, 5000);
            return odgovor;
        } catch (Exception e) {
            return "GRESKA|" + e.getMessage();
        }
    }
}