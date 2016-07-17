package game;

import io.netty.channel.Channel;

import java.util.HashMap;

import main.Scene;
import math.Vector3f;


public class GameHandler {
	
	/*
	 * 
	 * 	Manages the whole game basically
	 * 
	 * CONTAINS: ALL PLAYERS, MY CONNECTION, SCENE
	 * 
	 */
	
	public static MyPlayer me = new MyPlayer();
	public static Channel connection = null;
	public static Scene scene = new Scene();
	public static HashMap<String,Player> players = new HashMap<>(); //All other players
	
	public static synchronized Player getPlayer(String name){
		//System.out.println("Get player "+name);
		if(players.get(name) == null){
			System.out.println(name+" is a null");
			Player newPlayer = new Player();
			players.put(name, newPlayer);
			newPlayer.setUsername(name);
		}
		return players.get(name);
	}
	
	public static Channel getConnection(){
		if(connection == null){
			try {
				Thread.sleep(85);
				return getConnection();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
	

	
	public static void update(){ //Gets called every render frame
		
		//Update player animations maybe?
		players.forEach((k,v) -> v.update());
		
		me.update();
		
		
		//Finally render the scene
		scene.render();
	}
	
}
