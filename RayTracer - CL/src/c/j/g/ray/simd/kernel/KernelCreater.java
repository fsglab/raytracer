package c.j.g.ray.simd.kernel;

import java.lang.reflect.*;

public class KernelCreater {

	private final String kernelCode;

	public <Kernel extends Object> KernelCreater(Kernel kernel) {
		Class<? extends Object> kernelClass = kernel.getClass();

		try {
			StringBuilder builder = new StringBuilder();
			for (Method m : kernelClass.getDeclaredMethods())
				if (m.isAnnotationPresent(KernelPart.class))
					for (String code : m.getAnnotation(KernelPart.class)
							.value())
						builder.append(code).append("\n");
			String code = builder.toString();
			if (code.isEmpty())
				throw new IllegalArgumentException("Empty code");

			for (Field f : kernelClass.getDeclaredFields())
				if (f.isAnnotationPresent(KernelFinal.class)) {
					if (!Modifier.isFinal(f.getModifiers()))
						throw new IllegalArgumentException(f.getName()
								+ " has to be final!");
					f.setAccessible(true);
					code = code.replace(f.getName(), f.get(kernel).toString());
				}

			kernelCode = code;
		} catch (Exception e) {
			throw new RuntimeException("For: " + kernel, e);
		}
	}

	public String getCode() {
		return kernelCode;
	}
}
