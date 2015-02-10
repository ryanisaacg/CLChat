package com.ry_the_writer.chat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ServerDriver
{
	public static void main(String[] args) throws UnknownHostException
	{
		System.out.println("Enter the port you wish to use.");
		Scanner scan = new Scanner(System.in);
		int port = scan.nextInt();
		scan.close();
		System.out.println(InetAddress.getLocalHost());
		new Server(port);
	}
}
