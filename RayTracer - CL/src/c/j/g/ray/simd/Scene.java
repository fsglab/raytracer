package c.j.g.ray.simd;

import c.j.g.ray.simd.geo.Body;
import c.j.g.ray.simd.geo.tree.BoundingSphereNode;

public class Scene {

	private BoundingSphereNode objects;

	public void addBody(Body body) {
		objects = objects == null ? new BoundingSphereNode(body) : objects
				.inserte(body);
	}

}
