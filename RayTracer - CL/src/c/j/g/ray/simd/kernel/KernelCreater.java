package c.j.g.ray.simd.kernel;

import java.lang.reflect.*;

/**
 * This will build the kernel code from a given object.
 * 
 * @see KernelPart
 * @see KernelFinal
 * @author CJG
 *
 */
public class KernelCreater {

	/**
	 * The extracted code.
	 */
	private final String kernelCode;

	/**
	 * This will build the kernel source from the given kernel object.
	 * 
	 * @param kernel
	 *            the kernel object which contains the code and fields.
	 */
	public <Kernel extends Object> KernelCreater(Kernel kernel) {
		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));
		if (debug)
			System.out.println("D: Extract kernel code from: \"" + kernel
					+ "\"");
		try {
			String code = extractBasicCode(kernel);
			code = setDefines(kernel, code);
			kernelCode = code;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (debug) {
				System.out.println("D: Kernel code extracted.");
				System.out.println("-----------");
			}
		}
	}

	private <Kernel extends Object> String setDefines(Kernel kernel, String code)
			throws IllegalArgumentException, IllegalAccessException {

		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));

		Class<? extends Object> kernelClass = kernel.getClass();

		for (Field f : kernelClass.getDeclaredFields())
			if (f.isAnnotationPresent(KernelFinal.class)) {
				if (!Modifier.isFinal(f.getModifiers()))
					throw new IllegalArgumentException(f.getName()
							+ " has to be final!");
				f.setAccessible(true);
				code = code.replace(f.getName(), f.get(kernel).toString());
				if (debug)
					System.out.println("D: Replace: " + f.getName() + " with "
							+ f.get(kernel).toString());
			}

		return code;
	}

	private <Kernel extends Object> String extractBasicCode(Kernel kernel) {

		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));

		Class<? extends Object> kernelClass = kernel.getClass();

		StringBuilder builder = new StringBuilder();
		for (Method m : kernelClass.getDeclaredMethods())
			if (m.isAnnotationPresent(KernelPart.class))
				for (String code : m.getAnnotation(KernelPart.class).value())
					if (!code.trim().startsWith("//")) {
						builder.append(code).append("\n");
						if (debug)
							System.out.println("D: Add \"" + code + "\"");
					} else if (debug)
						System.out.println("D: Ignore \"" + code + "\"");
		String code = builder.toString();
		if (code.isEmpty())
			throw new IllegalArgumentException("Empty code for: " + kernel);
		return code;
	}

	public String getCode() {
		return kernelCode;
	}
}
