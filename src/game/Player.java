package game;

import java.awt.Rectangle;

import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import main.Scene;
import map.GameMap;
import math.Matrix4f;
import math.Vector3f;

public class Player {

	/*
	 * 
	 * 	Player class
	 * 
	 */

	protected char direction = 'S';
	
	protected Vector3f location;
	public Vector3f newLocation;
	public boolean running = false;

	public Shader shader;
	private VertexArray image;
	private Texture imageTextureUp, imageTextureDown, imageTextureLeft, imageTextureRight;

	private int width = 25;
	private int height = 50;

	protected Rectangle hitbox = new Rectangle(width, height/3);
	
	private String username = "bob";
	
	private boolean readyToRender = false;
	
	

	public Player(){

		


		location = new Vector3f();



	}
	
	public void loadRendering(){
		float[] vertices = new float[] {
				0, 0, 0.0f, //bottom left
				0, height, 0.0f, //bottom right
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
		image = new VertexArray(vertices, indices, tcs);

		imageTextureUp = new Texture("res/player/up.png");
		imageTextureDown = new Texture("res/player/down.png");
		imageTextureLeft = new Texture("res/player/left.png");
		imageTextureRight = new Texture("res/player/right.png");
		
		this.getShader().enable();
		this.getShader().setUniformMat4f("vw_matrix", Matrix4f.identity());
		this.shader.disable();
		
		readyToRender = true;
	}
	
	public Shader getShader(){
		if(shader == null){
			shader = new Shader("shaders/bg.vert","shaders/bg.frag");
		}
		return shader;
	}

	public Player(float x, float y, float z){
		location = new Vector3f(x,y,z);
	}


	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return username;
	}

	public synchronized Vector3f getLocation(){
		return location;
	}

	public void setDirection(char d){
		direction = d;
	}
	
	public char getDirection(){
		return direction;
	}
	
	public float getSpeed(){
		if (running)
			return 2;

		return 1;
	}

	public void update(){
		hitbox.setLocation((int)location.x, (int) (location.y-hitbox.getHeight()));
	}

	public void setVW(){
		this.getShader().enable();
		this.getShader().setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(-GameHandler.me.getLocation().x+location.x, GameHandler.me.getLocation().y-location.y,0)));
		this.shader.disable();
	}
	
	public void render(){
		
		if(!readyToRender)
			loadRendering();
		
		if(direction == 'W')
			imageTextureUp.bind();
		if(direction == 'A')
			imageTextureLeft.bind();
		if(direction == 'S')
			imageTextureDown.bind();
		if(direction == 'D')
			imageTextureRight.bind();

		Matrix4f pr_matrix = Matrix4f.orthographic(-200, 200, -200, 200, -1.0f, 1.0f);
		
		this.getShader().enable();
		this.getShader().setUniformMat4f("pr_matrix", pr_matrix);
		this.getShader().setUniform1i("tex", 1);
		image.render();

		this.shader.disable();
		image.unbind();
	}

	public void setPosition(float x, float y){
		location = new Vector3f(x,y,0);
	}

	

}
