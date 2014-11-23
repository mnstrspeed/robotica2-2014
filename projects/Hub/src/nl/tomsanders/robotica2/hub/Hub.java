package nl.tomsanders.robotica2.hub;


import java.awt.Color;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import nl.tomsanders.robotica2.hub.layers.GridLayer;
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
		EnvironmentView view = new EnvironmentView();
		view.addLayer(new GridLayer(GridLayer.MILLIMETER, Color.DARK_GRAY));
		view.addLayer(new GridLayer(GridLayer.CENTIMETER, Color.GRAY));
		view.addLayer(new GridLayer(GridLayer.METER, Color.LIGHT_GRAY));
		view.addLayer(new OriginLayer());
		
		view.setVisible(true);
		
		// - Live sensor readings
		// - Recording data
	}
}
