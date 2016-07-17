package main;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import io.netty.buffer.Unpooled;
import map.GameMap;
import game.GameHandler;

import org.lwjgl.glfw.GLFWKeyCallback;


public class Input extends GLFWKeyCallback{

	/*
	 * 
	 * 	Handle all input to the game in this class
	 * 	TODO:
	 *  1) Add mouse input handler
	 * 
	 */
	
	public static boolean keys[] = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if(key == -1)
			return;
		keys[key] = action != GLFW_RELEASE;

		
		if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
            glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this in our rendering loop
	} 
	
	public static void handleInput(){
		
		if(Input.keys[GLFW_KEY_LEFT_SHIFT] && !GameHandler.me.running){ //Press left shift
			GameHandler.me.running = true;
		}
		if(!Input.keys[GLFW_KEY_LEFT_SHIFT] && GameHandler.me.running){ //Release left shift
			GameHandler.me.running = false;
		}
		
		GameHandler.me.move(Input.keys[GLFW_KEY_W], Input.keys[GLFW_KEY_A], Input.keys[GLFW_KEY_S], Input.keys[GLFW_KEY_D]);
	}

}
