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
package org.onap.pomba.contextbuilder.sdc.service;

import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.onap.pomba.contextbuilder.sdc.logging.LoggingUtil;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextRequest;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextResponse;
import org.onap.pomba.contextbuilder.sdc.model.handlers.ToscaCsarArtifactHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpringServiceImpl implements SpringService {

    private static Logger log = LoggerFactory.getLogger(SpringService.class);


    @Autowired
    protected ToscaCsarArtifactHandler toscaCsarArtifactHandler;

    @Autowired
    private String httpBasicAuthorization;

    @Override
    public SDCContextResponse getModelData(SDCContextRequest ctxRequest) throws ToscaCsarException {

        LoggingUtil.initMdc(ctxRequest.getTransactionId(), ctxRequest.getFromAppId(), ctxRequest.getRemoteAddr());
        SDCContextResponse sdcContextResponse = null;
        try {
            ctxRequest.validate(httpBasicAuthorization);
            sdcContextResponse = toscaCsarArtifactHandler.getToscaModel(ctxRequest.getModelVersionId(), ctxRequest.getServiceInstanceId());
        } catch (ToscaCsarException e) {
            log.error(e.getStatus().getStatusCode() + " " + e.getStatus()+ ", Reason: " + e.getMessage());
            sdcContextResponse = new SDCContextResponse(e.getStatus(), e.getMessage());
        } finally {
            LoggingUtil.closeMdc();
        }
        return sdcContextResponse;

    }


}