package map;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import game.GameHandler;
import graphics.Shader;
import graphics.VertexArray;
import math.Matrix4f;
import math.Vector3f;

public class CollidableObject extends Object {

	private Shape shape;
	
	public CollidableObject(String name, long x, long y, long width, long height, boolean visible){
		super(name,x,y,width,height,visible);
		shape = new Rectangle(Math.toIntExact(x), Math.toIntExact(y), Math.toIntExact(width), Math.toIntExact(height));
	}
	
	public CollidableObject(String name, long x, long y, int[] xPoints, int[] yPoints, boolean visible) {
		super(name);
		shape = new Polygon(xPoints, yPoints, xPoints.length);
		((Polygon) shape).translate(Math.toIntExact(x), Math.toIntExact(y));
	}

	public Shape getShape(){
		return shape;
	}
	
	@Override
	public void render() {
		if(!isVisible())
			return;
		Matrix4f pr_matrix = Matrix4f.orthographic(-200, 200, 200, -200, -1.0f, 1.0f);

		shader.enable();
		shader.setUniformMat4f("vw_matrix", Matrix4f.translate(new Vector3f(-GameHandler.me.getLocation().x+location.x,-GameHandler.me.getLocation().y+location.y,0)));
		shader.setUniformMat4f("pr_matrix", pr_matrix);
		background.render();

		GameMap.shader.disable();
	}

	@Override
	public void loadObject() {
		shader = new Shader("shaders/engineObj.vert","shaders/engineObj.frag");
		float[] vertices = null;
		
		if(shape.getClass() == Polygon.class){
			Polygon p = (Polygon)shape;
			vertices = new float[p.npoints*3];
			for(int i=0;i<p.npoints;i++){
				vertices[i*3] = p.xpoints[i];
				vertices[i*3+1] = p.ypoints[i];

			}
			
		}else{
			 vertices = new float[]{
					0, 0, 0.0f, //bottom left
					0,  height, 0.0f, //bottom right
					width,  height, 0.0f, //top right
					width, 0, 0.0f //top left
			};
		}
		
		
		
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

}
