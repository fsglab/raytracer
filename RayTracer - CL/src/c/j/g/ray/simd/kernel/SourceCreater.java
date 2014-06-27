package c.j.g.ray.simd.kernel;

import java.lang.reflect.*;

import c.j.g.ray.simd.util.Util;

/**
 * This will build the kernel code from a given object.
 * 
 * @see SourcePart
 * @see SourceFinal
 * @author CJG
 *
 */
public class SourceCreater {

	/**
	 * The extracted code.
	 */
	private final String sourceCode;

	/**
	 * This will build the source code from the given source object. If the
	 * source object contains multiply methods annotated with {@link SourcePart} those
	 * will be added in no specific order.
	 * 
	 * @see SourcePart
	 * @see SourceFinal
	 * @param source
	 *            the source object which contains the code and fields.
	 */
	public <Source extends Object> SourceCreater(Source source) {
		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));
		if (debug)
			System.out.println("D: Extract source code from: \"" + source
					+ "\"");
		try {
			String code = extractBasicCode(source);
			code = setDefines(source, code);
			sourceCode = code;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (debug) {
				System.out.println("D: Source code extracted.");
				System.out.println("-----------");
			}
		}
	}

	private <Source extends Object> String setDefines(Source source, String code)
			throws IllegalArgumentException, IllegalAccessException {

		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));

		Class<? extends Object> sourceClass = source.getClass();

		for (Field f : sourceClass.getDeclaredFields())
			if (f.isAnnotationPresent(SourceFinal.class)) {
				if (!Modifier.isFinal(f.getModifiers()))
					throw new IllegalArgumentException(f.getName()
							+ " has to be final!");
				f.setAccessible(true);
				code = code.replace(f.getName(), f.get(source).toString());
				if (debug)
					System.out.println("D: Replace: " + f.getName() + " with "
							+ f.get(source).toString());
			}

		return code;
	}

	private <Source extends Object> String extractBasicCode(Source source) {

		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));

		Class<? extends Object> sourceClass = source.getClass();

		StringBuilder builder = new StringBuilder();

		if (sourceClass.isAnnotationPresent(SourcePart.class)) {
			if (debug)
				System.out.println("D: Extract from " + sourceClass.getSimpleName()+" class");
			addStrings(sourceClass.getAnnotation(SourcePart.class).value(), builder,
					debug);
		}

		for (Method m : sourceClass.getDeclaredMethods())
			if (m.isAnnotationPresent(SourcePart.class)) {
				if (debug)
					System.out.println("D: Extract from " + Util.toString(m));
				addStrings(m.getAnnotation(SourcePart.class).value(), builder,
						debug);
			}
		String code = builder.toString();
		if (code.isEmpty())
			throw new IllegalArgumentException("Empty code for: " + source);
		return code;
	}

	private void addStrings(String[] lines, StringBuilder builder, boolean debug) {
		for (String code : lines)
			if (!code.trim().startsWith("//")) {
				builder.append(code).append("\n");
				if (debug)
					System.out.println("D: Add \"" + code + "\"");
			} else if (debug)
				System.out.println("D: Ignore \"" + code + "\"");
	}

	public String getCode() {
		return sourceCode;
	}
}
