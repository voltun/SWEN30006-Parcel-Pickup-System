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
	
	//TODO implement Graph here
	private Graph revealedGraph;
	private CarController controller;

	public SensorHandler(CarController controller) {
		this.controller = controller;
	}
	
	public void update() {
		//TODO update method for sensor
	}
	
	public HashMap<Coordinate, MapTile> getCurrentView() {
		return controller.getView();
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
	
}
