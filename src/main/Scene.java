package main;
import game.GameHandler;
import map.GameMap;

public class Scene {
	
	/*
	 * 
	 * 	This class should be everything that is going to be rendered
	 * 	Collect background, entities, objects, etc. and draw them here
	 * 
	 * CONTAINS: MAP
	 * 
	 */
	
	public static GameMap map;
	
	public Scene(){
		
		map = new GameMap();
        map.loadMap("test1");
	}

	public void render(){
		
		//Works like a stack
		
		GameHandler.me.render();
		
		GameHandler.players.forEach((k,v) -> {
			v.setVW();
			v.render();
		});
		
		map.render();
		
	}
	
}
