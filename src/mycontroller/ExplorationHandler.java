package mycontroller;

import java.util.HashMap;

import controller.CarController;
import tiles.MapTile;
import utilities.Coordinate;
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
	
	public ExplorationHandler(CarController controller) {
		this.controller = controller;
		this.strategyFactory = StrategyFactory.getInstance();
		this.pathTracker = new PathTracker();
	}
	
	public void update() {
		//Stores variables to reduce overhead cost
		Coordinate currentPos = new Coordinate(controller.getPosition());
		HashMap<Coordinate, MapTile> view = controller.getView();
		
		//Gets the strategy from the StrategyFactory and the next direction to go
		this.exploreStrat = strategyFactory.getExplorationStrategy(DEFAULT_EXPLORATION_STRATEGY, 
				currentPos, view, controller.getOrientation(), pathTracker);
		RelativeDirection dir = this.exploreStrat.getNextDirection();
			
		if(controller.getSpeed() < MyAutoController.CAR_MAX_SPEED) {
			controller.applyForwardAcceleration();
		}
	
		if(dir != null) {
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
}
