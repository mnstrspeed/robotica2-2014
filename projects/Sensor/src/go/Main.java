package go;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import robotica2.model.DiagnosticsInterface;
import robotica2.model.OccupancyMap;
import lejos.hardware.Button;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.Pose;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;

/**
 * Example leJOS Project with an ant build file
 *
 */
public class Main extends UnicastRemoteObject implements DiagnosticsInterface {
	
	protected Main() throws RemoteException 
	{
		super();
	}

	public static void main(String[] args) throws InterruptedException, RemoteException, MalformedURLException {
		System.setProperty("java.rmi.server.hostname", "10.0.1.1");
		LocateRegistry.createRegistry(12345);
		
		Main server = new Main();
		Naming.rebind("//localhost:12345/HelloServer", server);
		
		server.run();
		Button.waitForAnyPress();
		System.exit(0);
	}
	
	private Mapper mapper;
	
	public void run()
	{
		int count = 0;
		RegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.D);
		SensorModes sensor = new EV3UltrasonicSensor(SensorPort.S3);
		
		SampleProvider distance= sensor.getMode("Distance");
		float[] sample = new float[distance.sampleSize()];
		ArrayList<Float> results = new ArrayList();
		motor.setSpeed(40);
		System.out.println("ROTATING first time ...");
		while(count < 1) {
			for (int i = 1; i <= 36; i++) {
				motor.rotate(5);
				distance.fetchSample(sample, 0);
				results.add( sample[0]);
			}
			count++;
		}
		System.out.println("DONE!");
		// Print scan result
		for(int i = 0; i < results.size(); i++)
			System.out.println("RESULT NR:" + (i+1) + " " + results.get(i));
		
		mapper = new Mapper(results);
		System.out.println("Enter the Matrix");
		mapper.enterTheMatrix();
		System.out.println("Print matrix");
	}

	@Override
	public OccupancyMap getOccupancyGrid() 
	{
		boolean[][] map = mapper.getMap();
		return new OccupancyMap(map, 10);
	}

}
