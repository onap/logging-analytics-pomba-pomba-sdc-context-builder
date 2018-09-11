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

package org.onap.pomba.contextbuilder.sdc.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.model.ArtifactInfo;
import org.openecomp.sdc.api.notification.IArtifactInfo;

public class ArtifactInfoTest {

    @Test
    public void testArtifactInfo() throws Exception {
        String url = "/sdc/v1/catalog/services/e9851a43-c068-4eb2-9fe7-2d123bd94ff0/toscaModel";
        String artifactType = "TOSCA_CSAR";
        ArtifactInfo artifact = new ArtifactInfo();
        artifact.setArtifactType(artifactType);
        artifact.setArtifactURL(url);
        artifact.setArtifactChecksum("artifactChecksum");
        artifact.setArtifactDescription("artifactDescription");
        artifact.setArtifactName("artifactName");
        artifact.setArtifactTimeout(1000);
        artifact.setArtifactUUID("ae04b88e-e2ee-4ce9-a62d-3d08cf0f46db");
        artifact.setArtifactVersion("artifactVersion");

        IArtifactInfo generatedArtifactTest = (IArtifactInfo)artifact;
        artifact.setGeneratedArtifact(generatedArtifactTest);

        assertEquals(artifactType, artifact.getArtifactType());
        assertEquals(url, artifact.getArtifactURL());
        assertEquals("artifactChecksum", artifact.getArtifactChecksum());
        assertEquals("artifactDescription", artifact.getArtifactDescription());
        assertEquals("artifactName", artifact.getArtifactName());
        assertEquals(Integer.valueOf(1000), artifact.getArtifactTimeout());
        assertEquals("ae04b88e-e2ee-4ce9-a62d-3d08cf0f46db", artifact.getArtifactUUID());
        assertEquals("artifactVersion", artifact.getArtifactVersion());
        assertEquals(generatedArtifactTest, artifact.getGeneratedArtifact());

    }
}