package map;

import java.awt.Shape;
import java.util.ArrayList;

import game.Collision;
import game.GameHandler;
import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;

public class GameMap {
	
	public static Shader shader;
	
	private VertexArray background;
	private Texture bgTexture;
	
	public static Collision collision;
	
	private ArrayList<Object> objects = new ArrayList<Object>();
	
	public GameMap(){

	}
	//bgTexture = new Texture(levelName);
	//GameHandler.players.clear();
	
	public void loadMap(String name){
		bgTexture = new Texture("res/Maps/"+name+"/background.png");
		TiledParser.loadObjects("res/Maps/"+name+"/info.json");
		setObjects(TiledParser.getObjects());
		setCollision(TiledParser.getCollidable());
		
		int height = bgTexture.getHeight();
		int width = bgTexture.getWidth();

		shader = new Shader("shaders/bg.vert","shaders/bg.frag");
		float[] vertices = new float[] {
				0, 0, 0.0f, //bottom left
				0,  height, 0.0f, //bottom right
				width,  height, 0.0f, //top right
				width, 0, 0.0f //top left
		};
		byte[] indices = new byte[] {
				0, 1, 2,
				2, 3, 0
		};

		float[] tcs = new float[] {
				0, 1,
				0, 0,
				1, 0,
				1, 1
		};
		background = new VertexArray(vertices, indices, tcs);
		
		
	}
	

	public void setCollision(ArrayList<Object> o){
		o.forEach(obj -> Collision.addCollidable(obj));
	}
	
	public void setObjects(ArrayList<Object> o){
		objects = o;
	}
	

	
	public float getMapWidth(){
		return bgTexture.getWidth();
	}
	
	public float getMapHeight(){
		return bgTexture.getHeight();
	}
	
	
	public void render(){
		bgTexture.bind();
		//Matrix4f pr_matrix = Matrix4f.orthographic(-100, 100, -100, 100, -1.0f, 1.0f);
		Matrix4f pr_matrix = Matrix4f.orthographic(-200, 200, 200, -200, -1.0f, 1.0f);
		
		objects.forEach(o -> {
			//System.out.println(o.isVisible());
			if(o.isVisible()){
				o.render();
			}
		});
		
		Collision.render();
		
		GameMap.shader.enable();
		GameMap.shader.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(-GameHandler.me.getLocation().x,-GameHandler.me.getLocation().y,0)));
		GameMap.shader.setUniformMat4f("pr_matrix", pr_matrix);
        GameMap.shader.setUniform1i("tex", 1);
        GameMap.shader.setUniform1i("flip", 1);
		background.render();
		
		GameMap.shader.disable();
		bgTexture.unbind();
	}
	
	
}
