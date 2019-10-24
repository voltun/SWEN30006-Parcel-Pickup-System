package mycontroller;

import controller.CarController;
import world.Car;
import utilities.Coordinate;

public class MyAutoController extends CarController{		
		
	public static final int CAR_MAX_SPEED = 1;
	public enum States {EXPLORING, OBJECT_RETRIEVAL};
	
	private ExplorationHandler exploreHandler;
	private ObjectRetrievalHandler objectRetrievalHandler;
	private SensorHandler sensorHandler;
	private States state;
	private Coordinate exitPos;
	
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
		sensorHandler.update();
		//Searches if there is any parcel in the viewsquare of the car
		Coordinate parcelPos = null;

		switch(state) {
			case EXPLORING:
				//Determines whether to go to exit tile or not
				if(canExit() && objectRetrievalHandler.goTo(exitPos, sensorHandler.getGraph())) {
					this.changeState(States.OBJECT_RETRIEVAL);
					break;
				}
				
				//Scans for exit coordinate if exit not known
				if(exitPos == null) {
					exitPos = sensorHandler.scanForExit();
				}
				//Scans for parcel in viewsquare of car
				parcelPos = sensorHandler.scanForParcels();
				
				//Parcel Found!
				if(parcelPos != null && 
						objectRetrievalHandler.goTo(parcelPos, sensorHandler.getGraph())) {					
					this.changeState(States.OBJECT_RETRIEVAL);
					break;
				} else {
					exploreHandler.update();
				}				
				break;
			case OBJECT_RETRIEVAL:
				objectRetrievalHandler.update();
				if(objectRetrievalHandler.hasReached()) {
					this.changeState(States.EXPLORING);
				}
				break;
			default:
				break;
		}			
		
	}
	
	//Checks if exit coordinate is known and the target number of parcels
	//has been collected
	private boolean canExit() {
		if(exitPos != null && super.numParcelsFound() >= super.numParcels()) {
			return true;
		}
		return false;
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
