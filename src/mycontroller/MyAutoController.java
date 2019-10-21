package mycontroller;

import controller.CarController;
import world.Car;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{		
		
		
		private MovementHandler movementHandler;
		
		public MyAutoController(Car car) {
			super(car);
			movementHandler = MovementHandler.getMovementHandler();
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = getView();
			
			// checkStateChange(); //Maybe this is where we should be checking for 1) Exploring 2) Moving toward parcel 3) Moving toward exit
			
			// this is where a movement strategy should be placed.
			
			
			if(getSpeed() < MovementHandler.CAR_MAX_SPEED){       // Need speed to turn and progress toward the exit
				applyForwardAcceleration();   // Tough luck if there's a wall in the way
			}
			
		}
	
		
	}
