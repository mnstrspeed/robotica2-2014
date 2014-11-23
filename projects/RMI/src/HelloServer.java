import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;


public class HelloServer extends UnicastRemoteObject implements Hello
{
	private ArrayList<Observer<String>> observers;
	
	protected Program() throws RemoteException
	{
		super();
		
		this.observers = new ArrayList<Observer<String>>();
	}

	public static void main(String[] args)
	{
		try
		{
			System.setProperty("java.rmi.server.hostname", "10.0.1.1");
			LocateRegistry.createRegistry(12345);
			
			HelloServer hello = new HelloServer();
			Naming.rebind("//localhost:12345/HelloServer", hello);
			
			hello.run();
		}
		catch (Exception ex)
		{
			for (int i = 0; i < 8; i++)
				LCD.drawString(ex.getMessage().substring(i * 18, (i + 1) * 18), 0, i);
		}
		
		Button.waitForAnyPress();
		System.exit(0);
	}

	public void run()
	{
		LCD.drawString("Started", 0, 0);
		while (!Button.ESCAPE.isDown())
		{
			Button.waitForAnyPress();
			for (Observer<String> observer : this.observers)
				observer.update(new Date().toString());
		}
		System.exit(0);
	}

	@Override
	public String sayHello() throws RemoteException
	{
		return "This is so fat";
	}

	@Override
	public void addObserver(Observer<String> observer) throws RemoteException
	{
		this.observers.add(observer);
	}
}