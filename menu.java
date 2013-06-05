import lejos.util.TextMenu;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import java.util.*;

public class menu {
	//List of lots to choose from.
	public static String[] parkingList = {
		"1","2","3","4","5",
		"6","7","8","9","10"
	};
	//List of spaces to choose from.
	public static String[] fiveSpaceList = {
		"1","2","3","4","5"
	};
	public static String[] fourSpaceList = {
		"1","2","3","4"
	};
	public static String[] threeSpaceList = {
		"1","2","3"
	};
	public static String chosenLot;
	public static String chosenSpace;
	public static ArrayList<Integer> chosenLots = new ArrayList<Integer>();
	public static ArrayList<Integer> chosenSpaces = new ArrayList<Integer>();
	public static int robotOn = 1;
	public static int stillConfirming = 1;
	public static Integer[] selectedLotList;
	public static Integer[] selectedSpaceList;
	public static Integer[][] parkingData;
	public static Integer[] five = {2,3};
	public static Integer[] four = {1,5};
	public static String[] confirmOptions = {"okay","quit"};
	public static int selectedOption;
	//Shortcut for creating menus
	public static void newMenu(TextMenu menu,ArrayList<Integer> chosen) {
		chosen.add(menu.select()+1);
		LCD.clear();
	}
	//confirms the selection.
	public static void confirm(Integer[] lots,Integer[] spaces) {
		TextMenu confirmMenu = new TextMenu(confirmOptions,1,"Correct Lots?");
		for (int i=0;i<lots.length;i++) {
			LCD.drawString("lot: "+lots[i]+" space: "+spaces[i],0,3+i);
		}
		selectedOption = confirmMenu.select() + 1;
		if (selectedOption == 1) {
			LCD.clear();
		}
		else if (selectedOption == 2) {
			robotOn = 0;
		}
	}

	//Convert ArrayList<Integer> to Integer[]
	public static Integer[][] getLotInput() {
		TextMenu lotMenu = new TextMenu(parkingList,1,"Choose Lot");
		TextMenu fiveSpaceMenu = new TextMenu(fiveSpaceList,1,"Choose Space");
		TextMenu fourSpaceMenu = new TextMenu(fourSpaceList,1,"Choose Space");
		TextMenu threeSpaceMenu = new TextMenu(threeSpaceList,1,"Choose Space");
		for(int i=0;i<=5;i++) {
			newMenu(lotMenu,chosenLots);
			if (Arrays.binarySearch(five,chosenLots.get(i)) >= 0) {
				newMenu(fiveSpaceMenu,chosenSpaces);
			}
			else if (Arrays.binarySearch(four,chosenLots.get(i)) >= 0) {
				newMenu(fourSpaceMenu,chosenSpaces);
			}
			else {
				newMenu(threeSpaceMenu,chosenSpaces);
			}
			if (i == 5) {
				chosenLots.add(55);
				chosenSpaces.add(1);
			}
		}
		selectedLotList = (Integer[]) chosenLots.toArray(new Integer[chosenLots.size()]);
		selectedSpaceList = (Integer[]) chosenSpaces.toArray(new Integer[chosenLots.size()]);
		confirm(selectedLotList,selectedSpaceList);
		parkingData = new Integer[][] {selectedLotList,selectedSpaceList};
		return parkingData;
	}
	public static void main(String[] args) {
		menu driver = new menu();
		while(robotOn==1) {
			if (Button.ESCAPE.isDown()) {
				robotOn = 0;
			}
		}
	}
}
