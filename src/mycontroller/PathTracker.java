package mycontroller;

import java.util.HashMap;
import utilities.Coordinate;

public class PathTracker {
	public static final  int VISITED_TILE_SCORE = -10;
	
	private HashMap<Coordinate, Float> whereTheCarHasBeen;
	
	public PathTracker(){
		whereTheCarHasBeen = new HashMap<Coordinate, Float>();
	}
	
	public void update(Coordinate coordinate, float value) {
		if(whereTheCarHasBeen.containsKey(coordinate))
			whereTheCarHasBeen.replace(coordinate, whereTheCarHasBeen.get(coordinate)+value);
		else 
			whereTheCarHasBeen.put(coordinate, value);
	}
	
	public float getCoordinateValue(Coordinate coordinate) {
		if(whereTheCarHasBeen.containsKey(coordinate))
			return whereTheCarHasBeen.get(coordinate);
		else
			return 0;
	}
}

