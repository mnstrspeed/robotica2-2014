package nl.tomsanders.robotica2.hub;

import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import robotica2.model.DiagnosticsInterface;
import nl.tomsanders.robotica2.hub.layers.GridLayer;
import nl.tomsanders.robotica2.hub.layers.OccupancyMapLayer;
import nl.tomsanders.robotica2.hub.layers.OriginLayer;
import nl.tomsanders.robotica2.logging.Log;
import nl.tomsanders.robotica2.logging.LogService;

public class Hub 
{
	public static void main(String[] args) throws IOException, 
			ClassNotFoundException, InstantiationException, 
			IllegalAccessException, UnsupportedLookAndFeelException
	{
		LogView logView = new LogView();
		logView.setVisible(true);
		
		Log.getInstance().add("Starting log service...");
		LogService logService = new LogService();
		logService.start();
		Log.getInstance().add("Log service started");
		
		UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
		
		BrickManager manager = new BrickManager();
		manager.setVisible(true);
		manager.addListener(Hub::brickSelected);
	}
	
	public static void brickSelected(BrickEndpoint endpoint)
	{
		DiagnosticsInterface brickInterface;
		try 
		{
			brickInterface = (DiagnosticsInterface)
					Naming.lookup("//" + endpoint.getAddress().getHostAddress() + ":12345/HelloServer");
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
		
		OccupancyMapLayer mapLayer = new OccupancyMapLayer(brickInterface.getOccupancyGrid());
		
		EnvironmentView view = new EnvironmentView();
		view.addLayer(new GridLayer(GridLayer.MILLIMETER, Color.DARK_GRAY));
		view.addLayer(new GridLayer(GridLayer.CENTIMETER, Color.GRAY));
		view.addLayer(new GridLayer(GridLayer.METER, Color.LIGHT_GRAY));
		view.addLayer(mapLayer);
		view.addLayer(new OriginLayer());
		
		view.setVisible(true);
		
		while (true)
		{
			try 
			{
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) 
			{
				throw new RuntimeException(e);
			}
			
			// Refresh
			mapLayer.setMap(brickInterface.getOccupancyGrid());
			view.repaint();
		}
		
		// - Live sensor readings
		// - Recording data
	}
}
