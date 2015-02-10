package com.ry_the_writer.chat;

import java.util.Scanner;

public class ClientDriver
{
	Client c;
	public ClientDriver()
	{
		System.out.println("Enter the IP address of the server.");
		Scanner scan = new Scanner(System.in);
		
		String ip = scan.next();
		System.out.println("Enter the port.");
		int port = scan.nextInt();
		System.out.println("Enter your name.");
		c = new Client(scan.next(), ip, port);
		String message = "";
		new Thread(()->{
			while(true)
			{
				String s = c.checkMessages();
				if(s != null)
					System.out.println(s);
			}
		}).start();
		scan.useDelimiter("\n");
		message = scan.next();
		while(!message.equals("/EXIT"))
		{
			c.sendMessage(message);
			message = scan.next();
		}
		scan.close();
	}
	public static void main(String[] args)
	{
		new ClientDriver();
	}
}
