package c.j.g.ray.simd.geo.tree;

import c.j.g.ray.simd.geo.Body;

/**
 * This is a node which can be used as root for a BVH.
 * 
 * @author CJG
 *
 */
public class BoundingSphereNode {

	/**
	 * The body this node holds. It will be null if this is not a leaf.
	 */
	private Body body;
	/**
	 * The bounds which inclose this node.
	 */
	private BoundingSphere bounds;
	/**
	 * The child of this node. They will be null if this is a leaf.
	 */
	private BoundingSphereNode child1, child2;

	/**
	 * The parent of this node. if this is the root it will be null.
	 */
	private BoundingSphereNode parent;

	/**
	 * If this is not a leaf null will be returned.
	 * 
	 * @return the body hold by this node.
	 */
	public Body getBody() {
		return body;
	}

	/**
	 * This returns the bounds of this node.
	 * 
	 * @return {@link #bounds}
	 */
	public BoundingSphere getBounds() {
		return bounds;
	}

	/**
	 * The fist child if this is not a leaf.
	 * 
	 * @return {@link #child1}
	 */
	public BoundingSphereNode getChild1() {
		return child1;
	}

	/**
	 * The second child if this is not a leaf.
	 * 
	 * @return {@link #child2}
	 */
	public BoundingSphereNode getChild2() {
		return child2;
	}

	/**
	 * This will return the parent of this node. If this is the root it will
	 * return null.
	 * 
	 * @return {@link #parent}
	 */
	public BoundingSphereNode getParent() {
		return parent;
	}

	/**
	 * This will create a new root for a BVH.
	 * 
	 * @param b
	 *            the body everything starts with.
	 */
	public BoundingSphereNode(Body b) {
		this.body = b;
		this.bounds = b.getBounds();
	}

	/**
	 * This will create a new node.
	 * 
	 * @param b
	 *            the body this node holds.
	 * @param parent
	 *            the parent of this node.
	 */
	private BoundingSphereNode(Body b, BoundingSphereNode parent) {
		this.body = b;
		this.bounds = b.getBounds();
		this.parent = parent;
	}

	/**
	 * This will return a value how much this node had to grow to contain the
	 * new BoundingSphere.
	 * 
	 * @param bs
	 *            the volume to test for.
	 * @return the grow indicator.
	 */
	private double getGrowth(BoundingSphere bs) {
		BoundingSphere bigger = new BoundingSphere(this.bounds, bs);
		return bigger.getRadius() * bigger.getRadius()
				- this.bounds.getRadius() * this.bounds.getRadius();
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

	/**
	 * This will return if this is a leaf or not.
	 * 
	 * @return if this contains a body.
	 */
	public boolean isLeaf() {
		return body != null;
	}

	/**
	 * This will recalculate the bounding volume.
	 */
	private void recalcSize() {
		if (isLeaf())
			return;
		bounds = new BoundingSphere(child1.bounds, child2.bounds);
		if (parent != null)
			parent.recalcSize();
	}

}
