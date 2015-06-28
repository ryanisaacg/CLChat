package com.ry_the_writer.chat;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Holds the data for the chat server
 * @author Ryan Goldstein
 */
public class Server
{
	private Vector<Socket> sockets;
	private ServerSocket server;
	
	public Server(int port)
	{
		sockets = new Vector<Socket>();
		try
		{
			server = new ServerSocket(port);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		//Check for new connections
		new Thread( () ->{
			while(true)
			{
				try
				{
					Socket s = server.accept();
					sockets.add(s);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
		//Read and send out messages
		new Thread( ()->{
			while(true)
			{
				Socket[] socketArray = new Socket[sockets.size()];
				for(Socket s : sockets.toArray(socketArray))
				{
					String message = checkMessages(s);
					if(message != null)
					{
						//messages.add(message);
						for(Socket socket : socketArray)
							if(socket != s)
								sendMessage(socket, message);
					}
				}
			}
		}).start();
	}
	
	private void sendMessage(Socket socket, String message)
	{
		byte[] messageBytes = new byte[message.length()];
		for(int i = 0; i < message.length(); i++)
			messageBytes[i] = (byte)message.charAt(i);
		try
		{
			BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
			out.write(messageBytes);
			out.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private String checkMessages(Socket socket)
	{
		String message = null;
		//Read message data
		try
		{
			InputStream reader = socket.getInputStream();
			if(reader.available() > 0)
			{
				int length = reader.read();
				if(length != - 1)
				{
					byte[] b = new byte[length];
					reader.read(b);
					message = new String(b);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return message;
	}
}
