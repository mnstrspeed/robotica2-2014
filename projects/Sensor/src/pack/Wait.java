package pack;

import lejos.hardware.Button;

public class Wait implements Runnable {
	static boolean press = false;
	
    public void run(){
    	while(!Thread.currentThread().isInterrupted()) {
    		Button.waitForAnyPress();
        	press = true;
    	}	
    }
  }