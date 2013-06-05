import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class outside extends control {
	public static void main (String[] args) {
		for (int i = 1;i <= 4;i++) {
			control.turnLeft();
		}
	}
}
