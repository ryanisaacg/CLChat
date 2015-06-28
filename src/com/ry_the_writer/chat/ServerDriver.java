package com.ry_the_writer.chat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ServerDriver
{
	public static void main(String[] args) throws UnknownHostException
	{
		System.out.println("Enter the port you wish to use.");
		Scanner scan = new Scanner(System.in);
		int port = scan.nextInt();
		System.out.println("See IP address of current machine? (Y/N)");
		if(scan.next().equalsIgnoreCase("Y"))
			try
			{
				//Check all network interfaces
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while(interfaces.hasMoreElements())
				{
					//Check all addresses
					NetworkInterface inter = interfaces.nextElement();
					Enumeration<InetAddress> addr = inter.getInetAddresses();
					while(addr.hasMoreElements())
					{
						String address = addr.nextElement().getHostAddress();
						//Make sure it the address is an external local address
						if(Pattern.matches("192\\.168\\.0\\..+", address))
							System.out.println(address);
					}
				}
			} catch (SocketException e)
			{
				e.printStackTrace();
			}
		scan.close();
		new Server(port);
	}
}
