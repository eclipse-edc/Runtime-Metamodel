package org.eclipse.edc.runtime.metamodel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes that a certain class is a "configuration object", i.e. contains fields annotated with {@link Setting}. Note that
 * if the configuration object is a record, it may ONLY contain fields annotated with {@link Setting}.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Settings {
}
