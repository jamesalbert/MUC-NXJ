import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class control extends menu{
	// mini urban challenge config
	public static int robotOn = menu.robotOn;
	public static int turnTime = 680;//925;
	public static int upTime = 1480;
	public static int findSpotSpeed = 50;
	public static int deflectSpeed = 50;
	public static int left = 1;
	public static int right = 2;
	public static int black = Color.BLACK;
	public static int white = Color.WHITE;
	public static int yellow = Color.YELLOW;
	public static int blue = Color.BLUE;
	public static int red = Color.RED;
	public static int green = Color.GREEN;
	public static int toLotAndSpace = 0;
	public static int correctingAngle = 0;
	public static int oneSecond = 850;
	public static int fiveSeconds = 1000;
	public static int atTheFirstSpace = 0;
	public static int finishedParking = 1;
	public static Integer[][] parkingData = menu.getLotInput();
	public static Integer[] desiredLots = parkingData[0];
	public static Integer[] desiredLotsCopy = desiredLots;
	public static Integer[] desiredSpaces = parkingData[1];
	public static Integer[] desiredSpacesCopy = desiredSpaces;
	public static int[] leftLots = {1,2,9,10};
	public static int[] rightLots = {3,4,5,6,7,8};
	public static ColorSensor rightEye = new ColorSensor(SensorPort.S1);
	public static ColorSensor leftEye = new ColorSensor(SensorPort.S4);
	public static ColorSensor centerEye = new ColorSensor(SensorPort.S2);
	//Change together don't forget!!!
	public static int currentNode = 0;
	public static int onOurWayTo = 1;
	public static int isParking = 0;
	public static int fullSpeed = 360;//488;
	public static int slowSpeed = 216;
	public static int lessSpeed = fullSpeed-100;
	public static int swerveTime = 425;
	public static int parkSpeed = 360;
	public static int parkTime = 1750;//1750;
	public static int beepTime = 293;
	public static int nextSpaceTime = 1695;
	public static int halfSpaceTime = nextSpaceTime/2;
	public static int stopTime = 1000;
	public static int parkLineTime = 750;
	public static int checkTime = 375;
	public static long startTime = System.currentTimeMillis();

	// timer
	public static void timer() {
		long timeDiff = System.currentTimeMillis();
		int newTime = (((int) timeDiff)-((int) startTime))/1000;
		LCD.drawString("Time: "+newTime,0,6);
	}

	// set motor speed (both motors)
	public static void speedIs(int speed) {
		Motor.A.setSpeed(speed);
		Motor.C.setSpeed(speed);
	}

	// go (forward)
	public static void go() {
		Motor.A.forward();
		Motor.C.forward();
	}

	// slow down on yellow
	public static void slowDown() {
		speedIs(slowSpeed);
	}

	// speed up on white
	public static void speedUp() {
		speedIs(fullSpeed);
	}

	// reverse (backwards)
	public static void reverse() {
		Motor.A.backward();
		Motor.C.backward();
	}

	// turn left
	public static void goLeft() {
		Motor.A.forward();
		Motor.C.backward();
	}

	// turn right
	public static void goRight() {
		Motor.A.backward();
		Motor.C.forward();
	}

	// turn either direction
	public static void biGo(int dir) {
		if (dir == 1) {
			goLeft();
		}
		else if (dir == 2) {
			goRight();
		}
	}

	// reverse left
	public static void reverseLeft() {
		Motor.A.backward();
		Motor.C.forward();
	}

	// stop
	public static void suspend() {
		Motor.A.stop(true);
		Motor.C.stop();
	}

	// get current speed
	public static int currentSpeed() {
		return Motor.A.getSpeed();
	}

	// turn right on stop sign
	public static void turnRight() {
		speedIs(360);
		suspend();
		Delay.msDelay(stopTime);
		go();
		Delay.msDelay(upTime);
		goRight();
		Delay.msDelay(turnTime);
		go();
	}

	// turn left on stop sign
	public static void turnLeft() {
		speedIs(360);
		suspend();
		Delay.msDelay(stopTime);
		go();
		Delay.msDelay(upTime);
		goLeft();
		Delay.msDelay(turnTime);
		go();
	}

	public static void stopAndGo() {
		suspend();
		Delay.msDelay(stopTime);
		go();
		Delay.msDelay(1500);
	}

	//More slight of a turn than veer()
	public static void swerve(int dir) {
		if (dir == 1) {
			Motor.A.setSpeed(fullSpeed);
			Motor.C.setSpeed(lessSpeed);
		}
		else if (dir == 2) {
			Motor.A.setSpeed(lessSpeed);
			Motor.C.setSpeed(fullSpeed);
		}
		Motor.A.forward();
		Motor.C.forward();
	}

	//More sharp of a turn than swerve.
	public static void veer(int dir) {
		//speedIs(deflectSpeed);
		if (dir == 2) {
			Motor.A.backward();
			Motor.C.forward();
		}
		else if (dir == 1) {
			Motor.A.forward();
			Motor.C.backward();
		}
		//speedIs(fullSpeed);
	}

	//Cool beeping sound when the robot backs out of a space.
	public static void reverseBeeps() {
		for(int i=1;i<=3;i++) {
			Sound.beep();
			Delay.msDelay(parkTime/3);
		}
	}

	public static void main (String[] args) {
		line driver = new line();
		while (robotOn == 1) {
			if (Button.ESCAPE.isDown()) {
				robotOn = 0;
			}
		}
	}
}
