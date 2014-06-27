package c.j.g.ray.simd.tracer.eyeRayCreator;

import c.j.g.ray.simd.Camera;
import c.j.g.ray.simd.geo.Ray;
import c.j.g.ray.simd.source.SourceCreator;
import c.j.g.ray.simd.source.SourceFinal;
import c.j.g.ray.simd.source.SourceInclude;
import c.j.g.ray.simd.source.SourcePart;

public class EyeRayCreator {

	private int width = 600, height = 400;
	private Camera cam;
	
	public EyeRayCreator(Camera cam){
		this.cam = cam;
		create();
	}
	
	private void create(){
		System.out.println(new SourceCreator(new RayCreatorSource(width, height, cam.getFov())).getCode());
	}
	
	@SourceInclude(Ray.class)
	@SourcePart("")
	private static class RayCreatorSource{
		
		@SourceFinal
		private final int width, height;
		@SourceFinal
		private final float fov;
		
		public RayCreatorSource(int width, int height, float fov){
			this.width = width;
			this.height = height;
			this.fov = fov;
		}
		
	}
	
}
