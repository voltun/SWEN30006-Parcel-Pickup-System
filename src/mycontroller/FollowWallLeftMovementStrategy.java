package mycontroller;

import controller.CarController;
import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.Direction;
import world.WorldSpatial.RelativeDirection;

public class FollowWallLeftMovementStrategy implements IMovementStrategy {
	@Override
	public RelativeDirection getDirection(CarController controller) {
		// TODO Auto-generated method stub
		if (isFollowingWall) {
			// If wall no longer on left, turn left
			if(!checkFollowingWall(controller.getOrientation(), controller.currentView)) {
				return WorldSpatial.RelativeDirection.LEFT;
			} else {
				// If wall on left and wall straight ahead, turn right
				if(checkWallAhead(controller.getOrientation(), currentView)) {
					//turnRight();
					return WorldSpatial.RelativeDirection.RIGHT;
				}
			}
		} else {
			// Start wall-following (with wall on left) as soon as we see a wall straight ahead
			if(checkWallAhead(controller.getOrientation(), currentView)) {
				//turnRight();
				return WorldSpatial.RelativeDirection.RIGHT;
				controller.movementHandler.setFollowingWall(true);
			}
		}
	}
	
	/**
	 * Check if the wall is on your left hand side given your orientation
	 * @param orientation
	 * @param currentView
	 * @return
	 */
	private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
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


