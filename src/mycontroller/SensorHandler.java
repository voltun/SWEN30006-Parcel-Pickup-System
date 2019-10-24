package mycontroller;

import controller.CarController;
import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;

/**
 * @author Simon Cozens
 *
 */
public class SensorHandler {
	
	private Coordinate lastKnownPosition;
	private Graph graph;
	private CarController controller;

	public SensorHandler(CarController controller) {
		this.controller = controller;
		this.lastKnownPosition = new Coordinate(controller.getPosition());
		initGraph();
	}
	
	public void update() {
		updateGraph();
		this.lastKnownPosition = new Coordinate(controller.getPosition());
	}
	
	/**
	 * Gets the current view square of the car defined by view distance
	 * @return
	 */
	public HashMap<Coordinate, MapTile> getCurrentView() {
		return controller.getView();
	}
	
	/**
	 * Gets the graph stored by the sensorHandler
	 * @return
	 */
	public Graph getGraph() {
		return this.graph;
	}
	
	/**
	 * Scans for parcel in the viewsquare
	 * @return Coordinate of the parcel if found, null otherwise
	 */
	protected Coordinate scanForParcels() {
		HashMap<Coordinate, MapTile> view = getCurrentView();
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		
		for(int i = currentPosition.x - controller.getViewSquare(); 
				i <= currentPosition.x + controller.getViewSquare(); i++) {
			for(int j = currentPosition.y - controller.getViewSquare(); 
					j <= currentPosition.y + controller.getViewSquare(); j++) {
				MapTile tile = view.get(new Coordinate(i, j));			
				//found a parcel tile
				if(tile.isType(MapTile.Type.TRAP) 
						&& ((tiles.ParcelTrap) tile).getTrap().equals("parcel")) {
					return new Coordinate(i, j);
				}
			}
		}
		//returns null if doesn't find a parcel in the viewsquare
		return null;
	}
	
	/**
	 * Scans for an exit in the viewsquare
	 * @return
	 */
	protected Coordinate scanForExit() {
		HashMap<Coordinate, MapTile> view = getCurrentView();
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		
		for(int i = currentPosition.x - controller.getViewSquare(); 
				i <= currentPosition.x + controller.getViewSquare(); i++) {
			for(int j = currentPosition.y - controller.getViewSquare(); 
					j <= currentPosition.y + controller.getViewSquare(); j++) {
				MapTile tile = view.get(new Coordinate(i, j));			
				//found a exit tile
				if(tile.isType(MapTile.Type.FINISH)) {
					return new Coordinate(i, j);
				}
			}
		}
		//returns null if doesn't find an exit in the viewsquare
		return null;
	}
	
	//Initialise graph with the starting point viewsquare
	private void initGraph() {
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		graph = new Graph(new Vertex(currentPosition));
		
		for(int i = currentPosition.x - controller.getViewSquare(); 
				i <= currentPosition.x + controller.getViewSquare(); i++) {
			for(int j = currentPosition.y - controller.getViewSquare(); 
					j <= currentPosition.y + controller.getViewSquare(); j++) {				
				addNode(i, j);
			}
		}
	}
	
	//Adds a node to graph based on certain conditions
	private void addNode(int x, int y) {
		MapTile tile = getCurrentView().get(new Coordinate(x, y));
		Vertex vertex = new Vertex(new Coordinate(x, y));
		
	    //does not add wall or empty tiles into the graph
		if(tile.isType(MapTile.Type.EMPTY) || tile.isType(MapTile.Type.WALL)  
				|| graph.hasVertex(vertex)) {
			return;
		}
		
		graph.addNode(vertex);
	}
	
	//Scans newly explored area corresponding to recent movement 
	//and updates the graph
	private void updateGraph() {
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		int viewSquare = controller.getViewSquare();
		
		if(currentPosition.equals(lastKnownPosition)) {
			return;
		}
		
		//Checks if the newly updated position is on the y-axis
		if(currentPosition.x == lastKnownPosition.x) {
			int yDiff = currentPosition.y - lastKnownPosition.y;
			
			//when the newly updated position is north  
			if(yDiff >= 0) {
				int lastViewDist = lastKnownPosition.y + controller.getViewSquare();
				
				for(int i = lastViewDist + 1; i <= lastViewDist + yDiff; i++) {
					for(int j = lastKnownPosition.x - viewSquare; j <= lastKnownPosition.x + viewSquare; j++) {
						this.addNode(j, i);
					}
				}
			} else {
				//when the newly update position is south
				int lastViewDist = lastKnownPosition.y - controller.getViewSquare();
				
				for(int i = lastViewDist - 1; i >= lastViewDist + yDiff; i--) {
					for(int j = lastKnownPosition.x - viewSquare; j <= lastKnownPosition.x + viewSquare; j++) {
						this.addNode(j, i);
					}
				}
			}
		} else {
			int xDiff = currentPosition.x - lastKnownPosition.x;
			
			//when the newly updated position is east  
			if(xDiff >= 0) {
				int lastViewDist = lastKnownPosition.x + controller.getViewSquare();
				
				for(int i = lastViewDist + 1; i <= lastViewDist + xDiff; i++) {
					for(int j = lastKnownPosition.y - viewSquare; j <= lastKnownPosition.y + viewSquare; j++) {
						this.addNode(i, j);
					}
				}
			} else {
				//when the newly update position is west
				int lastViewDist = lastKnownPosition.x - controller.getViewSquare();
				
				for(int i = lastViewDist - 1; i >= lastViewDist + xDiff; i--) {
					for(int j = lastKnownPosition.y - viewSquare; j <= lastKnownPosition.y + viewSquare; j++) {
						this.addNode(i, j);
					}
				}
			}
		}
	}
}
