
public interface Hello extends java.rmi.Remote {
	String sayHello() throws java.rmi.RemoteException;
	void addObserver(Observer<String> observer) throws java.rmi.RemoteException;
}
