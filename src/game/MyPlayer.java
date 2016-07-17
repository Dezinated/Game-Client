package game;

import io.netty.buffer.Unpooled;
import math.Vector3f;

public class MyPlayer extends Player {

	
	public void update(){
		if(GameHandler.connection != null) {
			//System.out.println("PLAYER "+username+" @ X: "+location.x + " Y: " + location.y);
			GameHandler.connection.write(Unpooled.copyShort(1));
			GameHandler.connection.write(Unpooled.copyFloat(getLocation().x));
			GameHandler.connection.write(Unpooled.copyFloat(getLocation().y));
			GameHandler.connection.write(Unpooled.copyShort(getDirection()));
			GameHandler.connection.flush();
		}
		super.update();
	}
	
	public void move(boolean W, boolean A, boolean S, boolean D){
		
		
		if(W){
			location.y-=getSpeed();
			if(Collision.doesCollide(hitbox, location))
				location.y+=getSpeed();
			direction = 'W';
		}

		if(S){
			location.y+=getSpeed();
			if(Collision.doesCollide(hitbox, location))
				location.y-=getSpeed();
			direction = 'S';
		}

		if(D){
			location.x+=getSpeed();
			if(Collision.doesCollide(hitbox, location)){
				location.x-=getSpeed();
				if(!Collision.doesCollide(hitbox, new Vector3f(location.x,location.y+getSpeed(),location.z))){
					location.y+=getSpeed();
					location.x+=getSpeed();
				}else if (!Collision.doesCollide(hitbox, new Vector3f(location.x,location.y-getSpeed(),location.z))){
					location.y-=getSpeed();
					location.x+=getSpeed();
				}
			}
			direction = 'D';
		}

		if(A){
			location.x-=getSpeed();
			if(Collision.doesCollide(hitbox, location))
				location.x+=getSpeed();
			direction = 'A';
		}
		

		
	}
}
