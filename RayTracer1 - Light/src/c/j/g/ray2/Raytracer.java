package c.j.g.ray2;

import static c.j.g.ray2.Color.add;
import static c.j.g.ray2.Vec3.nor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.Data;
import lombok.experimental.Builder;
import c.j.g.ray2.light.AmbientLighting;
import c.j.g.ray2.light.DiffusePointLight;
import c.j.g.ray2.light.Light;
import c.j.g.ray2.light.SpecularPointLight;

@Data
@Builder
public class Raytracer {

	public static void main(String[] args) throws IOException {
		Sphere[] spheren = new Sphere[3];
		spheren[0] = new Sphere(new Color(0, 0, 1), new Color(1, 1, 1),
				new Vec3(0, 0, 10), 4, 50);
		spheren[1] = new Sphere(new Color(1, 0, 0), new Color(1, 1, 1),
				new Vec3(4, 5, 14), 4, 50);
		spheren[2] = new Sphere(new Color(0, 1, 0), new Color(1, 1, 1),
				new Vec3(3, -4, 9), 2, 50);

		Light[] lights = new Light[3];
		lights[0] = new AmbientLighting(new Color(0.0, 0.0, 0.0));
		lights[1] = new DiffusePointLight(new Vec3(-5, -5, 0), new Color(1, 1,
				1), 50);
		lights[2] = new SpecularPointLight(new Vec3(-5, -5, 0), new Color(1, 1,
				1), 1e2);

		Raytracer tracer = new Raytracer(spheren, lights);
		BufferedImage im = tracer.render(600, 400, 330);
		File imgs = new File("img");
		imgs.mkdirs();
		ImageIO.write(im, "png", new File(imgs, "test1.png"));
	}

	private final Sphere[] spheren;
	private final Light[] lights;

	private BufferedImage render(int width, int height, double camDistance) {
		BufferedImage im = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		int hW = width / 2, hH = height / 2;
		for (int x = 0; x < width; ++x)
			for (int y = 0; y < height; ++y) {
				Ray r = getRay(x - hW, y - hH, camDistance);
				im.setRGB(x, y, getCol(r).getRGB());
			}

		return im;
	}

	private Color getCol(Ray ray) {
		HitInfo akt = null;
		for (Sphere s : spheren) {
			HitInfo i = s.getNearestIntersection(ray);
			if (i != null)
				if (akt == null || i.getDistance() < akt.getDistance())
					akt = i;
		}

		Color col = Color.BLACK;
		if (akt != null)
			for (Light l : lights)
				col = add(col, l.getColor(akt));
		return col;

	}

	private Ray getRay(int px, int py, double camDistance) {
		return new Ray(Vec3.O, nor(new Vec3(px, py, camDistance)));
	}

}
