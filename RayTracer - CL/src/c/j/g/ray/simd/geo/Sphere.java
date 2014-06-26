package c.j.g.ray.simd.geo;

import c.j.g.ray.simd.geo.math.Quaternion;
import c.j.g.ray.simd.geo.math.Vector3f;
import c.j.g.ray.simd.geo.tree.BoundingSphere;

/**
 * A simple sphere body.
 * @author CJG
 *
 */
public class Sphere extends Body {

	/**
	 * The sphere radius.
	 */
	private final float radius;

	/**
	 * This will create a new sphere with the given property.
	 * @param position the sphere world position.
	 * @param radius the sphere radius.
	 * @param rotation the sphere rotation.
	 */
	public Sphere(Vector3f position, float radius, Quaternion rotation) {
		super(position, rotation, new BoundingSphere(position, radius));
		this.radius = radius;
	}
	
	/**
	 * This will create a new sphere with the given property.
	 * @param position the sphere world position.
	 * @param radius the sphere radius.
	 */
	public Sphere(Vector3f position, float radius) {
		this(position, radius, new Quaternion(0,0,0,1));
	}

	/**
	 * This will return the spheres radius.
	 * @return the radius.ik
	 */
	public float getRadius() {
		return radius;
	}

}
