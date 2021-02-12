package mdb;


import banking.LocalBank;
import entity.Compte;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(name = "EJBExempleQueue", activationConfig={
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "queue/SampleQueue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName="maxPoolSize",
                propertyValue="1"),
        @ActivationConfigProperty(propertyName="maxSession",
                propertyValue="1")
})
public class JMSMessageBean implements MessageListener {

    @EJB
    private LocalBank bank;

    public synchronized void onMessage(final Message message) {

        System.out.println("Received JMS Message: " + message);

        // Extract Message's text value
        String text = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                text = textMessage.getText();
            } catch (JMSException e) {
                System.err.println("Unexpected Exception: " + e.getMessage());
                e.printStackTrace(System.err);
                return;
            }
        } else {
            return;
        }
        String[] mess= text.split(":");
        int id = Integer.parseInt(mess[0]);
        long solde = Long.decode(mess[1]);
        bank.addCompte(new Compte(id,solde));
    }

}
