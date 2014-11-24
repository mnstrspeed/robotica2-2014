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
import robotica2.model.OccupancyMap;
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
		if (endpoint == null)
			return;
		
		DiagnosticsInterface brickInterface;
		try 
		{
			brickInterface = (DiagnosticsInterface)
					Naming.lookup("//" + endpoint.getAddress().getHostAddress() + ":12345/HelloServer");
			
			OccupancyMap map = brickInterface.getOccupancyGrid();
			for (int i = 0; i < map.getMap().length; i++)
				for (int j = 0; j < map.getMap().length; j++)
					if (map.getMap()[i][j])
						System.out.println("WOW");
			
			OccupancyMapLayer mapLayer = new OccupancyMapLayer(map);
			
			EnvironmentView view = new EnvironmentView();
			view.addLayer(new GridLayer(GridLayer.MILLIMETER, Color.DARK_GRAY));
			view.addLayer(new GridLayer(GridLayer.CENTIMETER, Color.GRAY));
			view.addLayer(new GridLayer(GridLayer.METER, Color.LIGHT_GRAY));
			view.addLayer(mapLayer);
			view.addLayer(new OriginLayer());
			
			view.setVisible(true);
			
			/*
			while (true)
			{
				Thread.sleep(2000);
				
				// Refresh
				OccupancyMap grid = brickInterface.getOccupancyGrid();
				//mapLayer.setMap(grid);
				//view.repaint();
			}
			*/
			
			// - Live sensor readings
			// - Recording data
		} 
		catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	}
}
