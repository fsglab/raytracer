package c.j.g.ray.simd;

import java.nio.FloatBuffer;

import c.j.g.ray.simd.geo.math.Matrix4f;
import c.j.g.ray.simd.geo.math.Quaternion;
import c.j.g.ray.simd.geo.math.Vector3f;
import c.j.g.ray.simd.util.Buffer;

public class Camera {

	private final FloatBuffer buffer;

	private final Matrix4f translation;

	public Matrix4f getTranslation() {
		return translation;
	}

	public float getFov() {
		return fov;
	}

	private final float fov;

	public Camera(Vector3f origin, Quaternion rotation, float fov) {
		translation = new Matrix4f(origin, rotation);
		this.fov = fov;

		if ("false".equalsIgnoreCase(System.getProperty("opencl")))
			buffer = null;
		else {
			float[] buf = new float[17];
			buf[0] = fov;
			System.arraycopy(translation.getM(), 0, buf, 1, 16);
			buffer = Buffer.toFloatBuffer(buf);
		}

	}

	public FloatBuffer getBuffer() {
		return buffer;
	}

	public Camera() {
		this(new Vector3f(), new Quaternion(), 90);
	}

}
