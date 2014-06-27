package c.j.g.ray.simd.geo.tree;

import c.j.g.ray.simd.geo.Body;

public class BoundingSphereNode {

	private BoundingSphereNode parent;
	public BoundingSphere bounds;
	public Body body;
	public BoundingSphereNode child1, child2;

	public BoundingSphereNode(Body b) {
		this.body = b;
		this.bounds = b.getBounds();
	}

	private BoundingSphereNode(Body b, BoundingSphereNode parent) {
		this.body = b;
		this.bounds = b.getBounds();
		this.parent = parent;
	}

	public boolean isLeaf() {
		return body != null;
	}

	public void inserte(Body nbody) {
		if (isLeaf()) {
			child1 = new BoundingSphereNode(body, this);
			child2 = new BoundingSphereNode(nbody, this);
			body = null;
			recalcSize();
		} else {
			BoundingSphere bs = nbody.getBounds();
			BoundingSphereNode node = child1.getGrowth(bs) < child2
					.getGrowth(bs) ? child1 : child2;
			node.inserte(nbody);
		}
	}

	private double getGrowth(BoundingSphere bs) {
		BoundingSphere bigger = new BoundingSphere(this.bounds, bs);
		return bigger.getRadius() * bigger.getRadius() - this.bounds.getRadius() * this.bounds.getRadius();
	}

	private void recalcSize() {
		if (isLeaf())
			return;
		bounds = new BoundingSphere(child1.bounds, child2.bounds);
		if (parent != null)
			parent.recalcSize();
	}

}