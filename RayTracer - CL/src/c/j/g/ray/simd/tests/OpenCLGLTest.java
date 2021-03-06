package c.j.g.ray.simd.tests;

import static org.lwjgl.opencl.CL10.CL_DEVICE_TYPE_GPU;
import static org.lwjgl.opencl.CL10.CL_MEM_COPY_HOST_PTR;
import static org.lwjgl.opencl.CL10.CL_MEM_READ_WRITE;
import static org.lwjgl.opencl.CL10.CL_QUEUE_PROFILING_ENABLE;
import static org.lwjgl.opencl.CL10.clBuildProgram;
import static org.lwjgl.opencl.CL10.clCreateBuffer;
import static org.lwjgl.opencl.CL10.clCreateCommandQueue;
import static org.lwjgl.opencl.CL10.clCreateKernel;
import static org.lwjgl.opencl.CL10.clCreateProgramWithSource;
import static org.lwjgl.opencl.CL10.clEnqueueNDRangeKernel;
import static org.lwjgl.opencl.CL10.clEnqueueReadBuffer;
import static org.lwjgl.opencl.CL10.clEnqueueWriteBuffer;
import static org.lwjgl.opencl.CL10.clFinish;
import static org.lwjgl.opencl.CL10.clReleaseCommandQueue;
import static org.lwjgl.opencl.CL10.clReleaseContext;
import static org.lwjgl.opencl.CL10.clReleaseKernel;
import static org.lwjgl.opencl.CL10.clReleaseProgram;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opencl.CL;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLCommandQueue;
import org.lwjgl.opencl.CLContext;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLKernel;
import org.lwjgl.opencl.CLMem;
import org.lwjgl.opencl.CLPlatform;
import org.lwjgl.opencl.CLProgram;
import org.lwjgl.opencl.Util;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import c.j.g.ray.simd.source.SourceCreator;
import c.j.g.ray.simd.source.SourceFinal;
import c.j.g.ray.simd.source.SourcePart;

public class OpenCLGLTest {
	@SourcePart({
			"kernel void",
			"adv(global float *x, global float *y, global float *vx, global float *vy){",

			"	unsigned int xid 	= get_global_id(0);",
			"	unsigned int xid2	= (xid + 1) % particels;",

			"	float dx, dy;",
			"	float dist, rap;",
			"	vx[xid] *= 0.7f;",
			"	vy[xid] *= 0.7f;",

			"	for(int i = 0; i < particels; i++){",
			"		dx = x[i] - x[xid];",
			"		dy = y[i] - y[xid];",
			"		dist = sqrt(dx*dx + dy*dy);",

			"		if(dist < mindist){",
			"			rap = (mindist - dist)/mindist;",
			"			vx[xid] -= dx * rap * 0.02f;",
			"			vy[xid] -= dy * rap * 0.02f;",
			"		}",
			"	}",

			"	vx[xid] += (x[xid2] - x[xid]) * 0.05f;",
			"	vy[xid] += (y[xid2] - y[xid]) * 0.05f;",
			"	x[xid]  += vx[xid];",
			"	y[xid]  += vy[xid];",

			"	if(x[xid] < 0) x[xid] = 0; else if(x[xid] > width) x[xid] = width;",
			"	if(y[xid] < 0) y[xid] = 0; else if(y[xid] > height) y[xid] = height;",
			"}" })
	private static class Kernel {

		@SourceFinal
		private final int particels, width, height, mindist;

		Kernel(int particels, int width, int height, int mindist) {
			this.particels = particels;
			this.width = width;
			this.height = height;
			this.mindist = mindist;
		}

		@Override
		public String toString() {
			return String.format(
					"Kernel [particels=%s, width=%s, height=%s, mindist=%s]",
					particels, width, height, mindist);
		}

	}

	private static CLContext context;
	private static final int HEIGHT = 400;
	private static CLKernel kernel;
	private static PointerBuffer kernel1DGlobalWorkSize;
	private static final int MIN_DISTANCE = 20;
	private static final int PARTICLES = (int) 1.5e2;
	private static CLProgram program;
	private static CLCommandQueue queue;
	private static final int START_VEL = 10;
	private static final int WIDTH = 600;
	private static FloatBuffer x, y, vx, vy;
	private static CLMem xMem, yMem, vxMem, vyMem;

	public static void checkProgram(CLProgram program, CLDevice device) {
		PointerBuffer buffer = BufferUtils.createPointerBuffer(1);
		CL10.clGetProgramBuildInfo(program, device, CL10.CL_PROGRAM_BUILD_LOG,
				null, buffer);
		if (buffer.get(0) > 2) {
			ByteBuffer log = BufferUtils.createByteBuffer((int) buffer.get(0));
			CL10.clGetProgramBuildInfo(program, device,
					CL10.CL_PROGRAM_BUILD_LOG, log, buffer);
			byte bytes[] = new byte[log.capacity()];
			log.get(bytes);
			System.out.println(String.format("CL Compiler Error/Warning:\n %s",
					new String(bytes)));
		}
	}

