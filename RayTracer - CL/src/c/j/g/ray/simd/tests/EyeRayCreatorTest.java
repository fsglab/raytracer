package c.j.g.ray.simd.tests;

import c.j.g.ray.simd.Camera;
import c.j.g.ray.simd.tracer.eyeRayCreator.EyeRayCreator;

public class EyeRayCreatorTest {

	public static void main(String... s){
		Camera cam = new Camera();
		new EyeRayCreator(cam);
	}
	
}
