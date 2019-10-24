/**
 * 
 */
package mycontroller;

import java.util.ArrayList;

import utilities.Coordinate;

/**
 * @author Nicholas Wong
 *
 */
public class CompositeMinimiseDamageObjectRetrievalStrategy extends CompositeObjectRetrievalStrategy
		implements IObjectRetrievalStrategy {
	public static final float MIN_HEALTH_THRESHOLD = 100;
	
	public CompositeMinimiseDamageObjectRetrievalStrategy() {}

	@Override
	public ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler) {
		// TODO Auto-generated method stub
		return null;
	}

}
