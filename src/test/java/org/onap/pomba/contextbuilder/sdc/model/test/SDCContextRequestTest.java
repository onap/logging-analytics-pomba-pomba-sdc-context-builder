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

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextRequest;

public class SDCContextRequestTest {


    @Test
    public void testSDCContextRequest() throws Exception {
        String authorization = "Basic " + Base64.getEncoder().encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8));
        String fromAppId = "POMBA";
        String transactionId = UUID.randomUUID().toString();
        String serviceInstanceId = "b06270ab-99e6-4a58-9bc0-db2df5c36f4d";
        String modelVersionId= "e9851a43-c068-4eb2-9fe7-2d123bd94ff0";
        String modelInvariantId = "4fd21763-23ed-4f69-8654-e121626df327" ;

        SDCContextRequest sdcContext = new SDCContextRequest(null, authorization, fromAppId, transactionId,
                serviceInstanceId, modelVersionId, modelInvariantId);
        sdcContext.toString().contains(fromAppId);
        sdcContext.toString().contains(transactionId);
        sdcContext.toString().contains(serviceInstanceId);
        sdcContext.toString().contains(modelVersionId);
        sdcContext.toString().contains(modelInvariantId);

        assertEquals(authorization, sdcContext.getAuthorization());
        assertEquals(fromAppId, sdcContext.getFromAppId());
        assertEquals(transactionId, sdcContext.getTransactionId());
        assertEquals(serviceInstanceId, sdcContext.getServiceInstanceId());
        assertEquals(modelVersionId, sdcContext.getModelVersionId());
        assertEquals(modelInvariantId, sdcContext.getModelInvariantId());
    }

}
