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

package org.onap.pomba.contextbuilder.sdc.unittest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextRequest;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextResponse;
import org.onap.pomba.contextbuilder.sdc.service.SpringService;
import org.onap.pomba.contextbuilder.sdc.service.rs.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.VFModule;
import org.onap.pomba.common.datatypes.VNF;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@WebAppConfiguration
@SpringBootTest
public class SDCContextBuilderTest {

    static {
        System.setProperty("test.tosca.csar.file",
                            Paths.get(System.getProperty("user.dir"),
                            "src/test/resources/toscaModel.csar.zip").toString());
    }


    @Autowired
    private RestService service;
    @Autowired
    private SpringService springService;


    private String authorization = "Basic "
                                   + Base64.getEncoder()
                                   .encodeToString(("admin" + ":" + "admin")
                                   .getBytes(StandardCharsets.UTF_8));
    private String fromAppId = "POMBA";
    private String transactionId = UUID.randomUUID().toString();
    private String serviceInstanceId = "b06270ab-99e6-4a58-9bc0-db2df5c36f4d";
    private String modelVersionId = "e9851a43-c068-4eb2-9fe7-2d123bd94ff0";
    private String modelInvariantId = "4fd21763-23ed-4f69-8654-e121626df327" ;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testNoAuthHeader() throws Exception {
        Response response =  this.service.getContext(null,
                                                     fromAppId,
                                                     transactionId,
                                                     serviceInstanceId,
                                                     modelVersionId,
                                                     modelInvariantId);
        assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());

    }

    @Test
    public void testBadAuthoriztion() throws Exception {
        String authorizationTest = "Basic "
                                   + Base64.getEncoder()
                                   .encodeToString(("Test" + ":" + "Fake")
                                   .getBytes(StandardCharsets.UTF_8));
        Response response = this.service.getContext(authorizationTest,
                                                    fromAppId,
                                                    transactionId,
                                                    serviceInstanceId,
                                                    modelVersionId,
                                                    modelInvariantId);
        assertEquals(Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testNullXFromAppId() throws Exception {
        Response response = this.service.getContext(authorization,
                                                    null,
                                                    transactionId,
                                                    serviceInstanceId,
                                                    modelVersionId,
                                                    modelInvariantId);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(((String)response.getEntity()).contains("X-FromAppId"));
    }


    @Test
    public void testEmptyXFromAppId() throws Exception {
        Response response = this.service.getContext(authorization,
                                                    "",
                                                    transactionId,
                                                    serviceInstanceId,
                                                    modelVersionId,
                                                    modelInvariantId);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(((String)response.getEntity()).contains("X-FromAppId"));
    }


    @Test
    public void testNullModelVersionId() throws Exception {
        Response response = this.service.getContext(authorization,
                                                    fromAppId,
                                                    transactionId,
                                                    serviceInstanceId,
                                                    null,
                                                    modelInvariantId);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(((String)response.getEntity()).contains("modelVersionId"));
    }


    @Test
    public void testEmptyModelVersionId() throws Exception {
        Response response = this.service.getContext(authorization,
                                                    fromAppId,
                                                    transactionId,
                                                    serviceInstanceId,
                                                    "",
                                                    modelInvariantId);
        assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
        assertTrue(((String)response.getEntity()).contains("modelVersionId"));
    }


    @Test
    public void testRestAPISuccess() throws Exception {
        Response response = this.service.getContext(authorization,
                                                    fromAppId,
                                                    transactionId,
                                                    serviceInstanceId,
                                                    modelVersionId,
                                                    modelInvariantId);
        assertEquals(200, response.getStatus());

        Gson gson = new Gson();
        ModelContext modelCtx = gson.fromJson((String) response.getEntity(), ModelContext.class);


        // verify results
        assertEquals(serviceInstanceId, modelCtx.getService().getUuid());
        assertEquals(modelVersionId, modelCtx.getService().getModelVersionID());

        List<VNF> vnfList = modelCtx.getVnfs();
        assertEquals(vnfList.size(), 1);
        List<VFModule>  vfModuleList = vnfList.get(0).getVfModules();
        assertEquals(vfModuleList.size(), 1);
        assertEquals("8e363c35-60eb-45c0-9f14-2f3936f460c9",vfModuleList.get(0).getUuid() ); //vfModuleModelUUID in test data

    }


    @Test
    public void testSDCContextResponse() throws Exception {
        SDCContextRequest request = new SDCContextRequest(null, authorization, fromAppId, transactionId,
                serviceInstanceId, modelVersionId, modelInvariantId);
        SDCContextResponse sdcResponse = springService.getModelData(request);
        assertTrue(sdcResponse.getModelData().contains("service"));
        assertTrue(sdcResponse.getModelData().contains("vnfList"));
    }


}
