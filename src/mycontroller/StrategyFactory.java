package mycontroller;

import java.util.HashMap;

import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial.Direction;

/**
 * @author Nicholas Wong
 *
 */
public class StrategyFactory {
	private static StrategyFactory instance = null;
	private IExplorationStrategy exploreStrat = null;
	private IObjectRetrievalStrategy retrievalStrat = null;
	
	private StrategyFactory() {

	}
	
	/**
	 * Factory access method
	 * @return the factory instance
	 */
	public static StrategyFactory getInstance() {
		if (instance == null) {
			instance = new StrategyFactory();
		}
		return instance;
	}
	
	/**
	 * @param strategy - the string of the strategy to get
	 * @return
	 */
	public IExplorationStrategy getExplorationStrategy(String strategy, Coordinate carCoord, 
			HashMap<Coordinate, MapTile> map, Direction orientation, PathTracker pathTracker) {
		if(strategy.equals("IntelligentExplorationStrategy")) {
			exploreStrat = (IExplorationStrategy) new IntelligentExplorationStrategy(carCoord, map, orientation, pathTracker);
		}
		return exploreStrat;
	}
	
	/**
	 * @param strategy - string of strategy name
	 * @param graph - a Graph class object with information of the map
	 * @return
	 */
	public IObjectRetrievalStrategy getObjectRetrievalStrategy(float health, Graph graph) {
		if(health < CompositeMinimiseDamageObjectRetrievalStrategy.MIN_HEALTH_THRESHOLD) {
			CompositeObjectRetrievalStrategy compo = new CompositeMinimiseDamageObjectRetrievalStrategy();
			compo.add(new ShortestPathObjectRetrievalStrategy(graph));
			retrievalStrat = compo;
		}
		else {
			CompositeObjectRetrievalStrategy compo = new CompositeMinimiseFuelObjectRetrievalStrategy();
			compo.add(new ShortestPathObjectRetrievalStrategy(graph));
			retrievalStrat = compo;
		}
		
		return retrievalStrat;
	}
}
