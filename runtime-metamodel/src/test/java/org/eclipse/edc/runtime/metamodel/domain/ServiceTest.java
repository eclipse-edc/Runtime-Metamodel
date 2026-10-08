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

package org.eclipse.edc.runtime.metamodel.domain;

import org.junit.jupiter.api.Test;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceTest {

    @Test
    void verifySerializeDeserialize() throws JacksonException {
        var mapper = new ObjectMapper();
        var service = new Service("foo.bar.BarService");

        var serialized = mapper.writeValueAsString(service);
        var deserialized = mapper.readValue(serialized, Service.class);

        assertThat(deserialized).isNotNull();
    }
}
