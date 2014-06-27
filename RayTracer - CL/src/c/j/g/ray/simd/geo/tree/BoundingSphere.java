package c.j.g.ray.simd.geo.tree;

import c.j.g.ray.simd.geo.math.Vector3f;

public class BoundingSphere {

	private final Vector3f origin;

	private final float radius;

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

	public BoundingSphere(Vector3f origin, float radius) {
		this.origin = origin;
		this.radius = radius;
	}

	public Vector3f getOrigin() {
		return origin;
	}

	public float getRadius() {
		return radius;
	}

}
