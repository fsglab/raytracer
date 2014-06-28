package c.j.g.ray.simd.tests;

import java.nio.FloatBuffer;
import java.util.Arrays;

import c.j.g.ray.simd.Camera;
import c.j.g.ray.simd.geo.math.Quaternion;
import c.j.g.ray.simd.geo.math.Vector3f;

public class CameraTest {

	public static void main(String... s) {
		Camera cam = new Camera();
		sysoBuffer(cam.getBuffer());
		cam = new Camera(new Vector3f(1, 2, 3), new Quaternion(.7071068f, 0,
				.7071068f, 0), 75);
		sysoBuffer(cam.getBuffer());
	}

	private static void sysoBuffer(FloatBuffer buf) {
		if (buf.hasArray())
			System.out.println(Arrays.toString(buf.array()));
		else {
			StringBuilder b = new StringBuilder().append("[");
			for (int i = 0, m = buf.capacity(); i < m; ++i)
				b.append(buf.get(i)).append(i < m - 1 ? ", " : "]");
			System.out.println(b.toString());
		}
	}

}
