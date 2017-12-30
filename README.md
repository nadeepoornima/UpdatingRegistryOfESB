# The sample code for Updating the registry of the ESB 

This is a sample code to update the registry of the ESB by triggering a task scheduler. 

When using this class, you can trigger this class to a task scheduler by giving its reference to "Task implementation" property.
eg : *org.wso2.carbon.esb.sample.services.TaskExecutor*

Furthermore, you need to give the registry path as the task property. When giving that, you need to give it as this way. This code reads the config registry. Then you need to give the path from that place. 

eg: **Exact path** : */_system/config/store/response.xsd*
    **Given path** : */store/response.xsd (start after that config registry)*

