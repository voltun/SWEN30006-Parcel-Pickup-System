package mycontroller;

import controller.CarController;
import world.Car;
import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class MyAutoController extends CarController{		
		
	public static final int CAR_MAX_SPEED = 1;
	public enum States {EXPLORING, DIRECT_MOVE};
	
	private ExplorationHandler exploreHandler;
	private ObjectRetrievalHandler objectRetrievalHandler;
	private SensorHandler sensorHandler;
	private States state;
	
	public MyAutoController(Car car) {
		super(car);
		this.objectRetrievalHandler = new ObjectRetrievalHandler(this);
		this.exploreHandler = new ExplorationHandler(this);
		this.sensorHandler = new SensorHandler(this);
		this.state = States.EXPLORING;
		
	}
	
	// Coordinate initialGuess;
	// boolean notSouth = true;
	@Override
	public void update() {
		if(this.getSpeed() < CAR_MAX_SPEED) {
			this.applyForwardAcceleration();
		}
		//TODO explore -> scanparcel -> if true, go find path -> if path found, retrieve state				
		
		exploreHandler.update();
		
	}

	public SensorHandler getSensorHandler() {
		return sensorHandler;
	}
	
	/**
	 * @param other the state to change the controller into
	 */
	protected void changeState(States other) {
		this.state = other;
	}
	
	/**
	 * @return the current state the controller is in
	 */
	protected States getState() {
		return this.state;
	}
}
