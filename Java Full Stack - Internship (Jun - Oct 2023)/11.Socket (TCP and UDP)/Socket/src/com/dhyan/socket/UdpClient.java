package com.dhyan.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpClient {

	public static void main(String[] args) {
		try {
			DatagramSocket connection = new DatagramSocket();
			System.out.println("Connected");

			InetAddress ip = InetAddress.getByName("localhost");

			Scanner scan = new Scanner(System.in);
			while (true) {
				byte[] bytedata= new byte[1024];
				
				System.out.print("Client: ");
				String msg = scan.next();
				DatagramPacket data = new DatagramPacket(msg.getBytes(), msg.length(), ip, 5900);
				connection.send(data);	
				
				if(!msg.equals("End"))
				{
					DatagramPacket receivedData=new DatagramPacket(bytedata,1024);
					connection.receive(receivedData);		
					String result=new String(receivedData.getData(),0,receivedData.getLength());
					System.out.println("Server: "+result);
				}
				else
				{
					connection.close();
					scan.close();
					break;
				}
			}
		} 
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
