/**
 * 
 */
package mycontroller;

import java.util.ArrayList;
import utilities.Coordinate;

/**
 * @author simon
 *
 */
public interface IObjectRetrievalStrategy {
	
	/**
	 * @param orHandler - the ObjectRetrievalHandler in charge of the strat
	 * @return a path in an arraylist of coordinates to go to
	 */
	public ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler);
	
}
