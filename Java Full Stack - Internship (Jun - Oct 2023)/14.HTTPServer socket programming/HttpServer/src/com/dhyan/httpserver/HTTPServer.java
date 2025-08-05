package com.dhyan.httpserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			System.out.println("Waiting for client connection...");
			Socket socket=serverSocket.accept();
			System.out.println("Client connected");

			BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream write = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			
			String connectionMsg=" ";
			String url=read.readLine();
			System.out.println(url);
			
			while(true)
			{
				connectionMsg=read.readLine();
				System.out.println(connectionMsg);
				if(connectionMsg.isEmpty())
					break;
			}
			
			String[] patharr=url.split(" ");
			InputStream htmlInput = new FileInputStream(patharr[1]);
			byte[] bytes=new byte[1024];
			
			
			int count;
		    write.writeBytes("HTTP/1.1 200 OK \r\n");
		    write.writeBytes("Content-Type: text/html \r\n");
		    write.writeBytes("\r\n");
		    while ((count = htmlInput.read(bytes)) > 0) {
		        write.write(bytes, 0, count);
		    }
		    write.flush();
		    htmlInput.close();
			serverSocket.close();
						
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
