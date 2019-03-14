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
package org.onap.pomba.contextbuilder.sdc.model.handlers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response.Status;

import org.onap.pomba.contextbuilder.sdc.ToscaBuilderConfig;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.onap.pomba.contextbuilder.sdc.model.ArtifactInfo;
import org.onap.pomba.contextbuilder.sdc.model.SDCContextResponse;
import org.onap.sdc.api.results.IDistributionClientDownloadResult;
import org.onap.sdc.http.SdcConnectorClient;
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.exceptions.SdcToscaParserException;
import org.onap.sdc.tosca.parser.impl.SdcToscaParserFactory;
import org.onap.sdc.utils.DistributionActionResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ToscaCsarArtifactHandler")
public class ToscaCsarArtifactHandler {

    private static final String DEFAULT_CSAR_FILE_EXTENSION = ".zip";

    private static Logger log = LoggerFactory.getLogger(ToscaCsarArtifactHandler.class);

    private Map<String, ISdcCsarHelper> sdcCsarHelpers = new HashMap<>();

    @Autowired
    private SdcConnectorClient client;
    @Autowired
    private ToscaBuilderConfig config;


    /**
     * Retrieves a subset of the TOSCA model for the given version ID.
     * If the model is not cached, a request to download the CSAR file is sent to SDC.
     * @param modelVersionId
     * @return Response containing a subset of the model data
     * @throws ToscaCsarException
     */
    public SDCContextResponse getToscaModel(String modelVersionId, String serviceInstanceId) throws ToscaCsarException {

        ISdcCsarHelper helper = sdcCsarHelpers.get(modelVersionId);
        if(helper == null) {
            log.debug("No CSAR file cached for " + modelVersionId);
            helper = retrieveToscaCsarArtifact(modelVersionId);
            if(helper != null) {
                sdcCsarHelpers.put(modelVersionId, helper);
            }
        }

        SDCContextResponse response = new SDCContextResponse();

        if(helper == null) {
            log.debug("CSAR artifact not found for model-version-id: " + modelVersionId);
            response.setModelData("CSAR artifact not found for model-version-id: " + modelVersionId);
            response.setHttpStatus(Status.NOT_FOUND);
            return response;
        }

        Gson gson = new GsonBuilder().create();
        String modelData = gson.toJson(ToscaModelConverter.convert(helper, modelVersionId, serviceInstanceId));
        response.setModelData(modelData);
        return response;
    }


    /**
     * Retrieves the CSAR file from SDC for the given model version ID
     * @param modelVersionId
     * @return Returns NULL if CSAR file couldn't be retrieved
     * @throws ToscaCsarException
     */
    private ISdcCsarHelper retrieveToscaCsarArtifact(String modelVersionId) throws ToscaCsarException {
        if (config.getTestToscaCsarFile() != null && !config.getTestToscaCsarFile().isEmpty()) {
            return getSdcToscaContext(config.getTestToscaCsarFile());
        }
        String url = generateURL(modelVersionId);
        log.debug("Downloading CSAR using URL suffix: " + url);

        ArtifactInfo artifact = new ArtifactInfo();
        artifact.setArtifactType(config.getArtifactType());
        artifact.setArtifactURL(url);
        artifact.setArtifactChecksum("value_not_used_by_sdc_distribution_client_lib");

        String csarFile = generateTemporaryFile(modelVersionId);
        try (OutputStream outputStream = Files.newOutputStream(Paths.get(csarFile), StandardOpenOption.CREATE);) {
            IDistributionClientDownloadResult downloadResult = client.dowloadArtifact(artifact);
            if (downloadResult.getDistributionActionResult() == DistributionActionResultEnum.ARTIFACT_NOT_FOUND) {
                log.debug("Failed to retrieve CSAR artifact from SDC: " + downloadResult.getDistributionMessageResult());
                return null;
            } else if (downloadResult.getDistributionActionResult() != DistributionActionResultEnum.SUCCESS) {
                throw new ToscaCsarException("Failed to retrieve CSAR artifact from SDC: " + downloadResult.getDistributionMessageResult());
            }

            byte[] payload = downloadResult.getArtifactPayload();
            if ((payload == null) || (payload.length == 0)) {
                log.debug("Retrieved CSAR payload is empty");
                return null;
            }
            outputStream.write(payload);
        } catch (IOException e){
            throw new ToscaCsarException("Failed to write temporary CSAR file '" + csarFile + "'", e);
        }

        ISdcCsarHelper helper = getSdcToscaContext(csarFile);
        try {
            Files.delete(Paths.get(csarFile));
        } catch (IOException e) {
            log.error("Failed to delete temporary CSAR file '" + csarFile + "': " + e.getMessage());
        }
        return helper;
    }


    private String generateURL(String modelVersionId) {
        return MessageFormat.format(config.getUrlTemplate(), modelVersionId);
    }


    /**
     * Creates a temporary file for downloaded CSAR file
     * @param modelVersionId
     * @return
     * @throws ToscaCsarException
     */
    private String generateTemporaryFile(String modelVersionId) throws ToscaCsarException {
        try {
            String fullPrefix = config.getCsarFilePrefix() + modelVersionId + "-";
            String suffix = (config.getCsarFileSuffix() == null ? DEFAULT_CSAR_FILE_EXTENSION : config.getCsarFileSuffix());
            Path downloadFile = Files.createTempFile(fullPrefix, suffix);
            log.debug("Temporary CSAR file name: " + downloadFile.toString());
            return downloadFile.toString();
        } catch (IOException e) {
            throw new ToscaCsarException("Failed to create temporary CSAR file: " + e.getMessage());
        }
    }


    /**
     * Parses a CSAR file
     * @param csarFile
     * @return
     * @throws ToscaCsarException
     */
    private ISdcCsarHelper getSdcToscaContext(String csarFile) throws ToscaCsarException {

        log.info("Loading and parsing SDC csar file ...");
        try {
            SdcToscaParserFactory factory = SdcToscaParserFactory.getInstance();
            return factory.getSdcCsarHelper(csarFile);
        } catch (SdcToscaParserException e) {
            throw new ToscaCsarException("Failed to parse CSAR file", e);
        }
    }

}
