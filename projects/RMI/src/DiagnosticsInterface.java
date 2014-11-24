import lejos.robotics.navigation.Pose;


public interface DiagnosticsInterface extends java.rmi.Remote 
{
	public OccupancyGrid getOccupancyGrid();
	public Log getLogData();
	public Pose getPose();
}
