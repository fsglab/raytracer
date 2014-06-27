package c.j.g.ray.simd.source;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
	 * source object contains multiply methods annotated with {@link SourcePart}
	 * those will be added in no specific order.
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

	/**
	 * This will add the given lines to the StringBuilder.
	 * 
	 * @param lines
	 *            the lines to add. Lines starting with '//' will be ignored.
	 * @param builder
	 *            the StringBuilder which will contain the code.
	 * @param debug
	 *            if the debug flag is set.
	 */
	private void addLines(String[] lines, StringBuilder builder, boolean debug) {
		for (String code : lines)
			if (!code.trim().startsWith("//")) {
				builder.append(code).append("\n");
				if (debug)
					System.out.println("D: Add \"" + code + "\"");
			} else if (debug)
				System.out.println("D: Ignore \"" + code + "\"");
	}

	/**
	 * This will extract the basic code from the given source object. If the
	 * object class is annotated with the {@link SourcePart} it will be the
	 * first code. Annotated methods provide no given order.
	 * 
	 * @param source
	 *            the object which contains the source.
	 * @return the extracted raw source.
	 */
	private <Source extends Object> String extractBasicCode(Source source) {

		boolean debug = "true".equalsIgnoreCase(System.getProperty("debug"));

		Class<? extends Object> sourceClass = source.getClass();

		StringBuilder builder = new StringBuilder();

		if (sourceClass.isAnnotationPresent(SourcePart.class)) {
			if (debug)
				System.out.println("D: Extract from "
						+ sourceClass.getSimpleName() + " class");
			addLines(sourceClass.getAnnotation(SourcePart.class).value(),
					builder, debug);
		}

		for (Method m : sourceClass.getDeclaredMethods())
			if (m.isAnnotationPresent(SourcePart.class)) {
				if (debug)
					System.out.println("D: Extract from " + Util.toString(m));
				addLines(m.getAnnotation(SourcePart.class).value(), builder,
						debug);
			}
		String code = builder.toString();
		// code = code.trim().replaceAll("[ \t]+", " ");

		if (code.isEmpty())
			throw new IllegalArgumentException("Empty code for: " + source);
		return code;
	}

	/**
	 * The extracted code.
	 * 
	 * @return {@link #sourceCode}
	 */
	public String getCode() {
		return sourceCode;
	}

	/**
	 * This will set the final variables. It replaces every string named like
	 * the annotated fields with there value.
	 * 
	 * @param source
	 *            the source object.
	 * @param code
	 *            the raw code extracted by {@link #extractBasicCode(Object)}
	 * @return the provided code with replaced defines.
	 * @throws IllegalArgumentException
	 *             this will be thrown if a annotated field is not final.
	 * @throws IllegalAccessException
	 *             if the security manager is to high but the field is private.
	 */
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
}
