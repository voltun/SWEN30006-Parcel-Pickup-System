package mycontroller;

import java.util.HashMap;

/**
 * @author Meric Ungor
 *
 */
public class TileScores {
	
	HashMap<String, Float> tileScores;
	
	TileScores(){
		tileScores = new HashMap<String, Float>();
		tileScores.put("WALL", -30f);
		tileScores.put("UTILITY", 10f);
		tileScores.put("EMPTY", 0f);
		tileScores.put("ROAD", 10f);
		tileScores.put("START", -10f);
		tileScores.put("FINISH", 50f);
		tileScores.put("parcel", 100f);
		tileScores.put("health", 20f);
		tileScores.put("water", 10f);
		tileScores.put("mud", -10f);
		tileScores.put("grass", 0f);
		tileScores.put("lava", -60f);


		//For now all of the other traps are treated equally
		//but we can detail this in the future easily
	}
	
	HashMap<String, Float> getTileScores(){
		return tileScores;
	}
}

