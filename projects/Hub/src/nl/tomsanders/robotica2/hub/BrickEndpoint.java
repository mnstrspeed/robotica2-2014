package nl.tomsanders.robotica2.hub;

import java.net.InetAddress;

public class BrickEndpoint 
{
	private String name;
	private InetAddress address;
	
	public BrickEndpoint(String name, InetAddress address)
	{
		this.name = name;
		this.address = address;
	}
	
	public String getName() 
	{
		return this.name;
	}
	
	public InetAddress getAddress()
	{
		return this.address;
	}
}