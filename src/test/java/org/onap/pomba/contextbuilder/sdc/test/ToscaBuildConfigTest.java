/*
 *  ============LICENSE_START===================================================
 * Copyright (c) 2018 Amdocs
 * ============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=====================================================
 */

package org.onap.pomba.contextbuilder.sdc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.ToscaBuilderConfig;

public class ToscaBuildConfigTest {


    @Test
    public void testToscaBuilderConfig() throws Exception {

        String urlTemplate = "/sdc/v1/catalog/services/e9851a43-c068-4eb2-9fe7-2d123bd94ff0/toscaModel";
        ToscaBuilderConfig configTest = new ToscaBuilderConfig();
        configTest.setCsarFilePrefix("csar-");
        configTest.setCsarFileSuffix(".zip");
        configTest.setArtifactType("TOSCA_CSAR");
        configTest.setUrlTemplate(urlTemplate);
        configTest.setTestToscaCsarFile("/src/test/toscaModel.zip");

        assertEquals("csar-", configTest.getCsarFilePrefix());
        assertEquals(".zip", configTest.getCsarFileSuffix());
        assertEquals("TOSCA_CSAR", configTest.getArtifactType());
        assertEquals(urlTemplate, configTest.getUrlTemplate());
        assertEquals("/src/test/toscaModel.zip", configTest.getTestToscaCsarFile());

        String str = configTest.toString();
        assertTrue(str.contains("TOSCA_CSAR"));
        assertTrue(str.contains("csar-"));
        assertTrue(str.contains(".zip"));
        assertTrue(str.contains("/sdc/v1/catalog/services/e9851a43-c068-4eb2-9fe7-2d123bd94ff0/toscaModel"));
        assertTrue(str.contains("/src/test/toscaModel.zip"));

    }

}
