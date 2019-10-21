package mycontroller;

import controller.CarController;
import java.util.HashMap;
import tiles.MapTile;
import utilities.Coordinate;

public class SensorHandler {
	
	private CarController controller;

	public SensorHandler(CarController controller) {
		this.controller = controller;
	}
	
	public HashMap<Coordinate, MapTile> getCurrentView() {
		return controller.getView();
	}

}