	static void drawAllParticles() {

		glColor3f(1.0f, 0.0f, 0.0f);
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			glBegin(GL_QUADS);
			final int size = 2;
			for (int i = 0; i < PARTICLES; i++) {
				float tx = x.get(i);
				float ty = y.get(i);

				glVertex2f(tx, ty);
				glVertex2f(tx + size, ty);
				glVertex2f(tx + size, ty + size);
				glVertex2f(tx, ty + size);
			}
		} else {
			glBegin(GL_LINES);
			float ox = x.get(PARTICLES - 1), oy = y.get(PARTICLES - 1);
			for (int i = 0; i < PARTICLES; i++) {
				glVertex2f(ox, oy);
				glVertex2f((ox = x.get(i)), (oy = y.get(i)));
			}
		}

		glEnd();
	}

	static void freeCL() {
		clReleaseKernel(kernel);
		clReleaseProgram(program);
		clReleaseCommandQueue(queue);
		clReleaseContext(context);
		CL.destroy();
	}

	static void freeGL() {
		Display.destroy();
	}

	private static void initCL() throws LWJGLException {
		x = toFloatBuffer(randomFloatArray(PARTICLES, 0, WIDTH));
		y = toFloatBuffer(randomFloatArray(PARTICLES, 0, HEIGHT));
		vx = toFloatBuffer(randomFloatArray(PARTICLES, -START_VEL, START_VEL));
		vy = toFloatBuffer(randomFloatArray(PARTICLES, -START_VEL, START_VEL));

		// create
		CL.create();
		CLPlatform platform = CLPlatform.getPlatforms().get(0);

		List<CLDevice> devices = platform.getDevices(CL_DEVICE_TYPE_GPU);
		context = CLContext.create(platform, devices, null, null, null);
		queue = clCreateCommandQueue(context, devices.get(0),
				CL_QUEUE_PROFILING_ENABLE, null);

		// memory
		xMem = clCreateBuffer(context,
				CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR, x, null);
		clEnqueueWriteBuffer(queue, xMem, 1, 0, x, null, null);
		yMem = clCreateBuffer(context,
				CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR, y, null);
		clEnqueueWriteBuffer(queue, yMem, 1, 0, y, null, null);
		vxMem = clCreateBuffer(context, CL_MEM_READ_WRITE
				| CL_MEM_COPY_HOST_PTR, vx, null);
		clEnqueueWriteBuffer(queue, vxMem, 1, 0, vx, null, null);
		vyMem = clCreateBuffer(context, CL_MEM_READ_WRITE
				| CL_MEM_COPY_HOST_PTR, vy, null); // CL_MEM_WRITE_ONLY
		clEnqueueWriteBuffer(queue, vyMem, 1, 0, vy, null, null);
		clFinish(queue);

		// kernel
		Kernel kernelCode = new Kernel(PARTICLES, WIDTH, HEIGHT, MIN_DISTANCE);
		String src = new SourceCreator(kernelCode).getCode();
		System.out.println(src);
		program = clCreateProgramWithSource(context, src, null);
		int err = clBuildProgram(program, devices.get(0), "", null);
		checkProgram(program, devices.get(0));
		Util.checkCLError(err);

		kernel = clCreateKernel(program, "adv", null);
		kernel.setArg(0, xMem);
		kernel.setArg(1, yMem);
		kernel.setArg(2, vxMem);
		kernel.setArg(3, vyMem);

		kernel1DGlobalWorkSize = BufferUtils.createPointerBuffer(1);
		kernel1DGlobalWorkSize.put(0, PARTICLES);
	}

	private static void initGL() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		Display.setTitle("Particle Demo");
		Display.create();

		glMatrixMode(GL_PROJECTION);
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
	}

	public static void main(String... vargs) throws LWJGLException {
		System.setProperty("debug", "true");

		initGL();
		initCL();

		glMatrixMode(GL_MODELVIEW);
		while (!Display.isCloseRequested()) {

			stepCL();

			glClear(GL_COLOR_BUFFER_BIT);
			drawAllParticles();

			Display.update();
			Display.sync(30);
		}

		freeGL();
		freeCL();
	}

	private static float[] randomFloatArray(int count, float min, float max) {
		float[] ret = new float[count];
		for (int i = 0; i < ret.length; i++)
			ret[i] = (float) (Math.random() * (max - min) + min);
		return ret;
	}

	private static void stepCL() {
		clEnqueueNDRangeKernel(queue, kernel, 1, null, kernel1DGlobalWorkSize,
				null, null, null);
		clEnqueueReadBuffer(queue, xMem, 1, 0, x, null, null);
		clEnqueueReadBuffer(queue, yMem, 1, 0, y, null, null);
		clFinish(queue);
	}

	private static FloatBuffer toFloatBuffer(float[] floats) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(floats.length).put(
				floats);
		buf.rewind();
		return buf;
	}

}
