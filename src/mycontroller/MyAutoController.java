package mycontroller;

import controller.CarController;
import world.Car;
import java.util.HashMap;
import java.util.Random;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{		
		// How many minimum units the wall is away from the player.
		private int wallSensitivity = 1;
		
		private boolean isFollowingWall = false; // This is set to true when the car starts sticking to a wall.
		
		// Car Speed to move at
		private final int CAR_MAX_SPEED = 1;
		
		PathTracker pathTracker;
		
		public MyAutoController(Car car) {
			super(car);
			pathTracker = new PathTracker();
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = getView();
			if(getSpeed() < CAR_MAX_SPEED) {
				applyForwardAcceleration();
			}
			
			DirectionChooser directionChooser = new DirectionChooser(new Coordinate(getPosition()), currentView, getOrientation(), pathTracker);
			if(directionChooser.getFinalDirection(getOrientation()) == WorldSpatial.RelativeDirection.LEFT)
				turnLeft();
			else if(directionChooser.getFinalDirection(getOrientation()) == WorldSpatial.RelativeDirection.RIGHT)
				turnRight();
			
			pathTracker.update(new Coordinate(getPosition()), -10);
		}
		
	}
