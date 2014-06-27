package c.j.g.ray.simd.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Util {

	public static String toString(Method m) {
		StringBuilder b = new StringBuilder();
		b.append(Modifier.toString(m.getModifiers())).append(" ");
		b.append(m.getReturnType()).append(" ");
		b.append(m.getName());
		b.append("(");
		for (int i = 0, pc = m.getParameterCount(); i < pc; ++i) {
			b.append(m.getParameterTypes()[i].getSimpleName()).append(" ");
			b.append(m.getParameters()[i].getName());
			if (i < pc - 1)
				b.append(", ");
		}
		b.append(");");

		return b.toString();
	}

	public static String toString(Constructor<?> c) {
		StringBuilder b = new StringBuilder();
		b.append(Modifier.toString(c.getModifiers())).append(" ");
		b.append(c.getName());
		b.append("(");
		for (int i = 0, pc = c.getParameterCount(); i < pc; ++i) {
			b.append(c.getParameterTypes()[i].getSimpleName()).append(" ");
			b.append(c.getParameters()[i].getName());
			if (i < pc - 1)
				b.append(", ");
		}
		b.append(");");

		return b.toString();
	}

}
