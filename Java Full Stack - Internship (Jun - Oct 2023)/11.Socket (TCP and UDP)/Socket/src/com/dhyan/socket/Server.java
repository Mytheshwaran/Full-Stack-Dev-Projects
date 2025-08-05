package com.dhyan.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Waiting for client connection...");
			Socket socket=serverSocket.accept();
			System.out.println("Client connected");
			
			Scanner input = new Scanner(System.in);
			DataOutputStream write = new DataOutputStream(socket.getOutputStream());
			DataInputStream read = new DataInputStream(socket.getInputStream());
			
			System.out.println("Client Ip address: "+socket.getInetAddress());
			System.out.println("Client Port: "+socket.getPort());
			System.out.println("Server Ip address: "+serverSocket.getInetAddress());
			System.out.println("Server Port: "+serverSocket.getLocalPort());
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			String yourMsg = " ";
			while (true) {
				String clientMsg=read.readUTF();
				System.out.println("Client: "+clientMsg);
				if(!clientMsg.equals("End"))
				{
					System.out.print("Server: ");
					yourMsg = input.nextLine();
					write.writeUTF(yourMsg);					
				}
				else
				{
					System.out.println("--Client Connection End--");
					read.close();
					input.close();
					write.close();
					serverSocket.close();
					break;
				}
			}			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
