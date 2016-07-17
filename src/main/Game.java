package main;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.system.MemoryUtil.NULL;
import game.GameHandler;
import networking.Init;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
 
public class Game implements Runnable {
 
	/*
	 * 
	 * 	Creating and updating the display
	 *  Probably needs optimization
	 *  For sure needs more comments
	 *  
	 *  
	 *  GAME CLOCK
	 *  CALLS GAMEHANDLER UPDATE
	 *  
	 *  OPENGL INIT
	 * 
	 */
	
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    
 
    // The window handle
    private static long window;
    public static boolean running = true;
    public static int WIDTH = 1024;
    public static int HEIGHT = 576;
    private String username = "";
    
    public Game(String username){
    	this.username = username;
    }
    
    
    public void run() {
    	
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        //Set our username and start the networking thread
        GameHandler.me.setUsername(username);
        if(!Main.dev)
        	new Thread(new Init()).start();
        
        try {
        	
        	long lastTime = System.nanoTime();
    		double delta = 0.0;
    		double ns = 1000000000.0 / 60.0;
    		long timer = System.currentTimeMillis();
    		int updates = 0;
    		int frames = 0;
    		while (running) {
    			long now = System.nanoTime();
    			delta += (now - lastTime) / ns;
    			lastTime = now;
    			if (delta >= 1.0) {
    				update();
    				updates++;
    				delta--;
    			}
    			render();
    			frames++;
    			if (System.currentTimeMillis() - timer > 1000) {
    				timer += 1000;
    				//System.out.println(updates + " ups, " + frames + " fps");
    				updates = 0;
    				frames = 0;
    			}
    			if (glfwWindowShouldClose(window) == GL_TRUE)
    				running = false;
    		}
        	
           
            // Release window and window callbacks
            glfwDestroyWindow(window);
        } finally {
            // Terminate GLFW and release the GLFWErrorCallback
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
 
        
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World! "+username, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new Input());
        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (vidmode.width() - WIDTH) / 2,
            (vidmode.height() - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        //glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();
        
        glEnable(GL_DEPTH_TEST);
        
        glActiveTexture(GL_TEXTURE1);
        
        
    }
 
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        
 
        // Set the clear color
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
 
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
        	update();
        	render();
        }
    }
 
    public static void update(){
    	glfwPollEvents();
    	Input.handleInput();
    }
    
    public static void render(){
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
    	GameHandler.update();
        glfwSwapBuffers(window); // swap the color buffers
    }
    
    public static void main(String[] args) {
        new Game("").run();
    }
 
}