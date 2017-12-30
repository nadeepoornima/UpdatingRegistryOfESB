package org.wso2.carbon.esb.sample.services;

import org.apache.synapse.task.Task;
import org.wso2.carbon.context.PrivilegedCarbonContext;
import org.wso2.carbon.context.RegistryType;
import org.wso2.carbon.registry.core.Resource;
import org.wso2.carbon.registry.core.exceptions.RegistryException;
import org.wso2.carbon.registry.core.service.RegistryService;

import org.wso2.carbon.registry.core.Registry;
import org.wso2.carbon.registry.core.session.UserRegistry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class TaskExecutor implements Task {

    private static RegistryService registryService;
    private String registryPath;
    private Registry governanceRegistry;

    @Override
    public void execute() {

        try {
            PrivilegedCarbonContext.startTenantFlow();
            PrivilegedCarbonContext privilegedCarbonContext = PrivilegedCarbonContext.getThreadLocalCarbonContext();
            privilegedCarbonContext.setTenantId(-1234);
            privilegedCarbonContext.setTenantDomain("carbon.super");
            privilegedCarbonContext.setUsername("admin");
            //use the config registry of the esb
            UserRegistry confReg = (UserRegistry) privilegedCarbonContext.getRegistry(RegistryType.SYSTEM_CONFIGURATION);
            //get registry resources using the path which we given at the task sheduler
            Resource resource = confReg.get(getPath());
            //convert the content from bytes to string
            String str = new String((byte[])(resource.getContent()));
            System.out.println(str);

            //Updating the Registry
            //convert the registry contenet to bytes
            InputStream contentStream = new ByteArrayInputStream("helloWorld".getBytes());
            //set the content to the given registry
            resource.setContentStream(contentStream);
            confReg.put(getPath(), resource);

        } catch (RegistryException e) {
            e.printStackTrace();
        } finally {
            PrivilegedCarbonContext.endTenantFlow();

        }

    }

    public String getPath() {
        return registryPath;
    }

    public void setRegistryPath(String registryPath) {
        this.registryPath = registryPath;
    }

}
