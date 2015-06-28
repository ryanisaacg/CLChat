package com.ry_the_writer.chat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Holds data for a client
 * @author Ryan Goldstein
 */
public class Client
{
	private final String name;
	private LinkedList<Message> messages;
	private Socket socket;
	
	public Client(String name, InetAddress ip, int port)
	{
		this.name = name;
		try
		{
			socket = new Socket(ip, port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		messages = new LinkedList<>();
	}
	
	public Client(String name, String ip, int port)
	{
		this.name = name;
		try
		{
			InetAddress address = InetAddress.getByName(ip);
			socket = new Socket(address, port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		messages = new LinkedList<>();
	}
	
	public String checkMessages()
	{
		String message = null;
		//Read message data
		try
		{
			BufferedInputStream reader;
			if(socket.isConnected())
			{
				reader = new BufferedInputStream(socket.getInputStream());
				if(reader.available() > 0)
					message = "";
				while(reader.available() > 0)
				{
					message += (char)reader.read();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//Process message data
		if(message != null)
		{
			String[] received = message.split(System.lineSeparator());
			for(int i = 0; i < received.length; i++)
			{
				String name, contents;
				name = received[i].substring(0, received[i].indexOf(':'));
				contents = received[i].substring(received[i].indexOf(':'));
				messages.add(new Message(name, contents));
			}
		}
		return message;
	}
	
	public void sendMessage(String message)
	{
		message = name + ":" + message + "\n";
		byte[] messageBytes = new byte[message.length()];
		for(int i = 0; i < message.length(); i++)
			messageBytes[i] = (byte)message.charAt(i);
		try
		{
			if(socket.isConnected())
			{
				OutputStream out = socket.getOutputStream();
				out.write(message.length());
				out.write(message.getBytes());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String toString()
	{
		String str = "";
		for(Message m : messages)
			str += m.toString();
		return str;
	}
}
