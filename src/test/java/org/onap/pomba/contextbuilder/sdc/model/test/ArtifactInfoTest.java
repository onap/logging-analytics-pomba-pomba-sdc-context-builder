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

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.model.ArtifactInfo;

public class ArtifactInfoTest {

    @Test
    public void testArtifactInfo() throws Exception {
     String url = "/sdc/v1/catalog/services/e9851a43-c068-4eb2-9fe7-2d123bd94ff0/toscaModel";
     String artifactType = "TOSCA_CSAR";
     ArtifactInfo artifact = new ArtifactInfo();
     artifact.setArtifactType(artifactType);
     artifact.setArtifactURL(url);

     assertTrue("Authorization doesn't match", artifact.getArtifactType().equals(artifactType));
     assertTrue("Authorization doesn't match", artifact.getArtifactURL().equals(url));
}
}