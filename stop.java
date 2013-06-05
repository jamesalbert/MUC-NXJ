import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class stop extends park {
	public static int inParkingZone = checkParkingMode();

	//Shows where we are.
	public static void updateNodeDisplay() {
		LCD.drawString("Current Node "+control.currentNode,0,0);
	}

	//Shows the current speed.
	public static void updateSpeedDisplay() {
		LCD.drawString("Speed: "+currentSpeed(),0,5);
	}

	//Returns where we are.
	public static int getCurrentNode() {
		return control.currentNode;
	}

	//Updates where we are.
	public static void updateNode() {
		++control.currentNode;
		updateNodeDisplay();
	}

	//Where to turn left.
	public static int[] leftTurns = {
		1,2,3,10,12,13,15,16,18,19,20,21,22,24,
		27,28,29,30,31,34,36,41,46,47,49,52,55
	};

	//Where to turn right.
	public static int[] rightTurns = {43,44,45};

	//Where to just keep going.
	public static int[] straightAways = {
		4,5,6,7,8,9,
		11,14,17,23,
		25,26,32,33,
		35,37,38,39,
		40,48,50,51,
		53,54
	};

	//Where to start parking mode.
	public static int[] parkingMode = {
		3,11,14,20,
		25,27,43,44,
		47
	};

	//Where to update the next lot to go to.
	public static int[] nextTo = {4,12,15,21,26,28,44,45,48};

	/*Goes over each array and bases its turn on whether the node is in or not.
	nine_or_ten determines whether to increment control.currentNode twice or not.*/
	public static void checkDirections(int ten, int nine) {
		if (Arrays.binarySearch(leftTurns, control.currentNode) >= 0) {
			turnLeft();
		}
		else if (Arrays.binarySearch(rightTurns, control.currentNode) >= 0) {
			turnRight();
		}
		else if (Arrays.binarySearch(straightAways, control.currentNode) >= 0){
			stopAndGo();
		}
		else if (control.currentNode == 42) {
			control.suspend();
			Delay.msDelay(control.fiveSeconds);
			control.swerve(control.left);
			//control.go();
			Delay.msDelay(200);
		}
		if (Arrays.binarySearch(parkingMode, control.currentNode) >= 0){
			inParkingZone = 1;
			control.finishedParking = 0;
		}
		else {
			inParkingZone = 0;
		}
		/*if (control.currentNode == 55) {
			control.robotOn = 0;
		}*/
		if (Arrays.binarySearch(nextTo, control.currentNode) >= 0) {
			++control.onOurWayTo;
		}
		if (ten == 3 && control.currentNode == 11 && Arrays.binarySearch(control.desiredLots,2) <= 0) {
			++control.onOurWayTo;
			//++control.toLotAndSpace;
		}
		else if (nine == 2 && control.currentNode == 13) {
			++control.onOurWayTo;
		}
		if (control.currentNode == 54) {
			control.onOurWayTo = 55;
		}
	}

	//Checks to see if parking mode is on.
	public static boolean inParkingMode() {
		if (inParkingZone == 1) {
			return true;
		}
		else {
			return false;
		}
	}

	//Displays if parking mode is on or off.
	public static void updateParkingDisplay() {
		if (inParkingMode() == true) {
			LCD.drawString("on park-mode",0,3);
		}
		else {
			LCD.drawString("no park-mode",0,3);
		}
	}

	//Initial tasks for the robot.
	public static void warmUp() {
		/*Arrays.sort(control.desiredLots);
		for (int i = 0; i <=4; i++) {
			int pos = Arrays.binarySearch(control.desiredLots,control.desiredLotsCopy[i]);
			control.desiredSpaces[pos] = control.desiredSpacesCopy[i];
		}*/
		//Arrays.sort(control.desiredSpaces);
		updateNodeDisplay();
		speedIs(control.fullSpeed);
		go();
	}

	//What to do when the robot reads red.
	public static void actOnRed() {
		updateNode();
		updateNodeDisplay();
		checkDirections(ourNextLot(),ourPreviousLot());
	}

	public static void main (String[] args) {
		stop driver = new stop();
		while(control.robotOn == 1) {
			if (Button.ESCAPE.isDown()) {
				control.robotOn = 0;
			}
		}
	}
}
