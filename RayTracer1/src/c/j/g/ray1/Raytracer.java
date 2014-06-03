package c.j.g.ray1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Builder;
import static c.j.g.ray1.Vec3.*;

@Data
@Builder
@AllArgsConstructor
public class Raytracer {

    public static void main(String[] args) throws IOException {
	Sphere[] spheren = new Sphere[2];
	spheren[0] = new Sphere(new Color(0, 0, 1), new Vec3(0, 0, 10), 4);
	spheren[0] = new Sphere(new Color(1, 0, 0), new Vec3(4, 5, 14), 4);
	Raytracer tracer = new Raytracer(spheren);
	BufferedImage im = tracer.render(600, 400, 330);
	File imgs = new File("img");
	imgs.mkdirs();
	ImageIO.write(im, "png", new File(imgs, "test1.png"));
    }

    private final Sphere[] spheren;

    private BufferedImage render(int width, int height, double camDistance) {
	BufferedImage im = new BufferedImage(width, height,
		BufferedImage.TYPE_INT_RGB);

	int hW = width / 2, hH = height / 2;
	for (int x = 0; x < width; ++x)
	    for (int y = 0; y < height; ++y) {
		Ray r = getRay(x - hW, y - hH, camDistance);
	    }

	return im;
    }

    private Ray getRay(int px, int py, double camDistance) {
	return new Ray(Vec3.O, nor(new Vec3(px, py, camDistance)));
    }

}
