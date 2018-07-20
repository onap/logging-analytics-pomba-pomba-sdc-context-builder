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
package org.onap.pomba.contextbuilder.sdc.model;

import javax.ws.rs.core.Response.Status;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;

public class SDCContextRequest {

    private String remoteAddr;
    private String fromAppId;
    private String transactionId;
    private String serviceInstanceId;
    private String modelVersionId;
    private String modelInvariantId;
    private String authorization;


    /**
     * @param fromAppId
     * @param transactionId
     * @param serviceInstanceId
     * @param modelVersionId
     * @param modelInvariantId
     */
    public SDCContextRequest(String remoteAddr, String authorization, String fromAppId, String transactionId,
            String serviceInstanceId, String modelVersionId, String modelInvariantId) {
        this.remoteAddr = remoteAddr;
        this.authorization = authorization;
        this.fromAppId = fromAppId;
        this.transactionId = transactionId;
        this.serviceInstanceId = serviceInstanceId;
        this.modelVersionId = modelVersionId;
        this.modelInvariantId = modelInvariantId;

    }


    public String getAuthorization() {
        return authorization;
    }


    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }


    public String getRemoteAddr() {
        return remoteAddr;
    }

    public String getFromAppId() {
        return fromAppId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public String getModelVersionId() {
        return modelVersionId;
    }

    public String getModelInvariantId() {
        return modelInvariantId;
    }

    public void validate(String httpBasicAuthorization) throws ToscaCsarException {

        // validation on HTTP Authorization Header
        if (authorization != null && !authorization.trim().isEmpty() && authorization.startsWith("Basic")) {
            if (!authorization.equals(httpBasicAuthorization)) {
                throw new ToscaCsarException(Status.BAD_REQUEST, "Authorization Failed due to mismatch basic auth username or password");
            };
        } else {
            throw new ToscaCsarException(Status.BAD_REQUEST, "Missing Authorization header");
        }

        if((fromAppId == null) || fromAppId.isEmpty()) {
            throw new ToscaCsarException(Status.BAD_REQUEST, "Missing mandatory header parameter: X-FromAppId");
        }
        if((transactionId == null) || transactionId.isEmpty()) {
            throw new ToscaCsarException(Status.BAD_REQUEST, "Missing mandatory header parameter: X-TransactionId");
        }
        if((modelVersionId == null) || modelVersionId.isEmpty()) {
            throw new ToscaCsarException(Status.BAD_REQUEST, "Missing mandatory parameter: modelVersionId");
        }
    }

    @Override
    public String toString() {
        return "SDCContextRequest [remoteAddr=" + remoteAddr + ", authorization=" + authorization + ", fromAppId=" + fromAppId + ", transactionId="
                + transactionId + ", serviceInstanceId=" + serviceInstanceId + ", modelVersionId=" + modelVersionId
                + ", modelInvariantId=" + modelInvariantId + "]";
    }
}
