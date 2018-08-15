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

import org.eclipse.jetty.util.security.Password;
import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.SDCClientConfiguration;
import org.openecomp.sdc.api.consumer.IConfiguration;
import org.openecomp.sdc.http.SdcConnectorClient;

public class SDCClientConfigurationTest {

    @Test
    public void testSDCClientConfig() throws Exception {
        SDCClientConfiguration config = new SDCClientConfiguration();
        config.setUsername("pomba");
        config.setSdcAddress("10.147.58.6:30204");
        config.setPassword("OBF:1uha1uh81w8v1ugi1ugg");
        config.setConsumerID("consumerID");
        config.setPollingTimeout("6666");

        assertEquals("pomba", config.getUsername());
        assertEquals("10.147.58.6:30204", config.getSdcAddress());
        assertEquals("consumerID", config.getConsumerID());
        assertEquals("pomba", Password.deobfuscate(config.getPassword()));
        assertEquals("6666", config.getPollingTimeout());

        SdcConnectorClient sdcClient = config.asdcConnectorClient();
        IConfiguration sdcClientConfig = sdcClient.getConfiguration();
        assertEquals("pomba", sdcClientConfig.getUser());
        assertEquals("10.147.58.6:30204", sdcClientConfig.getAsdcAddress());
        assertEquals("consumerID", sdcClientConfig.getConsumerID());
        assertEquals("pomba", sdcClientConfig.getPassword());
        assertEquals(6666, sdcClientConfig.getPollingTimeout());

    }


    @Test
    public void testGetHttpBasicAuth() {
        String msg = "Basic YWRtaW46YWRtaW4=";
        SDCClientConfiguration config = new SDCClientConfiguration();
        config.setHttpUserId("admin");
        config.setHttpPassword("OBF:1u2a1toa1w8v1tok1u30");
        assertEquals(msg, config.getHttpBasicAuth());
    }
}
