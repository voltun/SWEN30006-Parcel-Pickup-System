package mycontroller;

import java.util.HashMap;
import java.util.Random;

import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;

public class DirectionChooser {
	
	float northScore;
	float southScore;
	float westScore;
	float eastScore;
	TileScores tileScores;
	
	Coordinate carCoordinate;
	private int wallSensitivity = 1;
	HashMap<Coordinate, MapTile> currentView;
	
	DirectionChooser(Coordinate carCoord, HashMap<Coordinate, MapTile> map, WorldSpatial.Direction orientation, PathTracker pathTracker){
		
		carCoordinate = carCoord;
		currentView = map;
		
		tileScores = new TileScores();
		for(int i=1; i<=4; i++) {//From the position of the car to the top
			
			for(int j=-4; j<=4; j++) {//Loop for the westScore
				if(orientation != WorldSpatial.Direction.WEST || j==0 || j==1 || j==-1) {
				Coordinate currentCoord = new Coordinate(carCoord.x-i, carCoord.y+j);
				float tileScore = getTileScore(map.get(currentCoord)); //get the Score of the current tile
				tileScore += pathTracker.getCoordinateValue(currentCoord);
				float finalScore = tileScore/(j*j*Math.abs(j)*5+Math.abs(i)); //Add that score to the total core of the left according to this function
				westScore += finalScore;
				}
			}
			for(int j=-4; j<=4; j++) {//Same thing for the eastScore
				if(orientation != WorldSpatial.Direction.EAST || j==0 || j==1 || j==-1) {
				Coordinate currentCoord = new Coordinate(carCoord.x+i, carCoord.y+j);
				float tileScore = getTileScore(map.get(currentCoord)); //get the Score of the current tile
				tileScore += pathTracker.getCoordinateValue(currentCoord);
				float finalScore = tileScore/(j*j*Math.abs(j)*5+Math.abs(i)); //Add that score to the total core of the left according to this function
				eastScore += finalScore;
				}
			}
			for(int j=-4; j<=4; j++) {//Similar for the northScore
				if(orientation != WorldSpatial.Direction.NORTH || j==0 || j==1 || j==-1) {
				Coordinate currentCoord = new Coordinate(carCoord.x+j, carCoord.y+i);
				float tileScore = getTileScore(map.get(currentCoord)); //get the Score of the current tile
				tileScore += pathTracker.getCoordinateValue(currentCoord);
				float finalScore = tileScore/(j*j*Math.abs(j)*5+Math.abs(i)); //Add that score to the total core of the left according to this function
				northScore += finalScore;
				}
			}
			for(int j=-4; j<=4; j++) {//Same as the northScore
				if(orientation != WorldSpatial.Direction.SOUTH || j==0 || j==1 || j==-1) {
				Coordinate currentCoord = new Coordinate(carCoord.x+j, carCoord.y-i);
				float tileScore = getTileScore(map.get(currentCoord)); //get the Score of the current tile
				tileScore += pathTracker.getCoordinateValue(currentCoord);
				float finalScore = tileScore/(j*j*Math.abs(j)*5+Math.abs(i)); //Add that score to the total core of the left according to this function
				southScore += finalScore;
				}
			}
		}
	}
	
	float getTileScore(MapTile tile) {
		if(tile.isType(MapTile.Type.TRAP)) {
			TrapTile temp = (TrapTile) tile;
			return tileScores.getTileScores().get(temp.getTrap());
		} else {
			return tileScores.getTileScores().get(tile.getType().toString());
		}
		
	}

	public WorldSpatial.RelativeDirection getFinalDirection(WorldSpatial.Direction orientation){
		WorldSpatial.RelativeDirection bestDirection = getBestDirection(orientation);
		if(bestDirection == WorldSpatial.RelativeDirection.LEFT && !checkLeft(orientation, currentView))
			return WorldSpatial.RelativeDirection.LEFT;
		else if(bestDirection == WorldSpatial.RelativeDirection.RIGHT && !checkRight(orientation, currentView))
			return WorldSpatial.RelativeDirection.RIGHT;
		//To prevent the car from headbutting constantly into a wall
		else if(checkWallAhead(orientation, currentView)) {
			Random rand = new Random();
			if(rand.nextBoolean() && !checkLeft(orientation, currentView))
				return WorldSpatial.RelativeDirection.LEFT;
			else if(!checkRight(orientation, currentView))
				return WorldSpatial.RelativeDirection.RIGHT;
			else {}
				//The car has a wall left, right and in front of it so it has to go back
				//I will deal with this later
		}
		return null;
	}
	
