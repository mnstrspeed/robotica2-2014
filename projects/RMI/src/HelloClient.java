import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class HelloClient implements Observer<String>, Serializable {
	public static void main(String[] args) 
			throws MalformedURLException, RemoteException, NotBoundException {

		Hello hello = (Hello) Naming.lookup("//10.0.1.1:12345/HelloServer");
		hello.addObserver((Observer<String>)new HelloClient());
		
		/*
		 * Exception in thread "main" java.rmi.ServerException: RemoteException occurred in server thread; nested exception is: 
	java.rmi.UnmarshalException: error unmarshalling arguments; nested exception is: 
	java.lang.ClassNotFoundException: HelloClient (no security manager: RMI class loader disabled)
	at sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:353)
	at sun.rmi.transport.Transport$1.run(Transport.java:177)
	at sun.rmi.transport.Transport$1.run(Transport.java:174)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.rmi.transport.Transport.serviceCall(Transport.java:173)
	at sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:556)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:811)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:670)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
	at sun.rmi.transport.StreamRemoteCall.exceptionReceivedFromServer(StreamRemoteCall.java:276)
	at sun.rmi.transport.StreamRemoteCall.executeCall(StreamRemoteCall.java:253)
	at sun.rmi.server.UnicastRef.invoke(UnicastRef.java:162)
	at java.rmi.server.RemoteObjectInvocationHandler.invokeRemoteMethod(RemoteObjectInvocationHandler.java:194)
	at java.rmi.server.RemoteObjectInvocationHandler.invoke(RemoteObjectInvocationHandler.java:148)
	at com.sun.proxy.$Proxy0.addObserver(Unknown Source)
	at HelloClient.main(HelloClient.java:15)
Caused by: java.rmi.UnmarshalException: error unmarshalling arguments; nested exception is: 
	java.lang.ClassNotFoundException: HelloClient (no security manager: RMI class loader disabled)
	at sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:313)
	at sun.rmi.transport.Transport$1.run(Transport.java:177)
	at sun.rmi.transport.Transport$1.run(Transport.java:174)
	at java.security.AccessController.doPrivileged(Native Method)
	at sun.rmi.transport.Transport.serviceCall(Transport.java:173)
	at sun.rmi.transport.tcp.TCPTransport.handleMessages(TCPTransport.java:556)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run0(TCPTransport.java:811)
	at sun.rmi.transport.tcp.TCPTransport$ConnectionHandler.run(TCPTransport.java:670)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)
	at java.lang.Thread.run(Thread.java:745)
Caused by: java.lang.ClassNotFoundException: HelloClient (no security manager: RMI class loader disabled)
	at sun.rmi.server.LoaderHandler.loadClass(LoaderHandler.java:393)
	at sun.rmi.server.LoaderHandler.loadClass(LoaderHandler.java:185)
	at java.rmi.server.RMIClassLoader$2.loadClass(RMIClassLoader.java:637)
	at java.rmi.server.RMIClassLoader.loadClass(RMIClassLoader.java:264)
	at sun.rmi.server.MarshalInputStream.resolveClass(MarshalInputStream.java:214)
	at java.io.ObjectInputStream.readNonProxyDesc(ObjectInputStream.java:1612)
	at java.io.ObjectInputStream.readClassDesc(ObjectInputStream.java:1517)
	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1771)
	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1350)
	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:370)
	at sun.rmi.server.UnicastRef.unmarshalValue(UnicastRef.java:325)
	at sun.rmi.server.UnicastServerRef.dispatch(UnicastServerRef.java:307)
	... 10 more

		 */
	}

	@Override
	public void update(String value)
	{
		System.out.println(value);
	}
}
