package c.j.g.ray.simd.tracer.eyeRayCreator;

import c.j.g.ray.simd.Camera;

public class EyeRayCreator {

	private int width = 600, height = 400;
	private Camera cam;
	
	public EyeRayCreator(Camera cam){
		this.cam = cam;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
	}
	


}
