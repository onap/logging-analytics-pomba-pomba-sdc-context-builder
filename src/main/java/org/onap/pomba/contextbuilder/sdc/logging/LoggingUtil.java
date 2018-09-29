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
package org.onap.pomba.contextbuilder.sdc.logging;

import org.onap.aai.cl.mdc.MdcContext;
import org.slf4j.MDC;

public class LoggingUtil {

    private static final String APP_NAME = "Pomba SDC ContextBuilder";

    private LoggingUtil() {

    }

    /**
     * Initializes MDC context.
     * Called when request processing begins.
     * @param httpReq
     * @param headers
     */
    public static void initMdc(String transactionId, String fromAppId, String remoteAddr) {
        MdcContext.initialize(transactionId, APP_NAME, "", fromAppId, remoteAddr);
    }

    /**
     * Clears the MDC context.
     * Called when request processing ends.
     */
    public static void closeMdc() {
        MDC.clear();
    }
}