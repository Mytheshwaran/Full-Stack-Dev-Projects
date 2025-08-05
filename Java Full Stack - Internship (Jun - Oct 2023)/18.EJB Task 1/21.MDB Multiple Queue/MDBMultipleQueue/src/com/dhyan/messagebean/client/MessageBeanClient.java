package com.dhyan.messagebean.client;

import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MessageBeanClient
{
    public static void main(String[] args)
    {
        QueueSession session = null;
        try
        {
            Properties properties = new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");

            Context context = new InitialContext(properties);
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("myQueueTask");
            QueueSender sender = session.createSender(queue);

            int i = 0;
            while (true)
            {
                TextMessage message = session.createTextMessage();
                message.setText(Thread.currentThread().getName() + "<<<<<<  Queue  >>>>>> " + i++);
                sender.send(message);
                Thread.sleep(2000);
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            try
            {
                session.close();
            }
            catch (JMSException e)
            {
                e.printStackTrace();
            }
        }
    }

}
