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

import java.util.ArrayList;
import java.util.List;
import org.onap.pomba.common.datatypes.ModelContext;
import org.onap.pomba.common.datatypes.Service;
import org.onap.pomba.common.datatypes.VF;
import org.onap.pomba.common.datatypes.VFModule;
import org.onap.pomba.common.datatypes.VNFC;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.openecomp.sdc.tosca.parser.api.ISdcCsarHelper;
import org.openecomp.sdc.tosca.parser.impl.SdcPropertyNames;
import org.openecomp.sdc.toscaparser.api.Group;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.toscaparser.api.elements.Metadata;

public class ToscaModelConverter {

    private static final String PROPERTY_NAME_NFC_NAMING_CODE = "nfc_naming_code";

    private ToscaModelConverter() {

    }

    /**
     * Conversion from SDC model to the common model
     * @param helper
     * @return
     * @throws ToscaCsarException
     */
    public static ModelContext convert(ISdcCsarHelper helper) throws ToscaCsarException {
        ModelContext context = new ModelContext();
        context.setService(generateService(helper.getServiceMetadata()));
        context.setVf(generateVfList(helper));
        return context;
    }

    /**
     * Instantiates a service based on the SDC Service metadata
     * @param serviceMetadata
     * @return
     * @throws ToscaCsarException
     */
    private static Service generateService(Metadata serviceMetadata) throws ToscaCsarException {
        Service service = new Service();
        if (serviceMetadata != null) {
            service.setName(serviceMetadata.getValue(SdcPropertyNames.PROPERTY_NAME_NAME));
            service.setInvariantUuid(serviceMetadata.getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            service.setUuid(serviceMetadata.getValue(SdcPropertyNames.PROPERTY_NAME_UUID));
        }
        return service;
    }

    /**
     * Generates a list of VFs from the SDC VF-Resources
     * @param helper
     * @return
     * @throws ToscaCsarException
     */
    private static List<VF> generateVfList(ISdcCsarHelper helper) throws ToscaCsarException {

        List<VF> vfList = new ArrayList<>();
        List<NodeTemplate> vfNodeTemplateList = helper.getServiceVfList();
        for (NodeTemplate vfNodeTemplate : vfNodeTemplateList) {
            VF vf = new VF();
            vf.setName(vfNodeTemplate.getName());
            vf.setType(vfNodeTemplate.getType());
            vf.setInvariantUuid(vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            vf.setUuid(vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_UUID));

            String customizationUUID = vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_CUSTOMIZATIONUUID);
            vf.setVnfc(generateVnfcList(helper.getVfcListByVf(customizationUUID)));
            vf.setVfModules(generateVfModuleList(helper.getVfModulesByVf(customizationUUID)));

            vfList.add(vf);
        }
        return vfList;
    }

    /**
     * Generates a list of VFNCs from the SDC VF-Resources's VNFCs
     * @param vfcNodeTemplateList
     * @return
     * @throws ToscaCsarException
     */
    private static List<VNFC> generateVnfcList(List<NodeTemplate> vfcNodeTemplateList) throws ToscaCsarException {
        List<VNFC> vnfcList = new ArrayList<>();
        for (NodeTemplate vfcNodeTemplate : vfcNodeTemplateList) {
            VNFC vnfc = new VNFC();
            vnfc.setName(vfcNodeTemplate.getName());
            vnfc.setInvariantUuid(vfcNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            vnfc.setUuid(vfcNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_UUID));

            Object nfcNamingCode = vfcNodeTemplate.getPropertyValue(PROPERTY_NAME_NFC_NAMING_CODE);
            if (nfcNamingCode != null) {
                vnfc.setNfcNamingCode((String)nfcNamingCode);
            }

            vnfcList.add(vnfc);
        }
        return vnfcList;
    }

    /**
     * Generates a list of VFModules from the SDC VF-Resource's VF-Modules
     * @param vfModuleGroupList
     * @return
     * @throws ToscaCsarException
     */
    private static List<VFModule> generateVfModuleList(List<Group> vfModuleGroupList) throws ToscaCsarException {
        List<VFModule> vfModuleList = new ArrayList<>();
        for (Group moduleGroup : vfModuleGroupList) {
            VFModule vfModule = new VFModule();
            vfModule.setInvariantUuid(moduleGroup.getMetadata().getValue(SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELINVARIANTUUID));
            vfModule.setUuid(moduleGroup.getMetadata().getValue(SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELUUID));

            Object minInstances = moduleGroup.getPropertyValue(SdcPropertyNames.PROPERTY_NAME_MINVFMODULEINSTANCES);
            if (minInstances != null) {
                vfModule.setMinInstances((Integer)minInstances);
            }
            Object maxInstances = moduleGroup.getPropertyValue(SdcPropertyNames.PROPERTY_NAME_MINVFMODULEINSTANCES);
            if (maxInstances != null) {
                vfModule.setMaxInstances((Integer)maxInstances);
            }

            vfModuleList.add(vfModule);
        }
        return vfModuleList;
    }
}
