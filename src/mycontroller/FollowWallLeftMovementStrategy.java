package mycontroller;

import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.RelativeDirection;

public class FollowWallLeftMovementStrategy implements IObjectRetrievalStrategy {
	
	// How many minimum units the wall is away from the player.
	private int wallSensitivity = 1;
	private Coordinate currentPosition;
	
	private boolean isFollowingWall = false; // This is set to true when the car starts sticking to a wall.
		
	@Override
	public RelativeDirection getNextDirection(MyAutoController controller) {
		SensorHandler sh = controller.getSensorHandler();
		this.currentPosition = new Coordinate(controller.getPosition());
		
		if (this.isFollowingWall) {
			// If wall no longer on left, turn left
			if(!checkFollowingWall(controller.getOrientation(), sh.getCurrentView())) {
				return WorldSpatial.RelativeDirection.LEFT;
			} else {
				// If wall on left and wall straight ahead, turn right
				if(checkWallAhead(controller.getOrientation(), sh.getCurrentView())) {
					//turnRight();
					return WorldSpatial.RelativeDirection.RIGHT;
				}
			}
		} else {
			// Start wall-following (with wall on left) as soon as we see a wall straight ahead
			if(checkWallAhead(controller.getOrientation(), sh.getCurrentView())) {
				//turnRight();
				this.isFollowingWall = true;
				return WorldSpatial.RelativeDirection.RIGHT;
			}
		}
		return null; // this was added just to get compilation happening.
	}
	
	/**
	 * Method below just iterates through the list and check in the correct coordinates.
	 * i.e. Given your current position is 10,10
	 * checkEast will check up to wallSensitivity amount of tiles to the right.
	 * checkWest will check up to wallSensitivity amount of tiles to the left.
	 * checkNorth will check up to wallSensitivity amount of tiles to the top.
	 * checkSouth will check up to wallSensitivity amount of tiles below.
	 */
	public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Check if you have a wall in front of you!
	 * @param orientation the orientation we are in based on WorldSpatial
	 * @param currentView what the car can currently see
	 * @return
	 */
	public boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEast(currentView);
		case NORTH:
			return checkNorth(currentView);
		case SOUTH:
			return checkSouth(currentView);
		case WEST:
			return checkWest(currentView);
		default:
			return false;
		}
	}
	
	/**
	 * Check if the wall is on your left hand side given your orientation
	 * @param orientation
	 * @param currentView
	 * @return
	 */
	public boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
		switch(orientation){
		case EAST:
			return checkNorth(currentView);
		case NORTH:
			return checkWest(currentView);
		case SOUTH:
			return checkEast(currentView);
		case WEST:
			return checkSouth(currentView);
		default:
			return false;
		}	
	}

}


