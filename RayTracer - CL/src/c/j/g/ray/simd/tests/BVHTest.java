package c.j.g.ray.simd.tests;

import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_COLOR_RENDERING;
import static java.awt.RenderingHints.KEY_DITHERING;
import static java.awt.RenderingHints.KEY_FRACTIONALMETRICS;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.KEY_STROKE_CONTROL;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.KEY_TEXT_LCD_CONTRAST;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_DITHER_ENABLE;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_ON;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_STROKE_PURE;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import c.j.g.ray.simd.geo.Sphere;
import c.j.g.ray.simd.geo.math.Vector3f;
import c.j.g.ray.simd.geo.tree.BoundingSphereNode;

@SuppressWarnings("serial")
public class BVHTest extends JPanel implements MouseListener {

	public static void main(String... s) {
		JFrame j = new JFrame("BVH Test");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(600, 400);
		j.setLocationRelativeTo(null);
		j.setContentPane(new BVHTest());
		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
		j.setVisible(true);
	}

	private BVHTest() {
		this.addMouseListener(this);
	}

	private BoundingSphereNode root;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setQuality(g);
		if (root != null){
			paintNodeBounds(g, root);
			paintNodeLeaf(g, root);
		}
		drawInfo(g);
	}

	private void drawInfo(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawString(
				"Left click for more objects. Right click to clear the scene.",
				20, 20);
	}
	
	private void paintNodeBounds(Graphics g, BoundingSphereNode node){
		if (!node.isLeaf()) {
			g.setColor(Color.RED);
			int r = (int) node.bounds.getRadius();
			int x = (int) node.bounds.getOrigin().getX(), y = (int) node.bounds
					.getOrigin().getY();
			g.drawOval(x - r, y - r, r + r, r + r);
			paintNodeBounds(g, node.child1);
			paintNodeBounds(g, node.child2);
		}
	}

	private void paintNodeLeaf(Graphics g, BoundingSphereNode node) {
		if (node.isLeaf()) {
			g.setColor(Color.DARK_GRAY);
			int r = (int) node.bounds.getRadius();
			int x = (int) node.bounds.getOrigin().getX(), y = (int) node.bounds
					.getOrigin().getY();
			g.fillOval(x - r, y - r, r + r, r + r);
		}else{
			paintNodeLeaf(g, node.child1);
			paintNodeLeaf(g, node.child2);
		}
	}

	private void setQuality(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(KEY_TEXT_LCD_CONTRAST, 100);
		g2d.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(KEY_ALPHA_INTERPOLATION,
				VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (SwingUtilities.isRightMouseButton(arg0)) {
			root = null;
		} else if (SwingUtilities.isLeftMouseButton(arg0)) {
			Vector3f pos = new Vector3f(arg0.getX(), arg0.getY(), 0);
			 float r = (float) (5 + Math.random() * 45);
			Sphere s = new Sphere(pos, r);
			if (root == null)
				root = new BoundingSphereNode(s);
			else
				root.inserte(s);
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
