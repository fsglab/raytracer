package c.j.g.ray.simd.util;

import java.nio.FloatBuffer;

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
	public static FloatBuffer toFloatBuffer(float... floats) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(floats.length);
		buf.put(floats).rewind();
		return buf;
	}

}
