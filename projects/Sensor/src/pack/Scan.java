package pack;

import java.util.ArrayList;

import javax.sound.sampled.Port;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;


public class Scan implements Runnable {

    private ArrayList<Integer> results = new ArrayList<>();
	public void run() {
    	while(!Thread.currentThread().isInterrupted()) {
    		SensorModes sensor = new EV3UltrasonicSensor(SensorPort.S3);

    		// get an instance of this sensor in measurement mode
    		SampleProvider distance= sensor.getMode("Distance");

    		// initialize an array of floats for fetching samples
    		float[] sample = new float[distance.sampleSize()];

    		// fetch a sample
    		distance.fetchSample(sample, 0);
    		for(int i = 0; i < sample.length; i++) {
    			System.out.println(sample[i]);
    		}
    	} 	
    }
	
	public void done_cycle(){
		
		results.clear();
	}
	
	public ArrayList<Integer> get_results() {
		return results;
	}
  }