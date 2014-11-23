package nl.tomsanders.robotica2.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Log extends Observable
{
	public static class Entry
	{
		public long time;
		public String message;
		
		public Entry(long time, String message)
		{
			this.time = time;
			this.message = message;
		}
	}
	
	private static Log instance = new Log();
	
	public static Log getInstance()
	{
		if (instance == null)
			instance = new Log();
		return instance;
	}
	
	private ArrayList<Entry> log;
	
	public Log()
	{
		this.log = new ArrayList<Entry>();
	}
	
	public void add(String message)
	{
		this.log.add(new Entry(System.currentTimeMillis(), message));
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public List<Entry> getEntries()
	{
		return this.log;
	}
}
