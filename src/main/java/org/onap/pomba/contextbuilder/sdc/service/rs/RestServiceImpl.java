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
package org.onap.pomba.contextbuilder.sdc.service.rs;


import java.util.UUID;
import javax.ws.rs.core.Response;

import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextRequest;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextResponse;
import org.onap.pomba.contextbuilder.sdc.service.SpringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestServiceImpl implements RestService {
     private static Logger log = LoggerFactory.getLogger(RestService.class);

    @Autowired
    private SpringService service;

    public RestServiceImpl() {

    }

    @Override
    public Response getContext(String authorization, String xFromAppId, String xTransactionId, String serviceInstanceId,
            String modelVersionId, String modelInvariantId) throws ToscaCsarException {

        if (xTransactionId == null || xTransactionId.isEmpty()) {
            xTransactionId = UUID.randomUUID().toString();
            log.debug("X-TransactionId is missing; using newly generated value: " + xTransactionId);
        }
        SDCContextRequest sdcContext = new SDCContextRequest(null, authorization, xFromAppId, xTransactionId,
                serviceInstanceId, modelVersionId, modelInvariantId);
        SDCContextResponse sdcContextResponse = service.getModelData(sdcContext);
        return sdcContextResponse.buildResponse();
    }

}
