package com.dhyan.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UdpServer {
	public static void main(String[] args) {
		try {
			DatagramSocket connection=new DatagramSocket(5900);
			System.out.println("waiting for client");
			Scanner scan=new Scanner(System.in);
			while (true) {
				byte[] bytedata= new byte[1024];
								
				DatagramPacket receivedData=new DatagramPacket(bytedata,1024);
				connection.receive(receivedData);		
				String result=new String(receivedData.getData(),0,receivedData.getLength());
				System.out.println("Client: "+result);
				
				
				if(!result.equals("End"))
				{
					System.out.print("Server: ");
					String msg = scan.next();
					DatagramPacket data = new DatagramPacket(msg.getBytes(), msg.length(),receivedData.getAddress(), receivedData.getPort());
					connection.send(data);
				}
				else
				{
					System.out.println("--Connection End--");
					connection.close();
					scan.close();
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static String getString()
	{
		Scanner scan=new Scanner(System.in);
		String string = scan.next();
		scan.close();
		return string;
	}
}
