package mycontroller;

import java.util.HashMap;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;
import world.WorldSpatial.RelativeDirection;

/**
 * @author Nicholas Wong
 *
 */
public class ExplorationHandler {
	public static final String DEFAULT_EXPLORATION_STRATEGY = "IntelligentExplorationStrategy";
	
	private CarController controller;
	private StrategyFactory strategyFactory;
	private IExplorationStrategy exploreStrat;
	private PathTracker pathTracker;
	
	private float health1UpdateBefore;
	private boolean backingUp = false;
	
	public ExplorationHandler(CarController controller) {
		this.controller = controller;
		this.strategyFactory = StrategyFactory.getInstance();
		this.pathTracker = new PathTracker();
		
		health1UpdateBefore = controller.getHealth();
	}
	
	public void update() {
		//Stores variables to reduce overhead cost
		Coordinate currentPos = new Coordinate(controller.getPosition());
		HashMap<Coordinate, MapTile> view = controller.getView();
		
		//Gets the strategy from the StrategyFactory and the next direction to go
		this.exploreStrat = strategyFactory.getExplorationStrategy(DEFAULT_EXPLORATION_STRATEGY, 
				currentPos, view, controller.getOrientation(), pathTracker);
		RelativeDirection dir = this.exploreStrat.getNextDirection();
		
		
		if(controller.getHealth() < health1UpdateBefore) {
			health1UpdateBefore = controller.getHealth();
			backingUp = true;
		}

		if(backingUp) {
			//Back up until there is a wall behind you
			if(checkBehind())
				backingUp = false;
			else
				controller.applyReverseAcceleration();
		}
		else if(controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
			controller.applyForwardAcceleration();
		}
		else if(dir != null) {
			if(dir == RelativeDirection.LEFT) {
				controller.turnLeft();
			}
			else {
				controller.turnRight();
			}
		}
		
		//Updates the pathTracker to correspond with post-movement
		pathTracker.update(new Coordinate(controller.getPosition()), PathTracker.VISITED_TILE_SCORE);
		
	}
		
	private boolean checkBehind() {
		Coordinate carCoord = new Coordinate(controller.getPosition());
		
		if(controller.getOrientation() == WorldSpatial.Direction.NORTH) {
			if(controller.getMap().get(new Coordinate(carCoord.x, carCoord.y-1)).getType().toString() == "WALL")
				return true;
		} else if(controller.getOrientation() == WorldSpatial.Direction.EAST) {
			if(controller.getMap().get(new Coordinate(carCoord.x-1, carCoord.y)).getType().toString() == "WALL")
				return true;
		} else if(controller.getOrientation() == WorldSpatial.Direction.SOUTH) {
			if(controller.getMap().get(new Coordinate(carCoord.x, carCoord.y+1)).getType().toString() == "WALL")
				return true;
		} else if(controller.getOrientation() == WorldSpatial.Direction.WEST) {
			if(controller.getMap().get(new Coordinate(carCoord.x+1, carCoord.y)).getType().toString() == "WALL")
				return true;
		}
		return false;
	}
	
	
	
	
	
	
}
