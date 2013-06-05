import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import lejos.robotics.Color;
import lejos.util.Delay;
import java.util.*;

public class mother extends stop {

	public static void main (String[] args) {
		mother driver = new mother();
		driver.warmUp();
		robotOn = menu.robotOn;
		while (control.robotOn == 1) {
			//How long it's been since we started.
			driver.timer();
			//Show me what to expect parking wise on screen.
			driver.updateParkingDisplay();
			//Where the robot thinks we're parking.
			driver.expectedLotDisplay();
			//Where we think we're parking.
			driver.displayOurNextToLot();
			//What space are we going to?
			driver.updateSpaceDisplay();
			//What our current speed is.
			driver.updateSpeedDisplay();
			//This is where we'll park for certain.
			driver.nextLot(onOurWayTo);
			//Watch out for white lines.
			driver.steer(control.rightEye.getColorID(),control.leftEye.getColorID());
			//Check speed on yellow lines.
			//driver.checkSpeed(control.rightEye.getColorID(),control.leftEye.getColorID());
			//Hug the left side of the road at node 42.
			driver.handleNode42(control.leftEye.getColorID());
			//Where are we at.
			currentNode = driver.getCurrentNode();
			//If we're about to hit a lot, and it's our lot, do this.
			if (driver.inParkingMode() == true && driver.isOurLot() == true) {
				//Have we hit the lot yet?
				driver.checkSpace(control.rightEye.getColorID(), control.leftEye.getColorID(), currentNode);
				//What side is the lot on?
				//int lotSide = driver.checkLotSide(currentNode);
				//Once we finish parking, turn parking mode off.
				if (control.finishedParking == 1) {
					stop.inParkingZone = 0;
				}
				//Veer to the side that the lot is on so that we hit the lot for sure.
				else if (control.finishedParking == 0) {
					if (control.rightEye.getColorID() == control.red || control.rightEye.getColorID() == control.yellow) {
						control.veer(control.left);
					}
					else if (control.leftEye.getColorID() == control.red || control.leftEye.getColorID() == control.yellow) {
						control.veer(control.right);
					}
					driver.parkingModeSteer(control.rightEye.getColorID(),control.leftEye.getColorID(),driver.checkLotSide(control.desiredLots[control.toLotAndSpace]));//lotSide);
					//stop.inParkingZone = park.isParking;
				}
				LCD.drawInt(control.finishedParking,5,5);
			}
			//Or if it's not our lot, treat it like a white line and avoid it.
			/*else if (control.rightEye.getColorID() == Color.BLUE || control.rightEye.getColorID() == Color.RED || control.rightEye.getColorID() == control.yellow) {
				driver.veer(control.left);
			}
			else if (control.leftEye.getColorID() == Color.BLUE || control.leftEye.getColorID() == Color.RED || control.leftEye.getColorID() == control.yellow) {
				driver.veer(control.right);
			}*/
			//If the robot reads green, lets hope turning slightly will save it.

			/*if (control.rightEye.getColorID() == Color.GREEN) {
				control.swerve(control.left);
			}
			else if (control.leftEye.getColorID() == Color.GREEN) {
				control.swerve(control.right);
			}*/
			//Otherwise, just go right along.
			else {
				go();
			}
			//Watch out for white lines.
			driver.steer(control.rightEye.getColorID(),control.leftEye.getColorID());
			//We are we heading to now? penis
			driver.updateParkingDisplay();

			//What to do at this exact stop node.
			if (control.centerEye.getColorID() == Color.RED) {
				driver.actOnRed();
			}
			//We're done
			if (control.currentNode == 54 && control.leftEye.getColorID() == control.black) {
				swerve(control.left);
			}
			if (control.leftEye.getColorID() == Color.RED && control.onOurWayTo == 55 && control.currentNode == 54) {
				control.turnLeft();
				Delay.msDelay(1000);
				System.exit(0);
			}
			if (Button.ESCAPE.isDown()) {
				control.robotOn = 0;
			}
		}
	}
}
