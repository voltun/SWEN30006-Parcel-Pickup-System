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
public abstract class CompositeObjectRetrievalStrategy implements IObjectRetrievalStrategy {
	protected ArrayList<IObjectRetrievalStrategy> 
	objectRetrievalStrategies = new ArrayList<IObjectRetrievalStrategy>();
	
	public CompositeObjectRetrievalStrategy() {}
	
	/**
	 * Adds a strategy to the Composite object
	 * @param strategy
	 */
	public void add(IObjectRetrievalStrategy strategy) {
		objectRetrievalStrategies.add(strategy);
	}
	
	public abstract ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler);
	
}
