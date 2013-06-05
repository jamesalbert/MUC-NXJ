import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.ColorSensor;
import lejos.robotics.mapping.LineMap;
import lejos.robotics.localization.*;
import lejos.robotics.Color;
import lejos.robotics.pathfinding.NodePathFinder;
import lejos.robotics.pathfinding.ShortestPathFinder;
import lejos.robotics.pathfinding.NavigationMesh;
import lejos.robotics.pathfinding.FourWayGridMesh;
import lejos.robotics.pathfinding.AstarSearchAlgorithm;
import lejos.robotics.pathfinding.DijkstraPathFinder;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.MoveController;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.pathfinding.Node;
import lejos.geom.Line;
import lejos.geom.Rectangle;
import lejos.util.Delay;
import java.util.*;

public class navi {
	public static int robot_on = 1;
	public static Line[] lines = {
		//frame
		new Line(-40,-42,-40,102),
		new Line(-42,100,22,100),
		new Line(20,102,20,78),
		new Line(18,80,82,80),			//the barriers that the robot will avoid
		new Line(80,78,80,122),
		new Line(78,120,122,120),
		new Line(120,122,120,78),
		new Line(118,80,162,80),
		new Line(160,82,160,-42),
		new Line(162,-40,-42,-40),
		//first box
		new Line(20,20,20,60),
		new Line(20,60,40,60),
		new Line(40,60,40,20),
		new Line(40,20,20,20),
		//second box
		new Line(60,20,60,60),
		new Line(60,60,80,60),
		new Line(80,60,80,20),
		new Line(80,20,60,20),
		//third box
		new Line(100,20,100,60),
		new Line(100,60,120,60),
		new Line(120,60,120,20),
		new Line(120,20,100,20),
	};

	public static Pose start_node = new Pose(0,0,180);					//start position
	public static Pose final_node = new Pose(100,100,90);					//end position
	public static Waypoint end_node_waypoint = new Waypoint(final_node);	//create waypoint from end position

	public static Rectangle frame = new Rectangle(150,150,250,250);			//surrounding frame of map
	public static LineMap psuedo_map = new LineMap(lines,frame);			//create line map from array lines and frame

	//public static FourWayGridMesh nodes = new FourWayGridMesh(psuedo_map,20,0);		//create grid
	//public static AstarSearchAlgorithm astar = new AstarSearchAlgorithm();			//instantiate astar class
	public static DijkstraPathFinder dijkstra = new DijkstraPathFinder(psuedo_map);	//instantiate dijkstra class
	public static Path path;			//variable to store path
	public static String pathString;	//variable to store path string

	public static DifferentialPilot captain = new DifferentialPilot(4.37,20,Motor.C,Motor.A);		//class that drives the robot

	public static void suspend_gps(Navigator gps) {
		gps.stop();
	}

	public static void continue_gps(Navigator gps) {
		gps.followPath();
	}

	publ
	ic static void main(String[] args) {
		navi pathfinder = new navi();
		captain.setTravelSpeed(20);
		captain.setRotateSpeed(20);
		captain.setMinRadius(0);
		try {
			//psuedo_map.createSVGFile("map");							//create reusable map data
			path = dijkstra.findRoute(start_node,end_node_waypoint);	//calculate route
			//pathString = path.toString();								//convert path to string
			//PoseProvider position = new OdometryPoseProvider(captain);	//initiate current position
			//System.out.println(pathString);								//print path
			//Navigator gps = new Navigator(captain, position);			//initiate navigator
			//gps.followPath(path);										//drive robot to destination
		}
		catch (Exception e) {
			System.out.println(e);
		};
		PoseProvider position = new OdometryPoseProvider(captain);	//initiate current position
		position.setPose(start_node);
		Navigator gps = new Navigator(captain, position);			//initiate navigator
		gps.followPath(path);										//drive robot to destination
		while(robot_on==1) {
			if (Button.ESCAPE.isDown()) {
				robot_on = 0;
			}
		}
	}
}

//commented out stuff
//public static ShortestPathFinder map = new ShortestPathFinder(psuedo_map);		ignore
//public static NodePathFinder map = new NodePathFinder(astar,nodes);				ignore
