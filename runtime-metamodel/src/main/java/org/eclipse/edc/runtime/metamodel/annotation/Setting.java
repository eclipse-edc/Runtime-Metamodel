/*
 *  Copyright (c) 2022 Microsoft Corporation
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Microsoft Corporation - initial API and implementation
 *       Bayerische Motoren Werke Aktiengesellschaft (BMW AG) - improvements
 *
 */

package org.eclipse.edc.runtime.metamodel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a runtime configuration setting. This can be put on config value fields and the dependency injection mechanism will
 * attempt to automatically resolve them.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Setting {
    String NULL = "";

    /**
     * The setting description.
     *
     * @deprecated Please use {@link Setting#description()} to supply description. In future releases this property will hold the Setting's config key!
     */
    @Deprecated
    String value() default NULL;

    /**
     * The setting context
     */
    String context() default NULL;

    /**
     * Type of the config value
     *
     * @deprecated this attribute is deprecated because it will be inferred from the field
     */
    @Deprecated
    String type() default "string";

    /**
     * The setting default value. Empty string if no default value is provided
     *
     * @return the setting's default value
     */
    String defaultValue() default NULL;

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    /**
     * Returns true if the setting is required.
     */
    boolean required() default true;

    /**
     * The key of the property, e.g. "edc.foo.bar.baz". If this attribute is present, the dependency injection mechanism
     * will attempt to resolve the config value.
     */
    String key() default NULL;

    /**
     * The Setting's description. This is equivalent to {@link Setting#value()} in the current release, but users should
     * use this attribute.
     *
     * @return The setting's description.
     */
    String description() default NULL;

    /**
     * Specify whether a warning should be issued when an optional config value is missing or when the default value is used.
     * If set to false, a debug log is issued.
     * Note that this is ignored on mandatory (non-optional) config values.
     */
    boolean warnOnMissingConfig() default false;
}
