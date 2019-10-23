/**
 * 
 */
package mycontroller;

import world.WorldSpatial.RelativeDirection;

/**
 * @author simon
 *
 */
public interface IObjectRetrievalStrategy {
	
	/**
	 * 
	 * @param controller
	 * @return the direction the car should take in the next update
	 */
	public RelativeDirection getNextDirection(MyAutoController controller);
	
}
