import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;


public class HelloServer extends UnicastRemoteObject implements Hello
{
	private ArrayList<Observer> observers;
	
	protected HelloServer() throws RemoteException
	{
		super();
		this.observers = new ArrayList<Observer>();
	}

	public static void main(String[] args)
	{
		try
		{
			System.setProperty("java.rmi.server.hostname", "192.168.0.198");
			//System.setProperty("java.rmi.server.codebase", "...");
			LocateRegistry.createRegistry(12345);
			
			HelloServer hello = new HelloServer();
			Naming.rebind("//localhost:12345/HelloServer", hello);
			
			hello.run();
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		
		System.exit(0);
	}

	public void run() throws InterruptedException
	{
		System.out.println("Started");
		//while (!Button.ESCAPE.isDown())
		while (true)
		{
			//Button.waitForAnyPress();
			Thread.sleep(5000);
			
			System.out.println("Updating...");
			for (Observer observer : this.observers)
				observer.update(new Date().toString());
		}
	}

	@Override
	public void addObserver(Observer observer) throws RemoteException
	{
		this.observers.add(observer);
	}
}