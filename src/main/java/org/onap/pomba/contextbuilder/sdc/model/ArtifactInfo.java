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

import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.openecomp.sdc.api.notification.IArtifactInfo;

@Data
@ToString(includeFieldNames = true)
public class ArtifactInfo implements IArtifactInfo {

    private String artifactName;
    private String artifactType;
    private String artifactDescription;
    private String artifactVersion;
    private String artifactURL;
    private String artifactChecksum;
    private Integer artifactTimeout;
    private String artifactUUID;
    private IArtifactInfo generatedArtifact;
    private List<IArtifactInfo> relatedArtifactsInfo;

    public IArtifactInfo getGeneratedArtifact() {
        return generatedArtifact;
    }

    public void setGeneratedArtifact(IArtifactInfo generatedArtifact) {
        this.generatedArtifact = generatedArtifact;
    }

    public List<IArtifactInfo> getRelatedArtifacts() {
        return relatedArtifactsInfo;
    }

    public void setRelatedArtifacts(List<IArtifactInfo> relatedArtifactsInfo) {
        this.relatedArtifactsInfo = relatedArtifactsInfo;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getArtifactDescription() {
        return artifactDescription;
    }

    public void setArtifactDescription(String artifactDescription) {
        this.artifactDescription = artifactDescription;
    }

    public String getArtifactVersion() {
        return artifactVersion;
    }

    public void setArtifactVersion(String artifactVersion) {
        this.artifactVersion = artifactVersion;
    }

    public String getArtifactURL() {
        return artifactURL;
    }

    public void setArtifactURL(String artifactURL) {
        this.artifactURL = artifactURL;
    }

    public String getArtifactChecksum() {
        return artifactChecksum;
    }

    public void setArtifactChecksum(String artifactChecksum) {
        this.artifactChecksum = artifactChecksum;
    }

    public Integer getArtifactTimeout() {
        return artifactTimeout;
    }

    public void setArtifactTimeout(Integer artifactTimeout) {
        this.artifactTimeout = artifactTimeout;
    }

    public String getArtifactUUID() {
        return artifactUUID;
    }

    public void setArtifactUUID(String artifactUUID) {
        this.artifactUUID = artifactUUID;
    }



}