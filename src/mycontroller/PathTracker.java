package mycontroller;

import java.util.HashMap;

import utilities.Coordinate;

public class PathTracker {
	
	private HashMap<Coordinate, Float> whereTheCarHasBeen;
	
	PathTracker(){
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
