package map;

import java.util.HashMap;
import java.util.Map;

import game.GameHandler;
import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;

public abstract class Object {

	protected String name;
	protected Vector3f location = new Vector3f(0,0,0);
	protected float width;
	protected float height;
	private boolean isVisible;
	protected HashMap<String,String> properties = new HashMap();

	public static Shader shader;

	protected VertexArray background;
	protected Texture bgTexture;

	public Object(){
		
	}

	public Object(String name){
		this.name = name;
	}

	public Object(String name, float x, float y, float width, float height, boolean visible){
		this.name = name;
		this.location = new Vector3f(x,y,0);
		this.width = width;
		this.height = height;
		this.isVisible = visible;
	}
	
	public void setPositon(float x, float y){
		location.x = x;
		location.y = y;
	}

	public boolean isVisible(){
		return isVisible;
	}

	public void setProperty(Map map){
		properties = (HashMap<String, String>) map;
	}

	public void setProperty(String key, String value){
		properties.put(key, value);
	}

	public abstract void render();
	
	public abstract void loadObject();
	
	public String toString(){
		StringBuilder value = new StringBuilder();
		value.append(name+" @ ("+location.x+", "+location.y+") w:"+width+" h:"+height+" Visible:"+isVisible
				+"\nProperties:\n");
		properties.forEach((k,v)->{
			value.append(k+": "+v+"\n");
		});

		return value.toString();
	}

	

}
