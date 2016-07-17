package graphics;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;
import game.GameHandler;

import java.util.HashMap;
import java.util.Map;

import math.Matrix4f;
import math.Vector3f;
import utils.ShaderUtils;

public class Shader {

	public static final int VERTEX_ATTRIB = 0;
	public static final int TEXTURECOORD_ATTRIB = 1;
	
	
	
	private final int ID;
	private Map<String, Integer> locationCache = new HashMap<>();
	
	public Shader(String vert, String frag){
		ID = ShaderUtils.load(vert, frag);
	}
	
	
	public int getUniform(String name){
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		int result = glGetUniformLocation(ID,name);
		if(result == -1){
			System.err.println("Could not find uniform variable [" + name + "]");
		}else{
			locationCache.put(name, result);
		}
		return result;
	}
	
	public void setUniform1i(String name, int value){
		glUniform1i(getUniform(name), value);
	}
	
	public void setUniform1f(String name, float value){
		glUniform1f(getUniform(name), value);
	}
	
	public void setUniform2f(String name, float x, float y){
		glUniform2f(getUniform(name), x, y);
	}
	
	public void setUniform3f(String name, Vector3f v){
		glUniform3f(getUniform(name), v.x, v.y, v.z);
	}
	
	public void setUniform3f(String name, float x, float y, float z){
		glUniform3f(getUniform(name), x, y, z);
	}
	
	public void setUniformMat4f(String name, Matrix4f m){
		glUniformMatrix4fv(getUniform(name), false, m.toFloatBuffer());
	}
	
	public void enable(){
		glUseProgram(ID);
	}
	

	public void disable(){
		glUseProgram(0);
	}
	
}
