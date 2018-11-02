/*
 * ============LICENSE_START===================================================
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
package org.onap.pomba.contextbuilder.sdc;

import java.util.Base64;
import lombok.Data;
import org.eclipse.jetty.util.security.Password;
import org.onap.sdc.http.SdcConnectorClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SDCClientConfiguration {

    @Autowired
    @Value("${sdcConnect.username}")
    private String username;
    @Autowired
    @Value("${sdcConnect.sdcAddress}")
    private String sdcAddress;
    @Autowired
    @Value("${sdcConnect.password}")
    private String password;
    @Autowired
    @Value("${sdcConnect.consumerID}")
    private String consumerID;
    @Autowired
    @Value("${sdcConnect.timeout.seconds}")
    private String pollingTimeout;

    @Autowired
    @Value("${http.userId}")
    private String httpUserId;

    @Autowired
    @Value("${http.password}")
    private String httpPassword;


    @Bean(name="httpBasicAuthorization")
    public String getHttpBasicAuth() {
        String auth = new String(this.httpUserId + ":" + Password.deobfuscate(this.httpPassword));

        String encodedAuth =  Base64.getEncoder().encodeToString(auth.getBytes());
        return ("Basic " + encodedAuth);
    }


    @Bean(name="client")
    public SdcConnectorClient asdcConnectorClient(){
        SdcConnectorClient client = new SdcConnectorClient();
        SDCContextConfig sdcContextConfig = new SDCContextConfig();
        sdcContextConfig.setUser(username);
        sdcContextConfig.setAsdcAddress(sdcAddress);
        sdcContextConfig.setPassword(Password.deobfuscate(password));
        sdcContextConfig.setUseHttpsWithDmaap(false);
        sdcContextConfig.setConsumerId(consumerID);
        sdcContextConfig.setActivateServerTLSAuth(false);
        sdcContextConfig.setPollingTimeout(Integer.parseInt(pollingTimeout));

        org.onap.sdc.impl.Configuration config = new org.onap.sdc.impl.Configuration(sdcContextConfig);
        client.init(config);

        return client;
    }
}
