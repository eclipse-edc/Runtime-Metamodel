/*
 *  Copyright (c) 2026 Metaform Systems, Inc.
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Apache License, Version 2.0 which is available at
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 *  SPDX-License-Identifier: Apache-2.0
 *
 *  Contributors:
 *       Metaform Systems, Inc. - initial API and implementation
 *
 */

package org.eclipse.edc.plugins.autodoc.tasks;

import org.gradle.api.logging.Logging;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JsonFileAppenderTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonFileAppender appender = new JsonFileAppender(Logging.getLogger(JsonFileAppenderTest.class));

    @Test
    void append_shouldCreateDestination_whenMissing(@TempDir Path dir) throws IOException {
        var destination = dir.resolve("manifest.json").toFile();

        appender.append(destination, manifest(dir, "module-1"));

        assertThat(modules(destination)).containsExactly("module-1");
    }

    @Test
    void append_shouldKeepPreviousContent(@TempDir Path dir) throws IOException {
        var destination = dir.resolve("manifest.json").toFile();

        appender.append(destination, manifest(dir, "module-1"));
        appender.append(destination, manifest(dir, "module-2"));
        appender.append(destination, manifest(dir, "module-3"));

        assertThat(modules(destination)).containsExactly("module-1", "module-2", "module-3");
    }

    private File manifest(Path dir, String modulePath) throws IOException {
        var file = dir.resolve(modulePath + ".json");
        Files.writeString(file, mapper.writeValueAsString(List.of(Map.of("modulePath", modulePath))));
        return file.toFile();
    }

    private List<Object> modules(File destination) {
        return mapper.readValue(destination, new TypeReference<List<Map<String, Object>>>() {
        }).stream().map(it -> it.get("modulePath")).toList();
    }
}
