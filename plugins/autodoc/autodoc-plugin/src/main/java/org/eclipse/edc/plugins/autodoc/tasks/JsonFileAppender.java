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

package org.eclipse.edc.plugins.autodoc.tasks;

import org.gradle.api.GradleException;
import org.gradle.api.logging.Logger;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

/**
 * Appends the contents of one file to another.
 * The contents of either file must be interpretable as {@link List}
 */
class JsonFileAppender {
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final Logger logger;
    private final ObjectMapper mapper;
    private final TypeReference<List<?>> listTypeReference;

    JsonFileAppender(Logger logger) {
        this.logger = logger;
        mapper = new ObjectMapper();
        listTypeReference = new TypeReference<>() {
        };
    }

    /**
     * Appends the contents of the source file to the destination file.
     * It reads the contents of both files as {@link List}, and appends the contents of the source file to the destination file.
     * This method is threadsafe.
     *
     * @param destination The file to which the list get appended to
     * @param source      The file whose contents are appended
     */
    public void append(File destination, File source) {
        logger.lifecycle(format("Appending contents of [%s] to [%s]", source, destination));

        checkOrCreate(destination);
        if (!source.exists()) {
            throw new GradleException(format("Source file [%s] does not exist!", source));
        }

        LOCK.lock();
        try {
            var srcContent = mapper.readValue(source, listTypeReference);
            var targetContent = mapper.readValue(source, listTypeReference);

            var newContent = new ArrayList<>();
            newContent.addAll(targetContent);
            newContent.addAll(srcContent);

            mapper.writeValue(destination, newContent);
        } catch (JacksonException e) {
            throw new GradleException("Error reading input manifest", e);
        } finally {
            LOCK.unlock();
        }

    }

    private void checkOrCreate(File destination) {
        if (!destination.exists()) {
            logger.warn("Destination file {} does not exist, creating...", destination);
            try {
                if (!destination.createNewFile()) {
                    destinationError(destination, null);
                }
            } catch (IOException e) {
                destinationError(destination, e);
            }
        }
    }

    private void destinationError(File destination, Throwable t) {
        logger.error("Could not create destination file {}", destination);
        throw new GradleException("Error creating destination file", t);
    }
}
