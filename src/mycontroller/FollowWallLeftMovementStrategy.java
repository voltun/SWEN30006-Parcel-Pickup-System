package mycontroller;

import controller.CarController;
import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.RelativeDirection;

public class FollowWallLeftMovementStrategy implements IMovementStrategy {
	
	@Override
	public RelativeDirection getDirection(MyAutoController controller) {
		if (controller.getMovementHandler().isFollowingWall()) {
			// If wall no longer on left, turn left
			if(!controller.getMovementHandler().checkFollowingWall(controller.getOrientation(), controller.getSensorHandler().getCurrentView())) {
				return WorldSpatial.RelativeDirection.LEFT;
			} else {
				// If wall on left and wall straight ahead, turn right
				if(controller.getMovementHandler().checkWallAhead(controller.getOrientation(), controller.getSensorHandler().getCurrentView())) {
					//turnRight();
					return WorldSpatial.RelativeDirection.RIGHT;
				}
			}
		} else {
			// Start wall-following (with wall on left) as soon as we see a wall straight ahead
			if(controller.getMovementHandler().checkWallAhead(controller.getOrientation(), controller.getSensorHandler().getCurrentView())) {
				//turnRight();
				controller.getMovementHandler().setFollowingWall(true);
				return WorldSpatial.RelativeDirection.RIGHT;
			}
		}
		return null; // this was added just to get compilation happening.
	}
	
	
}


