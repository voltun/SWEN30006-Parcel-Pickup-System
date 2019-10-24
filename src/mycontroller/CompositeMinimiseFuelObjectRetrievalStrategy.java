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
public class CompositeMinimiseFuelObjectRetrievalStrategy extends CompositeObjectRetrievalStrategy {
	public static final int MIN_FUEL_THRESHOLD = 300;
	
	private ArrayList<Coordinate> path = null;
	
	public CompositeMinimiseFuelObjectRetrievalStrategy() {}

	@Override
	public ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler) {
		path = new ArrayList<Coordinate>();
		ArrayList<Coordinate> tempPath = new ArrayList<Coordinate>();
		
		for(IObjectRetrievalStrategy strat : objectRetrievalStrategies) {
			tempPath = strat.getPath(orHandler);
			
			if(tempPath == null || path.size() < tempPath.size()) {
				path = tempPath;
			}
		}
		return path;
	}

}
