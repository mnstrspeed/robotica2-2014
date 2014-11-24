import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;


public class HelloClient implements Observer, Serializable 
{
	public static void main(String[] args) 
			throws MalformedURLException, RemoteException, NotBoundException, InterruptedException 
	{
		Hello hello = (Hello) Naming.lookup("//127.0.0.1:12345/HelloServer");
		System.out.println("Found!!1");
		
		Observer observer = new HelloClient();
		hello.addObserver(observer);
		
		System.out.println("Started client");
		while (true)
			Thread.sleep(100);
		
		/*
		 * Exception in thread "main" java.rmi.ServerException: RemoteException occurred in server thread; nested exception is: 
			java.rmi.UnmarshalException: error unmarshalling arguments; nested exception is: 
			java.lang.ClassNotFoundException: HelloClient (no security manager: RMI class loader disabled)
		 */
	}

	@Override
	public void update(String value)
	{
		System.out.println(value);
	}
}
