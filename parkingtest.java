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

public class parkingtest {
	public static int robotOn = 1;
	public static void main(String[] args) {
		Motor.A.setSpeed(300);
		Motor.C.setSpeed(300);
		Motor.A.forward();
		Motor.C.forward();
		while(robotOn == 1) {
			if (Button.ESCAPE.isDown()) {
				robotOn = 0;
			}
		}
	}
}
