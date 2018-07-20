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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.onap.pomba.contextbuilder.sdc.service.rs.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
@WebAppConfiguration
@SpringBootTest
@TestPropertySource(properties = {"sdcConnect.sdcAddress=localhost:30204"})
public class SDCContextRestAPITest {

    static {
        System.setProperty("test.tosca.csar.file", "");
    }

    @Autowired
    private RestService service;


    private String authorization = "Basic " + Base64.getEncoder().encodeToString(("admin" + ":" + "admin").getBytes(StandardCharsets.UTF_8));
    private String fromAppId = "POMBA";
    private String transactionId = UUID.randomUUID().toString();
    private String serviceInstanceId = "b06270ab-99e6-4a58-9bc0-db2df5c36f4d";
    private String modelVersionId= "e9851a43-c068-4eb2-9fe7-2d123bd94ff0";
    private String modelInvariantId = "4fd21763-23ed-4f69-8654-e121626df327" ;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /**
     * Call to SDC to download the Tosca Csar file
     *
     */
    @Test
    public void testVerifyAPI() throws Exception {
        //TODO we need mock the localhost as SDC to return success. For now, assume it failed to reach the SDC
        try {
            this.service.getContext(authorization, fromAppId, transactionId, serviceInstanceId, modelVersionId, modelInvariantId);
        }catch (ToscaCsarException x) {
            fail("Failed to retrieve CSAR artifact from SDC");
        }

    }


}
