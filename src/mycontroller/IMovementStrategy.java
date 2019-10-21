/**
 * 
 */
package mycontroller;

import controller.CarController;
import world.WorldSpatial.Direction;
import world.WorldSpatial.RelativeDirection;

/**
 * @author simon
 *
 */
public interface IMovementStrategy {
	
	/**
	 * 
	 * @param controller
	 * @return the direction the car should take in the next update
	 */
	public RelativeDirection getDirection(MyAutoController controller);

}
