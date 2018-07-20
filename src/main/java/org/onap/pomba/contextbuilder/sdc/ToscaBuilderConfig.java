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
package org.onap.pomba.contextbuilder.sdc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ToscaBuilderConfig {

    @Autowired
    @Value("${sdc.artifact.type}")
    private String artifactType;

    @Autowired
    @Value("${sdc.url.template}")
    private String urlTemplate;

    @Autowired
    @Value("${sdc.csar.file.prefix}")
    private String csarFilePrefix;

    @Autowired
    @Value("${sdc.csar.file.suffix}")
    private String csarFileSuffix;

    @Autowired(required=false)
    @Value("${test.tosca.csar.file:}")
    private String testToscaCsarFile;


    @Bean(name="config")
    public ToscaBuilderConfig config() {
        return this;
    }


    public String getArtifactType() {
        return artifactType;
    }


    public String getUrlTemplate() {
        return urlTemplate;
    }


    public String getCsarFilePrefix() {
        return csarFilePrefix;
    }


    public String getCsarFileSuffix() {
        return csarFileSuffix;
    }


    public String getTestToscaCsarFile() {
        return testToscaCsarFile;
    }


    public void setTestToscaCsarFile(String testToscaCsarFile) {
        this.testToscaCsarFile = testToscaCsarFile;
    }

}
