package org.eclipse.edc.runtime.metamodel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that a certain field is a "configuration object", i.e. a POJO that contains fields annotated with
 * {@link Setting}. These fields must be declared inside an extension class. Their respective class declaration must be annotated
 * with {@link Settings}.
 * Types annotated with this annotation will get instantiated by the EDC dependency injection mechanism using values from the config.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Configuration {
}
