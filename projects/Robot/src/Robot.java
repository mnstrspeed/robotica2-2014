import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RangeFinderAdaptor;
import lejos.robotics.RangeScanner;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RotatingRangeScanner;
import lejos.robotics.localization.MCLPoseProvider;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.mapping.RangeMap;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	public static final double WHEEL_DIAMETER = 56; // mm
	public static final double TRACK_WIDTH = 110; // mm
	private static final int PORT = 12543;

	private ServerSocket server;
	private Socket connection;

	private RegulatedMotor leftMotor;
	private RegulatedMotor rightMotor;
	private RegulatedMotor ultrasonicSensorMotor;
	private EV3UltrasonicSensor ultrasonicSensor;
	
	private DifferentialPilot pilot;
	// Goal of the project:
	private RangeMap map; // a *shared* world model; and
	private PoseProvider poseProvider; // our location on the map

	// Mission: cooperating vacuum cleaning robots (explore entire space)

	public static void main(String[] args) {
		System.out.println("Running program...");
		Button.waitForAnyPress();
		new Robot().run();
	}

	public void run() {
		this.prepareRobot();
	}

	private void prepareRobot() {
		this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.B);
		this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.C);
		this.ultrasonicSensorMotor = new EV3MediumRegulatedMotor(MotorPort.D);
		this.ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3);

		this.pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH,
				this.leftMotor, this.rightMotor);
		this.poseProvider = new OdometryPoseProvider(this.pilot);

		this.initializeCommunications();
		
		
		
		// RangeScanner rangeScanner = new
		// RotatingRangeScanner(this.ultrasonicSensorMotor,
		// new RangeFinderAdaptor(this.ultrasonicSensor.getMode("distance")));
		// rangeScanner.setAngles(new float[] { -45f, 0f, 45f });
		// this.poseProvider = new MCLPoseProvider(this.pilot, rangeScanner,
		// null, 200, 0);
	}

	private void initializeCommunications() {
		try {
			BrickInfo info = BrickFinder.discover()[0];

			Socket socket = new Socket(info.getIPAddress(), PORT);
			this.connection = socket;
			
			this.update();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	private void update() {
		try {
			ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			
			output.writeObject(new Handshake());
			output.flush();
			
			try {
				Handshake h = (Handshake)input.readObject();
				System.out.println(h.getTime());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
