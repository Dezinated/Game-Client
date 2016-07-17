package map;

import game.GameHandler;
import graphics.Shader;
import graphics.Texture;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;

public class EngineObject extends Object {
	
	public EngineObject(String name, float x, float y, float width, float height, boolean visible){
		super(name,x,y,width,height,visible);
	}
	
	public void loadObject(){
		shader = new Shader("shaders/engineObj.vert","shaders/engineObj.frag");
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
	
	@Override
	public void render(){
		if(!isVisible())
			return;
		Matrix4f pr_matrix = Matrix4f.orthographic(-200, 200, 200, -200, -1.0f, 1.0f);

		shader.enable();
		shader.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(-GameHandler.me.getLocation().x+location.x,-GameHandler.me.getLocation().y+location.y,0)));
		shader.setUniformMat4f("pr_matrix", pr_matrix);
		background.render();

		GameMap.shader.disable();
	}
	
	public void collide(){
		properties.forEach((k,v) -> {
			switch(k){
				case "room":
					System.out.println("Teleport to" + v);
					break;
			}
		});
	}
}
