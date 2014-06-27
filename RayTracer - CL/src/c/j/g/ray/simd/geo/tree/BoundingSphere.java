package c.j.g.ray.simd.geo.tree;

import c.j.g.ray.simd.geo.math.Vector3f;

/**
 * This provides the volume for a {@link BoundingSpherenode}. Also for each
 * {@link c.j.g.ray.geo.body}.
 * 
 * @author CJG
 *
 */
public class BoundingSphere {

	/**
	 * The sphere origin.
	 */
	private final Vector3f origin;

	/**
	 * The sphere radius.
	 */
	private final float radius;

	/**
	 * This creates a sphere which incloses the both given.
	 * 
	 * @param b1
	 * @param b2
	 */
	BoundingSphere(BoundingSphere b1, BoundingSphere b2) {
		Vector3f centerOff = b2.origin.sub(b1.origin);
		float dist = centerOff.squareLength();
		float radiusDiff = b2.radius - b1.radius;

		if (radiusDiff * radiusDiff >= dist) {
			BoundingSphere enclosing = b1.radius > b2.radius ? b1 : b2;
			origin = enclosing.origin;
			radius = enclosing.radius;
		} else {
			dist = (float) Math.sqrt(dist);
			radius = (dist + b1.radius + b2.radius) * .5f;
			Vector3f _origin = b1.origin;
			if (dist > 0)
				_origin = _origin.add(centerOff
						.mul((radius - b1.radius) / dist));
			origin = _origin;
		}
	}

	/**
	 * this will create a sphere with the given property. 
	 * @param origin the origin.
	 * @param radius the radius.
	 */
	public BoundingSphere(Vector3f origin, float radius) {
		this.origin = origin;
		this.radius = radius;
	}

	/**
	 * The origin.
	 * @return {@link origin}
	 */ 
	public Vector3f getOrigin() {
		return origin;
	}

	/**
	 * The radius.
	 * @return {@link radius}
	 */
	public float getRadius() {
		return radius;
	}

}
