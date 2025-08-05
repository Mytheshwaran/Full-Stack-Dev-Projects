package com.dhyan.local.queue;

import java.io.PrintWriter;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
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
            Context context = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
            QueueConnection connection = factory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("myQueueTask");
            QueueSender sender = session.createSender(queue);

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
