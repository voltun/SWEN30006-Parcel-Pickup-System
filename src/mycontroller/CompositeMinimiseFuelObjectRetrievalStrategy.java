/**
 * 
 */
package mycontroller;

import java.util.ArrayList;

import utilities.Coordinate;

/**
 * Can be extended by adding more pathfinding algortihms using graphs
 * Finds the shortest path to take (therefore less fuel usage) 
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
