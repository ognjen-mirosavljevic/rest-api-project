/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.fasterxml.classmate.TypeBindings;
import java.util.UUID;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 *
 * @author ognje
 */
public class JMSHelper {
    public static String send(String zahtev, String queueName) {
        try {
            InitialContext ctx = new InitialContext();

            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/__defaultConnectionFactory");
            Queue requestQueue = (Queue) ctx.lookup(queueName);
            Queue replyQueue = (Queue) ctx.lookup("ServerReplyQueue");

            String correlationId = java.util.UUID.randomUUID().toString();

            try (JMSContext context = cf.createContext()) {
                JMSProducer producer = context.createProducer();

                String selector = "JMSCorrelationID = '" + correlationId + "'";
                JMSConsumer consumer = context.createConsumer(replyQueue, selector);

                TextMessage requestMsg = context.createTextMessage(zahtev);
                requestMsg.setJMSReplyTo(replyQueue);
                requestMsg.setJMSCorrelationID(correlationId);

                System.out.println("SALJEM zahtev: " + zahtev);
                System.out.println("SALJEM correlationId: " + correlationId);

                producer.send(requestQueue, requestMsg);

                Message msg = consumer.receive(5000);

                if (msg == null) {
                    return "ERROR;Nije stigao JMS odgovor";
                }

                System.out.println("STIGAO odgovor correlationId: " + msg.getJMSCorrelationID());

                if (msg instanceof TextMessage) {
                    return ((TextMessage) msg).getText();
                }

                return "ERROR;Primljena poruka nije TextMessage";
            }
        } catch (Exception e) {
            return "ERROR;" + e.getMessage();
        }
    }
}
