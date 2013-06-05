import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class forloop {
	public static int roboton = 1;
	public static void main(String[] args) {
		for(int i=1;i<10;i++) {
			LCD.drawString("line"+i,i,0);
		}
		while (roboton == 1) {
			if (Button.ESCAPE.isDown()) {
				roboton = 0;
			}
		}
	}
}
