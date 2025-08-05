package com.dhyan.local.topic;

import java.io.PrintWriter;

import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SenderServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            PrintWriter out=response.getWriter();
            Context context=new InitialContext();
            TopicConnectionFactory factory=(TopicConnectionFactory) context.lookup("ConnectionFactory");
            TopicConnection connection=factory.createTopicConnection();
            TopicSession session=connection.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
            Topic topic=(Topic) context.lookup("myTopicTask");
            TopicPublisher sender=session.createPublisher(topic);
//          MessageProducer sender = session.createProducer(topic);
            
            TextMessage message = session.createTextMessage();
            String msg=request.getParameter("Message");
            message.setText(msg);
            sender.send(message);
            out.println("<div align='center'><h1>Message Sended Successfully</h1></div>");
            session.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
