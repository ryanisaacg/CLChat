package com.ry_the_writer.chat;
/**
 * Represent a chat message
 * @author Ryan Goldstein
 */
public class Message
{
	public String name;
	public String contents;
	
	public Message(String name, String contents)
	{
		this.name = name;
		this.contents = contents;
	}
	
	public String toString()
	{
		return name + ": " + contents + System.lineSeparator();
	}
}
