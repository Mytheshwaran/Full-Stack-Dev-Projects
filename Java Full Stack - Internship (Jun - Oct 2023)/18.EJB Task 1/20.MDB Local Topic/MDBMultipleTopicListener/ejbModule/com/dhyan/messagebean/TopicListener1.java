package com.dhyan.messagebean;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Message-Driven Bean implementation class for: QueueListener
 */
@MessageDriven(activationConfig =
{@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "myTopicTask")})
public class TopicListener1 implements MessageListener
{
    /**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message)
    {
        try
        {
        TextMessage msg=(TextMessage) message;
        System.out.println("Message Bean 1: "+msg.getText());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

}
