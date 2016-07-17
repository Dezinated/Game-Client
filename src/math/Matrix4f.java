package math;
import java.nio.FloatBuffer;

import utils.BufferUtils;


public class Matrix4f {

	public static final int SIZE = 4 * 4;
	public float[] elements = new float[SIZE];
	
	public Matrix4f(){
		
	}
	
	public static Matrix4f identity(){
		Matrix4f result = new Matrix4f();
		result.elements[0 + 0 * 4] = 1.0f;
		result.elements[1 + 1 * 4] = 1.0f;
		result.elements[2 + 2 * 4] = 1.0f;
		result.elements[3 + 3 * 4] = 1.0f;
		return result;
	}
	
	public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far){
		Matrix4f result = identity();
		
		result.elements[0 + 0 * 4] = 2.0f / (right - left);
		result.elements[1 + 1 * 4] = 2.0f / (top - bottom);
		result.elements[2 + 2 * 4] = 2.0f / (near - far);
		
		result.elements[0 + 3 * 4] = (left + right) / (left - right);
		result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
		result.elements[2 + 3 * 4] = (far + near) / (far - near);
		
		return result;
	}
	
	public static Matrix4f translate(Vector3f v){
		Matrix4f result = identity();
		
		result.elements[0 + 3 * 4] = v.x;
		result.elements[1 + 3 * 4] = v.y;
		result.elements[2 + 3 * 4] = v.z;
		
		return result;
	}
	
	public static Matrix4f rotate(float angle){
		Matrix4f result = identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		
		result.elements[0 + 0 * 4] = cos;
		result.elements[1 + 0 * 4] = sin;
		
		result.elements[0 + 1 * 4] = -sin;
		result.elements[1 + 1 * 4] = cos;
		
		return result;
	}
	
	public Matrix4f multiply(Matrix4f m){
		Matrix4f result = new Matrix4f();
		
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				float sum = 0.0f;
				for (int e = 0; e < 4; e++) {
					sum += this.elements[j + e * 4] * m.elements[e + i * 4]; 
				}			
				result.elements[j + i * 4] = sum;
			}
		}
		
		return result;
	}
	
	public FloatBuffer toFloatBuffer() {
		return BufferUtils.createFloatBuffer(elements);
	}
}
