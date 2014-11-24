import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import lejos.robotics.navigation.Pose;


public class Server extends UnicastRemoteObject implements DiagnosticsInterface
{
	private static final long serialVersionUID = -8755830169075236700L;

	protected Server() throws RemoteException 
	{
		super();
	}

	public static void main(String[] args)
	{
		try
		{
			System.setProperty("java.rmi.server.hostname", "10.0.1.1");
			//System.setProperty("java.rmi.server.codebase", "...");
			LocateRegistry.createRegistry(12345);
			
			Server hello = new Server();
			Naming.rebind("//localhost:12345/HelloServer", hello);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}

	@Override
	public OccupancyGrid getOccupancyGrid() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Log getLogData()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pose getPose() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}