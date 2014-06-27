package c.j.g.ray.simd.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This can be used to define the code extracted by the {@link SourceCreater}.
 * If this annotation is used on a class the provided code will be the head of
 * the extracted. if it annotates a method no order is guaranteed.
 * 
 * @author CJG
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SourcePart {
	String[] value();
}
