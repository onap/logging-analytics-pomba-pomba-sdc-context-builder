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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;


@Path("{version: v2}/service")
@Produces({MediaType.APPLICATION_JSON})
public interface RestService {


    @GET
    @Path("/context")
    @Produces({ MediaType.APPLICATION_JSON })
    @Operation(
            summary = "Retrieve SDC CSAR file for a give UUID",
            description = "This method returns SDC CSAR encoded value. ",
            responses = {
                @ApiResponse(responseCode = "404", description = "Service not available"),
                @ApiResponse(responseCode = "400", description = "Bad request"),
                @ApiResponse(responseCode = "401", description = "Unauthorized"),
                @ApiResponse(responseCode = "500", description = "Unexpected Runtime error") })
    public Response getContext(
            @HeaderParam("Authorization") String authorization,
            @HeaderParam("X-FromAppId") String xFromAppId,
            @HeaderParam("X-TransactionId") String xTransactionId,
            @QueryParam("serviceInstanceId") String serviceInstanceId,
            @QueryParam("modelVersionId") String modelVersionId,
            @QueryParam("modelInvariantId") String modelInvariantId
            ) throws ToscaCsarException;
}