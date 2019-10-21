package mycontroller;

import controller.CarController;
import world.Car;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{		
		
		
		private MovementHandler movementHandler;
		private SensorHandler sensorHandler;
		
		public MyAutoController(Car car) {
			super(car);
			MovementHandler movementHandler = new MovementHandler(this);
			SensorHandler sensorHandler = new SensorHandler(this);
			
			
		}
		
		// Coordinate initialGuess;
		// boolean notSouth = true;
		@Override
		public void update() {
			// Gets what the car can see
			HashMap<Coordinate, MapTile> currentView = sensorHandler.getCurrentView();
			
			
			// checkStateChange(); //Maybe this is where we should be checking for 1) Exploring 2) Moving toward parcel 3) Moving toward exit
			
			// this is where a movement strategy should be placed.
			// create a new instance of strategy, passing in getView();
			
			movementHandler.move();
			
		}
		
		public MovementHandler getMovementHandler() {
			return movementHandler;
		}
		
		public SensorHandler getSensorHandler() {
			return sensorHandler;
		}
	
		
	}
