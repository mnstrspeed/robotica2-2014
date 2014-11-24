import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class HelloClient
{
	public static void main(String[] args) 
			throws MalformedURLException, RemoteException, NotBoundException, InterruptedException 
	{
		DiagnosticsInterface iface = (DiagnosticsInterface)Naming.lookup("//192.168.0.198:12345/HelloServer");
		OccupancyGrid grid = iface.getOccupancyGrid();
	}
}
