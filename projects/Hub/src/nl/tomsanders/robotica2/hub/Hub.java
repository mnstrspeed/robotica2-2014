package nl.tomsanders.robotica2.hub;


import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import nl.tomsanders.robotica2.hub.layers.GridLayer;
import nl.tomsanders.robotica2.hub.layers.OccupancyMapLayer;
import nl.tomsanders.robotica2.hub.layers.OriginLayer;
import nl.tomsanders.robotica2.logging.Log;
import nl.tomsanders.robotica2.logging.LogService;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

public class Hub 
{
	public static void main(String[] args)
	{
		try 
		{
			LogView logView = new LogView();
			logView.setVisible(true);
			
			Log.getInstance().add("Starting log service...");
			LogService logService = new LogService();
			logService.start();
			Log.getInstance().add("Log service started");
			
			BrickInfo brickInfo = null;
			Log.getInstance().add("Starting Brick discovery");
			for (BrickInfo info : BrickFinder.discover())
			{
				Log.getInstance().add("Found " + info.getName() + 
						" at " + info.getIPAddress());
				brickInfo = info;
			}
			
			Log.getInstance().add("Connecting to " + brickInfo.getName());
			
			Socket socket = new Socket(brickInfo.getIPAddress(), 4242);
			Log.getInstance().add("Connected");
			
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			
			try
			{
				OccupancyMap map = (OccupancyMap)input.readObject();
				Log.getInstance().add("Read map");
				
				EnvironmentView view = new EnvironmentView();
				view.addLayer(new GridLayer(GridLayer.MILLIMETER, Color.DARK_GRAY));
				view.addLayer(new GridLayer(GridLayer.CENTIMETER, Color.GRAY));
				view.addLayer(new GridLayer(GridLayer.METER, Color.LIGHT_GRAY));
				view.addLayer(new OriginLayer());
				view.addLayer(new OccupancyMapLayer(map));
				
				view.setVisible(true);
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		// TODO:
		// - Brick selection
		// - Live sensor readings
		// - Recording data
	}
}
