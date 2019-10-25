package mycontroller;

import world.WorldSpatial.RelativeDirection;

/**
 * Interface for Exploration related strategies
 * @author Nicholas Wong
 *
 */
public interface IExplorationStrategy {
	
	public RelativeDirection getNextDirection();
	
}
