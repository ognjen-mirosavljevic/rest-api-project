/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author ognje
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "podsistem1Queue")
    private static Queue podsistem1Queue;
    
    @Resource(lookup = "ServerReplyQueue")
    private static Queue replyQueue;
    
    public static void main(String[] args) {
        try (JMSContext context = connectionFactory.createContext()) {

            JMSConsumer consumer = context.createConsumer(podsistem1Queue);
            JMSProducer producer = context.createProducer();

            while (true) {
                Message msg = consumer.receive();

                if (!(msg instanceof TextMessage)) {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, "Primljena poruka nije TextMessage.");
                    continue;
                }

                try {
                    TextMessage textMsg = (TextMessage) msg;
                    String zahtev = textMsg.getText();

                    System.out.println("PRIMLJEN zahtev: " + zahtev);
                    System.out.println("PRIMLJEN correlationId: " + msg.getJMSCorrelationID());
                    System.out.println("PRIMLJEN replyTo: " + msg.getJMSReplyTo());

                    String odgovor = ObradaZahteva.obradi(zahtev);

                    TextMessage replyMsg = context.createTextMessage(odgovor);
                    replyMsg.setJMSCorrelationID(msg.getJMSCorrelationID());

                    producer.send(msg.getJMSReplyTo(), replyMsg);

                    System.out.println("POSLAT odgovor: " + odgovor);
                    System.out.println("POSLAT correlationId: " + replyMsg.getJMSCorrelationID());

                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Greska pri obradi zahteva.", ex);
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Fatalna greska u podsistemu 1.", ex);
        }
    }
  
}
