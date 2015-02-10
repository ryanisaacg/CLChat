package com.ry_the_writer.chat;

import java.net.UnknownHostException;

/**
 * @author Ryan Goldstein
 */
public class UniversalDriver
{
	public static void main(String[] args) throws UnknownHostException
	{
		if(args.length != 0 && args[0].equals("server"))
			ServerDriver.main(args);
		else
			ClientDriver.main(args);
	}
}
