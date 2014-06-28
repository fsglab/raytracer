package c.j.g.ray.simd.tracer.eyeRayCreator;

import java.nio.IntBuffer;

import c.j.g.ray.simd.Camera;
import c.j.g.ray.simd.util.Buffer;

public class EyeRayCreator {

	private int width = 600, height = 400;
	private Camera cam;
	private IntBuffer size;
	
	public EyeRayCreator(Camera cam, int width, int height){
		this.cam = cam;
		this.width = width;
		this.height = height;
		size = Buffer.toBuffer(width, height);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		size.put(0, width);
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		size.put(1, height);
	}

	public Camera getCam() {
		return cam;
	}

	public void setCam(Camera cam) {
		this.cam = cam;
	}
	


}
