package robotica2.model;

import java.rmi.RemoteException;

import lejos.robotics.navigation.Pose;

public interface DiagnosticsInterface extends java.rmi.Remote 
{
	public OccupancyMap getOccupancyGrid() throws RemoteException;
}
