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

import java.util.List;
import org.openecomp.sdc.api.consumer.IConfiguration;

public class SDCContextConfig implements IConfiguration {

    private String sdcAddress;
    private String consumerID;
    private String user;
    private  int pollingTimeout;
    private String password;
    private String environmentName;
    private String keyStorePassword;
    private List<String> relevantArtifactTypes = null;
    private int pollingInterval;
    private String consumerGroup;
    private String keyStorePath;
    private boolean activateServerTLSAuth;
    private boolean isFilterInEmptyResources;
    private Boolean useHttpsWithDmaap;
    private List<String> msgBusAddress;


    @Override
    public List<String> getMsgBusAddress() {
        return msgBusAddress;
    }

    public void setMsgBusAddress(List<String> msgBusAddress) {
        this.msgBusAddress = msgBusAddress;
    }

    @Override

    public String getAsdcAddress() {
        return sdcAddress;
    }

    public void setAsdcAddress(String sdcAddress) {
        this.sdcAddress = sdcAddress;
    }

    public String getConsumerID() {
        return consumerID;
    }

    public void setConsumerId(String consumerId) {
        this.consumerID = consumerId;
    }

    @Override
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public int getPollingTimeout() {
        return pollingTimeout;
    }

    public void setPollingTimeout(int pollingTimeout) {
        this.pollingTimeout = pollingTimeout;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    @Override
    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    @Override
    public List<String> getRelevantArtifactTypes() {
        return relevantArtifactTypes;
    }

    public void setRelevantArtifactTypes(List<String> relevantArtifactTypes) {
        this.relevantArtifactTypes = relevantArtifactTypes;
    }

    @Override
    public int getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    @Override
    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    @Override
    public String getKeyStorePath() {
        return keyStorePath;
    }

    public void setKeyStorePath(String keyStorePath) {
        this.keyStorePath = keyStorePath;
    }

    public boolean activateServerTLSAuth() {
        return activateServerTLSAuth;
    }

    public void setActivateServerTLSAuth(boolean activateServerTLSAuth) {
        this.activateServerTLSAuth = activateServerTLSAuth;
    }

    @Override
    public boolean isFilterInEmptyResources() {
        return isFilterInEmptyResources;
    }

    public void setFilterInEmptyResources(boolean filterInEmptyResources) {
        isFilterInEmptyResources = filterInEmptyResources;
    }

    @Override
    public Boolean isUseHttpsWithDmaap() {
        return useHttpsWithDmaap;
    }

    public void setUseHttpsWithDmaap(boolean useHttpsWithDmaap) {
        this.useHttpsWithDmaap = useHttpsWithDmaap;
    }
}
