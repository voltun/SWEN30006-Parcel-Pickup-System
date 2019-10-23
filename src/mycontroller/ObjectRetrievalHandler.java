package mycontroller;

import controller.CarController;

/**
 * @author Nicholas Wong
 *
 */
public class ObjectRetrievalHandler {
	public static final String DEFAULT_OR_STRATEGY = "DjikstraObjectRetrievalStrategy";
	
	private CarController controller;
	private IObjectRetrievalStrategy retrievalStrat = null;
	
	public ObjectRetrievalHandler(CarController controller) {
		this.controller = controller;
	}
	
	/**
	 * TODO the update method should move the car in a path by following the direction popped 
	 * from a pre-computed list.
	 * @param graph 
	 */
	public void update(Graph graph) {
		if(retrievalStrat == null) {
			retrievalStrat = StrategyFactory.getInstance().getObjectRetrievalStrategy(DEFAULT_OR_STRATEGY, graph);
		}
	}

}
