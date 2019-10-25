/**
 * 
 */
package mycontroller;

import java.util.ArrayList;

import utilities.Coordinate;

/**
 * For future extension where there are traps that damage the car and decide on a 
 * best path to move the car while taking minimum possible damage
 * @author Nicholas Wong
 *
 */
public class CompositeMinimiseDamageObjectRetrievalStrategy extends CompositeObjectRetrievalStrategy
		implements IObjectRetrievalStrategy {
	public static final float MIN_HEALTH_THRESHOLD = 100;
	
	public CompositeMinimiseDamageObjectRetrievalStrategy() {}

	@Override
	public ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler) {
		// For future implementation
		return null;
	}

}
