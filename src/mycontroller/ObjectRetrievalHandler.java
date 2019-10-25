package mycontroller;

import java.util.ArrayList;
import controller.CarController;
import utilities.Coordinate;

/**
 * @author Nicholas Wong
 *
 */
public class ObjectRetrievalHandler {
	private CarController controller;
	private IObjectRetrievalStrategy retrievalStrat = null;
	private ArrayList<Coordinate> pathToFollow;
	private Coordinate target;
	private Coordinate source;
	private Coordinate checkpoint = null;
	//Flag to show that the car is in currently in reverse
	private boolean isReverse = false;
	
	/**
	 * Constructor for ObjectRetrievalHandler
	 * @param controller
	 */
	public ObjectRetrievalHandler(CarController controller) {
		this.controller = controller;
	}
	
	public void update() {
		Coordinate curr = new Coordinate(controller.getPosition());
		
		//Backtracks to the starting coordinate of path
		if(pathToFollow.contains(source)) {
			if(controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
				controller.applyReverseAcceleration();
			}
			checkpoint = popPath(pathToFollow);
			isReverse = true;
			return;
		}
		
		//Checks if the car is properly directed to the checkpoint before new pop
		if(checkpoint != null
				&& !checkpoint.equals(curr)) {
			steerCarTo(checkpoint);
			return;
		}
		//pops the first coordinate to go in the path
		if(!hasReached()) {
			checkpoint = popPath(pathToFollow);
			steerCarTo(checkpoint);
		} 	
	}
	
	/**
	 * Checks if the path is empty and object is retrieved
	 * @return
	 */
	public boolean hasReached() {
		return pathToFollow.isEmpty();
	}
	
	/**
	 * Assigns the handler to go to a specified coordinate.
	 * @param end - the Coordinate of the destination
	 * @param graph - the graph of nodes
	 * @return false if the coordinate could not be reached
	 */
	public boolean goTo(Coordinate end, Graph graph) {
		this.resetPath();
		this.target = end;
		retrievalStrat = StrategyFactory.getInstance().getObjectRetrievalStrategy(controller.getHealth(), graph);
		this.pathToFollow = retrievalStrat.getPath(this);
		//There is no path
		if(pathToFollow == null) {
			return false;
		}
		controller.applyBrake();
		return true;
	}
	
	//Cleans and reset the relevant variables for a new path search
	private void resetPath() {
		this.target = null;
		this.source = new Coordinate(controller.getPosition());
		this.pathToFollow = new ArrayList<Coordinate>();
	}
	
	//Applies pop operation to path list
	private Coordinate popPath(ArrayList<Coordinate> path) {
		Coordinate elem = path.get(0);
		
		path.remove(elem);
		path.trimToSize();
		return elem;
	}
	
	//to get to a target
	private void steerCarTo(Coordinate target) {
		Coordinate currentPosition = new Coordinate(controller.getPosition());
		
		//Stops the car if it was in reverse or it reached its target
		if(isReverse || currentPosition.equals(target)) {
			controller.applyBrake();
			isReverse = false;
			return;
		}
		//Turns, reverse or forward depending on car orientation and target's coordinates
		switch(controller.getOrientation()) {
			case NORTH:
				if(currentPosition.x < target.x) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnRight();
				} 
				else if(currentPosition.x > target.x) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnLeft();
				}	
				else if(currentPosition.y > target.y) {
					controller.applyReverseAcceleration();
					isReverse = true;
				}
				else if (currentPosition.y < target.y)
				{
					controller.applyForwardAcceleration();
				}
				break;
			case SOUTH:
				if(currentPosition.x < target.x) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnLeft();
				} 
				else if(currentPosition.x > target.x) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnRight();
				}
				else if(currentPosition.y < target.y) {
					controller.applyReverseAcceleration();
					isReverse = true;
				}
				else if (currentPosition.y > target.y)
				{
					controller.applyForwardAcceleration();
				}
				break;
			case EAST:
				if(currentPosition.y < target.y) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnLeft();
				} 
				else if(currentPosition.y > target.y) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnRight();
				}
				else if(currentPosition.x > target.x) {
					controller.applyReverseAcceleration();
					isReverse = true;
				}
				else if (currentPosition.x < target.x)
				{
					controller.applyForwardAcceleration();
				}
				break;
			case WEST:
				if(currentPosition.y < target.y) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnRight();
				} 
				else if(currentPosition.y > target.y) {
					if(isReverse || controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
						controller.applyForwardAcceleration();
						isReverse = false;
					}
					controller.turnLeft();
				}
				else if(currentPosition.x < target.x) {
					controller.applyReverseAcceleration();
					isReverse = true;
				}
				else if (currentPosition.x > target.x)
				{
					controller.applyForwardAcceleration();
				}
				break;
		}
	}
	
	public Coordinate getSource() {
		return this.source;
	}
	
	public Coordinate getTarget() {
		return this.target;
	}
}
