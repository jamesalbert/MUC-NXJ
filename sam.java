import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class sam {

	public static int blue = 2;
	public static int fullSpeed = 100;
	public static int fiveSeconds = 5000;
	public static int x = 0;
	public static int y = 0;

	public static ColorSensor newSensor = new ColorSensor(SensorPort.S1);

	public static void handleColorInput(int color) {
		if (color == blue) {
			LCD.drawString("blue",x,y);
		}
		else {
			LCD.drawString("not blue",x,y);
		}
	}

	public static void speedIs(int aSpeed, int cSpeed) {
		Motor.A.setSpeed(aSpeed);
		Motor.C.setSpeed(cSpeed);
	}

	public static void turnMotorsOn() {
		Motor.A.forward();
		Motor.C.forward();
	}

	public static void main(String[] args) {
		sam driver = new sam();
		sam.speedIs(fullSpeed,fullSpeed);
		sam.turnMotorsOn();
		sam.handleColorInput(newSensor.getColorID());
		Delay.msDelay(fiveSeconds);
	}
}
