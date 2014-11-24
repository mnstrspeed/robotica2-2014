package go;

import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import go.*;

/**
 * Example leJOS Project with an ant build file
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
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
		Mapper mapper = new Mapper(results);
		System.out.println("Enter the Matrix");
		mapper.enterTheMatrix();
		System.out.println("Print matrix");
		mapper.print();
		Button.waitForAnyPress();
	}
}
