package com.dhyan.messagebean.client;

import java.util.Properties;

import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

public class MessageBeanClient
{
    public static void main(String[] args)
    {
        try
        {
            Properties properties=new Properties();
            properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            
            Context context=new InitialContext(properties);
            TopicConnectionFactory factory=(TopicConnectionFactory) context.lookup("jms/RemoteConnectionFactory");
            TopicConnection connection=factory.createTopicConnection();
            TopicSession session=connection.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
            Topic topic=(Topic) context.lookup("myTopicTask");
            TopicPublisher sender=session.createPublisher(topic);
//          MessageProducer sender = session.createProducer(topic);
            
            TextMessage message = session.createTextMessage();
            message.setText("<<<<<<  Topic  >>>>>>");
            sender.send(message);
            session.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
