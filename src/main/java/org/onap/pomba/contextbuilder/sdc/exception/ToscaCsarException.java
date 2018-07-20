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
package org.onap.pomba.contextbuilder.sdc.exception;

import javax.ws.rs.core.Response.Status;

public class ToscaCsarException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String errorMessage;
    private final Status httpStatus;

    /**
     * Constructor for an instance of this exception with just a message.
     * HTTP Status set to INTERNAL-SERVER-ERROR
     * @param message information about the exception
     */
    public ToscaCsarException(String message) {
        super(message);
        errorMessage = message;
        httpStatus = Status.INTERNAL_SERVER_ERROR;
    }

    /**
     * Constructor for an instance of this exception with a message and status.
     * @param httpStatus
     * @param message
     */
    public ToscaCsarException(Status httpStatus, String message) {
        super(message);
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructor for an instance of this exception with a message and actual exception encountered.
     * HTTP Status set to INTERNAL-SERVER-ERROR
     * @param message information about the exception
     * @param cause the actual exception that was encountered
     */
    public ToscaCsarException(String message, Throwable cause) {
        super(message, cause);
        errorMessage = message;
        httpStatus = Status.INTERNAL_SERVER_ERROR;
    }

    /**
     * Constructor for an instance of this exception with a message, status and actual exception encountered.
     * @param httpStatus
     * @param message
     * @param cause
     */
    public ToscaCsarException(Status httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.httpStatus = httpStatus;
    }

    public Status getStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        String fullMessage = errorMessage;
        if(getCause() != null) {
            fullMessage += ": " + getCause().getMessage();
        }
        return fullMessage;
    }
}
