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

import org.junit.Test;
import org.onap.pomba.contextbuilder.sdc.SDCContextConfig;


public class SDCContextConfigTest {

    @Test
    public void testSDCContextConfig() throws Exception {

        SDCContextConfig sdcContextConfig= new SDCContextConfig();

        sdcContextConfig.setUser("pomba");
        sdcContextConfig.setAsdcAddress("10.69.100.139");
        sdcContextConfig.setPassword("pomba");
        sdcContextConfig.setUseHttpsWithDmaap(false);
        sdcContextConfig.setConsumerId("pomba");
        sdcContextConfig.setActivateServerTLSAuth(false);
        sdcContextConfig.setPollingTimeout(Integer.parseInt("12000"));
        sdcContextConfig.setConsumerGroup("consumerGroup");
        sdcContextConfig.setEnvironmentName("environmentName");
        sdcContextConfig.setKeyStorePassword("aegwhfef234e3dd111cdcd");

        assertEquals("pomba", sdcContextConfig.getUser());
        assertEquals("10.69.100.139", sdcContextConfig.getAsdcAddress());
        assertEquals("pomba", sdcContextConfig.getPassword());
        assertEquals("pomba", sdcContextConfig.getConsumerID());
        assertEquals(12000, sdcContextConfig.getPollingTimeout());
        assertEquals(false, sdcContextConfig.activateServerTLSAuth());
        assertEquals(false, sdcContextConfig.isUseHttpsWithDmaap());
        assertEquals("consumerGroup", sdcContextConfig.getConsumerGroup());
        assertEquals("environmentName", sdcContextConfig.getEnvironmentName());
        assertEquals("aegwhfef234e3dd111cdcd", sdcContextConfig.getKeyStorePassword());


    }
}
