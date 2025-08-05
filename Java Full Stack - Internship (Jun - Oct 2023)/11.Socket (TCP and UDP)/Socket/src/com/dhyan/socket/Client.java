package com.dhyan.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		try
		{
			Socket socket=new Socket("localhost",5000);
			System.out.println("Connected");
			
			Scanner input = new Scanner(System.in);
			DataOutputStream write=new DataOutputStream(socket.getOutputStream());
			DataInputStream read=new DataInputStream(socket.getInputStream());
			
			String yourMsg=" ";
			while(true)
			{
				System.out.print("Client: ");
				yourMsg=input.nextLine();
				write.writeUTF(yourMsg);
				if(!yourMsg.equals("End"))
				{
					System.out.println("Server: "+read.readUTF());
				}
				else
				{
					input.close();
					read.close();
					write.close();
					socket.close();
					break;
				}
			}			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
