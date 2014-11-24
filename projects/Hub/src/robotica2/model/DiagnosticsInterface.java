package robotica2.model;

import lejos.robotics.navigation.Pose;

public interface DiagnosticsInterface extends java.rmi.Remote 
{
	public OccupancyMap getOccupancyGrid();
	public Pose getPose();
}
