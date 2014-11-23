package nl.tomsanders.robotica2.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class LogService implements Runnable
{
	private ServerSocket serverSocket;
	private List<ConnectionHandler> connectionHandlers;
	
	private Thread serverThread;
	
	public LogService()
	{
		this.connectionHandlers = new ArrayList<ConnectionHandler>();
	}
	
	public void start()
	{
		this.serverThread = new Thread(this);
		this.serverThread.start();
	}

	@Override
	public void run()
	{
		try
		{
			this.serverSocket = new ServerSocket(7442);
			while (!this.serverSocket.isClosed())
			{
				// Block until new connection
				Socket connection = this.serverSocket.accept();
				
				ConnectionHandler handler = new ConnectionHandler(connection);
				this.connectionHandlers.add(handler);
				
				handler.start();
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		try
		{
			this.serverSocket.close();
			for (ConnectionHandler handler : this.connectionHandlers)
				handler.stop();
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	private class ConnectionHandler implements Runnable
	{
		private Socket connection;
		
		public ConnectionHandler(Socket connection)
		{
			this.connection = connection;
		}
		
		public void start()
		{
			new Thread(this).start();
		}
		
		public void stop() throws IOException
		{
			this.connection.close();
		}
		
		@Override
		public void run()
		{
			try
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(this.connection.getInputStream()));
				
				while (!this.connection.isClosed())
				{
					Log.getInstance().add(reader.readLine());
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
