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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;


@Api
@Path("/service")
@Produces({MediaType.APPLICATION_JSON})
public interface RestService {


    @GET
    @Path("/context")
    @Produces({ MediaType.APPLICATION_JSON })
    @ApiOperation(
            value = "Retrieve SDC CSAR file for a give UUID",
            notes = "This method returns SDC CSAR encoded value. ",
            response = Response.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Service not available"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Unexpected Runtime error") })
    public Response getContext(
            @HeaderParam("Authorization") String authorization,
            @HeaderParam("X-FromAppId") String xFromAppId,
            @HeaderParam("X-TransactionId") String xTransactionId,
            @QueryParam("serviceInstanceId") String serviceInstanceId,
            @QueryParam("modelVersionId") String modelVersionId,
            @QueryParam("modelInvariantId") String modelInvariantId
            ) throws ToscaCsarException;
}