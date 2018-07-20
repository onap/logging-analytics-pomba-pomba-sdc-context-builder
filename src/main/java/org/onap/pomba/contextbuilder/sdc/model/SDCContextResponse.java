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

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class SDCContextResponse {

    private String payload;
    private Status httpStatus = Status.OK;
    private String mediaType = MediaType.APPLICATION_JSON;

    /**
     * Instantiates with HTTP Status set to OK and MediaType set to APPLICATION-JSON
     */
    public SDCContextResponse() {
        // Intentionally empty
    }

    /**
     * This constructor is meant to be used when an error is encountered.
     * MediaType is set to TEXT-PLAIN for the error message.
     * @param httpStatus
     * @param errorMessage
     */
    public SDCContextResponse(Status httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.payload = errorMessage;
        this.mediaType = MediaType.TEXT_PLAIN;
    }

    public String getModelData() {
        return payload;
    }

    public void setModelData(String modelData) {
        this.payload = modelData;
    }

    public Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Status httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Response buildResponse() {
        return Response.status(httpStatus).entity(payload).type(mediaType).build();
    }

}
