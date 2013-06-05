import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class line extends control {
	/*veers in the opposite direction that the side sensor that reads control.white is on.
	Line deflection*/
	public static void steer(int rightColor, int leftColor) {
		if (rightColor == control.white || rightColor == control.blue || rightColor == control.red || rightColor == control.yellow || rightColor == control.green) {
			//speedUp();
			veer(control.left);
		}
		else if (leftColor == control.white || leftColor == control.blue || leftColor == control.red || leftColor == control.yellow || leftColor == control.green) {
			//speedUp();
			veer(control.right);
		}
		else {
			go();
		}
	}

	//Check to see if the robot needs to slow down.
	public static void checkSpeed(int rightColor, int leftColor) {
		if (rightColor == control.yellow) {
			//slowDown();
			veer(control.left);
		}
		else if (leftColor == control.yellow) {
			//slowDown();
			veer(control.right);
		}
		else {
			go();
		}
	}

	//Make sure to hug the road when leaving node 42.
	public static void handleNode42(int leftColor) {
		if (control.currentNode == 42 && leftColor == control.black) {
			veer(control.left);
		}
		else if (control.currentNode == 42 && (/*leftColor == control.yellow || */leftColor == control.white)) {
			veer(control.right);
		}
	}

	//Once we're in a parking zone, swerve to the side that the parking lot is on.
	public static void parkingModeSteer(int rightColor, int leftColor, int lotSide) {
		if (lotSide == control.left) {
			if (leftColor == control.black) {
				swerve(control.left);
			}
		}
		else if (lotSide == control.right) {
			if (rightColor == control.black) {
				swerve(control.right);
			}
		}
		else {
			speedIs(control.fullSpeed);
			go();
		}
	}


	public static void main (String[] args) {
		line driver = new line();
		while (control.robotOn == 1) {
			if (Button.ESCAPE.isDown()) {
				control.robotOn = 0;
			}
		}
	}
}
