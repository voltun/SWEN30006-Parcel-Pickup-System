package mycontroller;

import java.util.ArrayList;
import utilities.Coordinate;

/**
 * Example of an extendible strategy that prevents health decreasing traps in a path
 * @author Nicholas Wong
 *
 */
public class MinimiseDamageObjectRetrievalStrategy implements IObjectRetrievalStrategy {

	public MinimiseDamageObjectRetrievalStrategy() {}

	@Override
	public ArrayList<Coordinate> getPath(ObjectRetrievalHandler orHandler) {
		// FOR FUTURE IMPLEMENTATION
		return null;
	}

}
