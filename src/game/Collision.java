package game;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import map.CollidableObject;
import math.Vector3f;

public class Collision {

	private static ArrayList<CollidableObject> objects = new ArrayList<CollidableObject>();

	private Collision(){
		
	}
	
	public static boolean doesCollide(float x, float y){
		for(int i=0;i<objects.size();i++){
			if(objects.get(i).getShape().contains(x, y))
				return true;
		}
		return false;
	}
	
	public static boolean doesCollide(Rectangle r){
		for(int i=0;i<objects.size();i++){
			if(objects.get(i).getShape().intersects(r))
				return true;
			if(objects.get(i).getShape().getClass() != Polygon.class){
				if(r.intersects((Rectangle)objects.get(i).getShape()))
					return true;
			}
		}
		return false;
	}
	
	public static boolean doesCollide(Rectangle r, Vector3f v){
		r.setLocation((int)v.x, (int)v.y-r.height);
		for(int i=0;i<objects.size();i++){
			if(objects.get(i).getShape().intersects(r))
				return true;
			if(objects.get(i).getShape().getClass() != Polygon.class){
				if(r.intersects((Rectangle)objects.get(i).getShape()))
					return true;
			}
		}
		return false;
	}
	
	public static void addCollidable(Object o){
		objects.add((CollidableObject)o);
	}
	
	public static void render(){
		objects.forEach(o -> o.render());
	}
	
}
