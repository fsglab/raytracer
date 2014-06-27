package c.j.g.ray.simd.source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This can be used to annotate fields in classes parsed to a
 * {@link SourceCreater} instance. It indicates that all string literals which
 * are equals to the field name are replaced by its value.
 * 
 * @author CJG
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SourceFinal {
}
