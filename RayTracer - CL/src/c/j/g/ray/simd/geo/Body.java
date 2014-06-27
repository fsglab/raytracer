package c.j.g.ray.simd.geo;

import c.j.g.ray.simd.geo.math.Matrix4f;
import c.j.g.ray.simd.geo.math.Quaternion;
import c.j.g.ray.simd.geo.math.Vector3f;
import c.j.g.ray.simd.geo.tree.BoundingSphere;

/**
 * The basic body class. Known sub classes are: {@link Sphere}.
 * 
 * @author CJG
 *
 */
public abstract class Body {

	/**
	 * The body bounding sphere.
	 */
	private BoundingSphere bounds;
	/**
	 * The body transformation.
	 */
	private final Matrix4f translation;

	/**
	 * This will create a new body.
	 * 
	 * @param origin
	 *            the body origin in world space.
	 * @param quat
	 *            the body rotation in world space.
	 * @param bounds
	 *            the bounding sphere of the body in world space.
	 */
	public Body(Vector3f origin, Quaternion quat, BoundingSphere bounds) {
		translation = new Matrix4f(origin, quat);
		this.bounds = bounds;
	}

	/**
	 * This will return the bounding sphere in world space.
	 * 
	 * @return the bounding sphere.
	 */
	public BoundingSphere getBounds() {
		return bounds;
	}

	/**
	 * This will return the matrix to convert object space to world space.
	 * 
	 * @return the object-world-matrix.
	 */
	public Matrix4f getTransformation() {
		return translation;
	}

}
