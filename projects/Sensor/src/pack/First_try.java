package pack;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import pack.*;

/**
 * Example leJOS Project with an ant build file
 *
 */
public class First_try {

	public static void main(String[] args) throws InterruptedException {
		// boolean done;
		/*boolean right = true;
		int angle = 0;
		RegulatedMotor motor = new EV3LargeRegulatedMotor(MotorPort.D);
		motor.setSpeed(40);
		//Motor.C.setSpeed(40);
		Scan test = new Scan();
		Mapper map = new Mapper();
		Thread scan = new Thread(test);
		// scan.start();
		Thread wait = new Thread(new Wait());
		//wait.start();
		while(true) {
			// done = false;
			if(right) {
				for(int i = 1; i <= 36; i++) {
					// done = false;
					angle = 5*i;
					motor.rotate(-5);
					//Motor.C.rotate(-5);
					// done = true;
					map.rawValues(test.get_results());
					test.done_cycle();
				}
				right = false;
			}
			else {
				for(int i = 36; i >= 1; i--) {
					angle = 5*i;
					motor.rotate(-5);
					//Motor.C.rotate(5);
					//done = true;
					map.rawValues(test.get_results());
					test.done_cycle();
				}
				right = true;
			}
			if(Wait.press) {
				scan.interrupt();
				wait.interrupt();
				break;
			}
		
	   }*/
		
	}
}
