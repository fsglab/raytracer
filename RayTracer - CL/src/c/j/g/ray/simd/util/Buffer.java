package c.j.g.ray.simd.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

public class Buffer {

	private Buffer() {
	}

	/**
	 * Utility method to convert float array to float buffer
	 * 
	 * @param floats
	 *            - the float array to convert
	 * @return a float buffer containing the input float array
	 */
	public static FloatBuffer toBuffer(float... floats) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(floats.length);
		buf.put(floats).rewind();
		return buf;
	}

	/**
	 * Utility method to convert int array to int buffer
	 * 
	 * @param ints
	 *            - the int array to convert
	 * @return an int buffer containing the input int array
	 */
	public static IntBuffer toBuffer(int... ints) {
		IntBuffer buf = BufferUtils.createIntBuffer(ints.length);
		buf.put(ints).rewind();
		return buf;
	}

}
