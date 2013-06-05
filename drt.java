import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

import lejos.robotics.Color;

import lejos.util.Delay;
import java.util.*;

public class drt {
	public static int robot_on = 1;

	public static void main (String[] args) {
		Motor.A.setSpeed(360);
		Motor.C.setSpeed(360);
		Motor.A.forward();
		Motor.C.forward();
		Delay.msDelay(1000);
	}
}
