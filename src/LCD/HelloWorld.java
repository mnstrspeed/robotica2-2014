

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;


/**
 * Example leJOS EV3 Project with an ant build file
 *
 */
public class HelloWorld {

	private static LCD koen;
	
	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();

		koen = new LCD(ev3.getTextLCD());
		Keys keys = ev3.getKeys();

		koen.add("Hello world");
		
		// keys.waitForAnyPress();
		
		DifferentialPilot pilot = new DifferentialPilot(175.929, 135.0,
				new EV3LargeRegulatedMotor(MotorPort.B),
				new EV3LargeRegulatedMotor(MotorPort.C));
		
		OdometryPoseProvider poseProvider = new OdometryPoseProvider(pilot);
		
		pilot.steer(0, 180);
		koen.add("Heading: " + poseProvider.getPose().getHeading());
		pilot.steer(50, 180);
		koen.add("Heading: " + poseProvider.getPose().getHeading());
		
		keys.waitForAnyPress();
	}
	
}