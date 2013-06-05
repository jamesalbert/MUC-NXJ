import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class park extends line {
	// is robot in parking mode?
	public static int checkParkingMode() {
		return control.isParking;
	}

	// update parking space on LCD
	public static void updateSpaceDisplay() {
		LCD.drawString("To Space: "+control.desiredSpaces[control.toLotAndSpace],0,4);
	}

	/*What the robot should expect to hit. This must match the
	output of displayOurNextToLot() in order for the robot
	to park.*/
	public static void expectedLotDisplay() {
		LCD.drawString("Expect lot " + control.onOurWayTo,0,2);
	}

	// correct angle in parking mode
	public static void correctAngle(int in, int out) {
		control.correctingAngle = 1;
		while (control.correctingAngle == 1) {
			if (in == left && control.leftEye.getColorID() == control.blue) {
				control.swerve(out);
			}
			else if (in != right && control.leftEye.getColorID() == control.black) {
				speedIs(control.fullSpeed);
				go();
				control.correctingAngle = 0;
			}
			if (in == right && control.rightEye.getColorID() == control.blue) {
				control.swerve(out);
				//Delay.msDelay(80);
			}
			else if (in != left && control.rightEye.getColorID() == control.black) {
				speedIs(control.fullSpeed);
				go();
				control.correctingAngle = 0;
			}
		}
		go();
	}

	// determine parking side
	public static void getFrontSide(ColorSensor sensor, int in, int out) {
		speedIs(control.deflectSpeed);
		veer(in);
		Delay.msDelay(control.parkLineTime);
		if (sensor.getColorID() == control.white || sensor.getColorID() == control.yellow) {
			veer(out);
			Delay.msDelay(control.parkLineTime);
			control.atTheFirstSpace = 1;
		}
		else {
			veer(out);
			Delay.msDelay(control.parkLineTime);
		}
	}

	// position robot for parking
	public static void getInFront(int in, int out) {
		while(control.atTheFirstSpace == 0) {
			speedIs(control.findSpotSpeed);
			reverse();
			Delay.msDelay(control.checkTime);
			suspend();
			if (in == control.left) {
				getFrontSide(control.leftEye,in,out);
			}
			else {
				getFrontSide(control.rightEye,in,out);
			}
		}
		control.atTheFirstSpace = 0;
		speedIs(control.fullSpeed);
		go();
	}

	//Parking function
	public static void parkIn(int in,int out) {
		speedIs(control.fullSpeed);
		control.isParking = 1;
		correctAngle(in,out);
		suspend();
		getInFront(in,out);
		Delay.msDelay((control.nextSpaceTime*(control.desiredSpaces[control.toLotAndSpace]-1)) + control.halfSpaceTime);
		biGo(in);
		Delay.msDelay(control.turnTime);
		go();
		Delay.msDelay(control.parkTime);
		speedIs(control.fullSpeed);
		suspend();
		Delay.msDelay(control.fiveSeconds);
		//control.isParking = 0;
		if (control.desiredSpaces[control.toLotAndSpace+1] != null) {
			++control.toLotAndSpace;
		}
		reverse();
		reverseBeeps();
		speedIs(control.fullSpeed);
		biGo(out);
		Delay.msDelay(control.turnTime);
		if (Arrays.binarySearch(control.desiredLots,2) >= 0 && Arrays.binarySearch(control.desiredLots,3) >= 0 && control.desiredLots[control.toLotAndSpace] == 3 && control.currentNode == 11) {
			//++control.toLotAndSpace;
			++control.onOurWayTo;
			control.veer(control.right);
			Delay.msDelay(100);
			expectedLotDisplay();
		}
		else {
			control.finishedParking = 1;
			control.isParking = 0;
			go();
		}
	}

	//Displays the lot we're going to.
	public static void displayOurNextToLot() {
		LCD.drawString("Going to lot " + control.desiredLots[control.toLotAndSpace],0,1);
	}

	//Returns the next lot to hit.
	public static int ourNextLot() {
		return control.desiredLots[control.toLotAndSpace];
	}

	//Returns the previously visited lot.
	public static int ourPreviousLot() {
		if (control.toLotAndSpace != 0) {
			return control.desiredLots[control.toLotAndSpace-1];
		}
		else {
			return 0;
		}
	}

	/*What the robot should expect to hit. This must match the
	output of displayOurNextToLot() in order for the robot
	to park.
	public static void expectedLotDisplay() {
		LCD.drawString("Expect lot " + control.onOurWayTo,0,2);
	}*/

	//Confirms that this is a designated parking space.
	public static boolean isOurLot() {
		if (control.onOurWayTo == control.desiredLots[control.toLotAndSpace]) {
			return true;
		}
		else {
			return false;
		}
	}

	//Used to increment our next lot.
	public static void nextLot(int next) {
		control.onOurWayTo = next;
	}

	//Checks to see which side we should park in.
	public static void checkSpace(int rightColor, int leftColor, int currentNode) {
		if (rightColor == Color.BLUE || leftColor == Color.BLUE) {
			if (Arrays.binarySearch(control.leftLots,control.desiredLots[control.toLotAndSpace]) >= 0) {
				parkIn(control.left,control.right);
			}
			else if (Arrays.binarySearch(control.rightLots,control.desiredLots[control.toLotAndSpace]) >= 0) {
				parkIn(control.right,control.left);
			}
		}
	}

	//Does the same as checkSpace(), but returns a value used for parkingModeSteer()
	public static int checkLotSide(int currentSpace) {
		if (Arrays.binarySearch(control.leftLots,currentSpace) >= 0) {
			return 1;
		}
		else if (Arrays.binarySearch(control.rightLots,currentSpace) >= 0) {
			return 2;
		}
		else {
			return 0;
		}
	}

	public static void main (String[] args) {
		park driver = new park();
		while (control.robotOn == 1) {
			if (Button.ESCAPE.isDown()) {
				control.robotOn = 0;
			}
		}
	}
}
