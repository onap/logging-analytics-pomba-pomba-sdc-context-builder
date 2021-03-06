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
import org.onap.pomba.common.datatypes.VFModule;
import org.onap.pomba.common.datatypes.VNF;
import org.onap.pomba.common.datatypes.VNFC;
import org.onap.pomba.contextbuilder.sdc.exception.ToscaCsarException;
import org.onap.sdc.tosca.parser.api.ISdcCsarHelper;
import org.onap.sdc.tosca.parser.impl.SdcPropertyNames;
import org.onap.sdc.toscaparser.api.Group;
import org.onap.sdc.toscaparser.api.NodeTemplate;
import org.onap.sdc.toscaparser.api.elements.Metadata;

public class ToscaModelConverter {

    private ToscaModelConverter() {

    }

    /**
     * Conversion from SDC model to the common model
     * @param helper
     * @return
     * @throws ToscaCsarException
     */
    public static ModelContext convert(ISdcCsarHelper helper,String modelVersionId, String serviceInstanceId) throws ToscaCsarException {
        ModelContext context = new ModelContext();
        context.setService(generateService(helper.getServiceMetadata(), serviceInstanceId));
        context.setVnfs(generateVnfList(helper));
        return context;
    }

    /**
     * Instantiates a service based on the SDC Service metadata
     * @param serviceMetadata
     * @return
     * @throws ToscaCsarException
     */
    private static Service generateService(Metadata serviceMetadata, String serviceInstanceId) throws ToscaCsarException {
        Service service = new Service();
        if (serviceMetadata != null) {
            service.setModelInvariantUUID(serviceMetadata.getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            service.setModelVersionID(serviceMetadata.getValue(SdcPropertyNames.PROPERTY_NAME_UUID)); //assign sdc UUID to ModelVersionID based on common model mapping
            service.setUuid(serviceInstanceId);
        }
        return service;
    }

    /**
     * Generates a list of VFs from the SDC VF-Resources
     * @param helper
     * @return
     * @throws ToscaCsarException
     */
    private static List<VNF> generateVnfList(ISdcCsarHelper helper) throws ToscaCsarException {

        List<VNF> vnfList = new ArrayList<>();
        List<NodeTemplate> vfNodeTemplateList = helper.getServiceVfList();
        for (NodeTemplate vfNodeTemplate : vfNodeTemplateList) {
            VNF vnf = new VNF();
            vnf.setType(vfNodeTemplate.getType());
            vnf.setModelInvariantUUID(vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            vnf.setModelVersionID(vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_UUID)); //assign sdc UUID to ModelVersionID based on common model mapping

            String customizationUUID = vfNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_CUSTOMIZATIONUUID);
            vnf.setVnfcs(generateVnfcList(helper.getVfcListByVf(customizationUUID)));
            vnf.setVfModules(generateVfModuleList(helper.getVfModulesByVf(customizationUUID)));

            vnfList.add(vnf);
        }
        return vnfList;
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
            vnfc.setModelInvariantUUID(vfcNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_INVARIANTUUID));
            vnfc.setModelVersionID(vfcNodeTemplate.getMetaData().getValue(SdcPropertyNames.PROPERTY_NAME_UUID));   //assign sdc UUID to ModelVersionID based on common model mapping
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
            vfModule.setModelInvariantUUID(moduleGroup.getMetadata().getValue(SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELINVARIANTUUID));
            vfModule.setModelVersionID(moduleGroup.getMetadata().getValue(SdcPropertyNames.PROPERTY_NAME_VFMODULEMODELUUID));
            vfModule.setModelCustomizationUUID(moduleGroup.getMetadata().getValue(SdcPropertyNames.PROPERTY_NAME_VFMODULECUSTOMIZATIONUUID));


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
