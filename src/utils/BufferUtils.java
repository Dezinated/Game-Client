package utils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {

	public static ByteBuffer createByteBuffer(byte[] b){
		ByteBuffer result = ByteBuffer.allocateDirect(b.length).order(ByteOrder.nativeOrder());
		result.put(b).flip();
		return result;
	}
	
	public static FloatBuffer createFloatBuffer(float[] b){
		FloatBuffer result = ByteBuffer.allocateDirect(b.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(b).flip();
		return result;
	}
	
	public static IntBuffer createIntBuffer(int[] b){
		IntBuffer result = ByteBuffer.allocateDirect(b.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(b).flip();
		return result;
	}
	
}
