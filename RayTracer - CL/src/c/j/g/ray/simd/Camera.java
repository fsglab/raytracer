package c.j.g.ray.simd;

import c.j.g.ray.simd.geo.math.Matrix4f;
import c.j.g.ray.simd.geo.math.Quaternion;
import c.j.g.ray.simd.geo.math.Vector3f;

public class Camera {

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
	}

	public Camera() {
		this(new Vector3f(0,0,0), new Quaternion(0,0,0,1), 90);
	}

}
