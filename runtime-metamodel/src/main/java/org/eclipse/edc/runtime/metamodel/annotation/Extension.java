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
 *
 */

package org.eclipse.edc.runtime.metamodel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes an extension module.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Extension {

    /**
     * The readable module name.
     */
    String value();

    /**
     * Optional categories used to classify this module.
     */
    String[] categories() default "";

    /**
     * The configuration key of the feature this extension is bound to, e.g. {@code edc.feature.persistence}. An extension
     * that is bound to a feature is only loaded if this key is configured with {@link #featureValue()}. By convention,
     * all feature keys share the prefix {@code edc.feature}.
     * <p>
     * An empty string (the default) means the extension is not bound to a feature and is always loaded. If set,
     * {@link #featureValue()} must be set as well.
     */
    String featureKey() default "";

    /**
     * The value that {@link #featureKey()} must be configured with for this extension to be loaded, e.g. {@code postgres}.
     * The comparison is exact. Only evaluated if {@link #featureKey()} is set.
     */
    String featureValue() default "";

    /**
     * Whether this extension is loaded if {@link #featureKey()} is not configured at all. This is how the default
     * implementation of a feature is expressed. Only evaluated if {@link #featureKey()} is set.
     */
    boolean loadAsDefault() default false;

}
