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

import org.onap.pomba.contextbuilder.sdc.SDCClientConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SDCClientConfigurationTest {

    SDCClientConfiguration sdcClientConfiguration = mock(SDCClientConfiguration.class);

    @Test
    public void testSDCClientConfig() throws Exception {
        SDCClientConfiguration config = new SDCClientConfiguration();
        config.setConsumerID("consumerID");
        config.setPassword("password");
        config.setSdcAddress("10.147.58.6:30204");
        config.setUsername("username");
        config.setPollingTimeout("6666");


        assertEquals(config.getUsername().toString(), "username");
        assertEquals(config.getConsumerID().toString(), "consumerID");
        assertEquals(config.getPassword().toString(), "password");
        assertEquals(config.getPollingTimeout().toString(), "6666");
        assertEquals(config.getSdcAddress().toString(), "10.147.58.6:30204");
    }


    @Test
    public void testGetHttpBasicAuth() {
        String msg = "Basic YWRtaW46YWRtaW4=";
        when(sdcClientConfiguration.getHttpBasicAuth()).thenReturn(msg);
        assertEquals(sdcClientConfiguration.getHttpBasicAuth().toString(), "Basic YWRtaW46YWRtaW4=");

    }
}