	//For simplicity the current version of the algorithm only goes forward. 
	//But this can easily be changed by changing this method if we want to increase its efficiency in the future
	public WorldSpatial.RelativeDirection getBestDirection(WorldSpatial.Direction orientation){
		
		Random rand = new Random();
		if(orientation == WorldSpatial.Direction.NORTH) {
			float maxScore = Math.max(northScore*1.4f, Math.max(eastScore, westScore));
			if(maxScore == northScore*1.4f)
				return null;
			
			else if(eastScore==westScore) {
				Random ran = new Random();
				if(ran.nextBoolean())
					return WorldSpatial.RelativeDirection.RIGHT;
				else
					return WorldSpatial.RelativeDirection.LEFT;
			}
			
			else if(maxScore == eastScore)
				return WorldSpatial.RelativeDirection.RIGHT;
			else
				return WorldSpatial.RelativeDirection.LEFT;
		}
		if(orientation == WorldSpatial.Direction.EAST) {
			float maxScore = Math.max(eastScore*1.4f, Math.max(southScore, northScore));
			if(maxScore == eastScore*1.4f)
				return null;
			
			else if(southScore==northScore) {
				Random ran = new Random();
				if(ran.nextBoolean())
					return WorldSpatial.RelativeDirection.RIGHT;
				else
					return WorldSpatial.RelativeDirection.LEFT;
			}
			
			else if(maxScore == southScore)
				return WorldSpatial.RelativeDirection.RIGHT;
			else
				return WorldSpatial.RelativeDirection.LEFT;
		}
		if(orientation == WorldSpatial.Direction.SOUTH) {
			float maxScore = Math.max(southScore*1.4f, Math.max(westScore, eastScore));
			if(maxScore == southScore*1.4f)
				return null;
			
			else if(eastScore==westScore) {
				Random ran = new Random();
				if(ran.nextBoolean())
					return WorldSpatial.RelativeDirection.RIGHT;
				else
					return WorldSpatial.RelativeDirection.LEFT;
			}
			
			else if(maxScore == westScore)
				return WorldSpatial.RelativeDirection.RIGHT;
			else
				return WorldSpatial.RelativeDirection.LEFT;
		}
		else {
			float maxScore = Math.max(westScore*1.4f, Math.max(northScore, southScore));
			if(maxScore == westScore*1.4f)
				return null;
			
			else if(southScore==northScore) {
				Random ran = new Random();
				if(ran.nextBoolean())
					return WorldSpatial.RelativeDirection.RIGHT;
				else
					return WorldSpatial.RelativeDirection.LEFT;
			}
			
			else if(maxScore == northScore)
				return WorldSpatial.RelativeDirection.RIGHT;
			else
				return WorldSpatial.RelativeDirection.LEFT;
		}
	}
	
	
	/**
	 * Check if you have a wall in front of you!
	 * @param orientation the orientation we are in based on WorldSpatial
	 * @param currentView what the car can currently see
	 * @return
	 */
	private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEast(currentView);
		case NORTH:
			return checkNorth(currentView);
		case SOUTH:
			return checkSouth(currentView);
		case WEST:
			return checkWest(currentView);
		default:
			return false;
		}
	}
	
	/**
	 * Check if the wall is on your left hand side given your orientation
	 * @param orientation
	 * @param currentView
	 * @return
	 */
	private boolean checkLeft(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
		switch(orientation){
		case EAST:
			return checkNorth(currentView);
		case NORTH:
			return checkWest(currentView);
		case SOUTH:
			return checkEast(currentView);
		case WEST:
			return checkSouth(currentView);
		default:
			return false;
		}	
	}
	
	private boolean checkRight(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		
		switch(orientation){
		case EAST:
			return checkSouth(currentView);
		case NORTH:
			return checkEast(currentView);
		case SOUTH:
			return checkWest(currentView);
		case WEST:
			return checkNorth(currentView);
		default:
			return false;
		}	
	}
	
	/**
	 * Method below just iterates through the list and check in the correct coordinates.
	 * i.e. Given your current position is 10,10
	 * checkEast will check up to wallSensitivity amount of tiles to the right.
	 * checkWest will check up to wallSensitivity amount of tiles to the left.
	 * checkNorth will check up to wallSensitivity amount of tiles to the top.
	 * checkSouth will check up to wallSensitivity amount of tiles below.
	 */
	public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(carCoordinate.x+i, carCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(carCoordinate.x-i, carCoordinate.y));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(carCoordinate.x, carCoordinate.y+i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(carCoordinate.x, carCoordinate.y-i));
			if(tile.isType(MapTile.Type.WALL)){
				return true;
			}
		}
		return false;
	}
}







