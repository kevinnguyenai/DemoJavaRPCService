package JavaService.responses;

import JavaService.ResponseMessage;
import JavaService.GsonRpc;

public class Hello extends ResponseMessage{
    private String appName = GsonRpc.class.getSimpleName();
    private String version = GsonRpc.APP_VERSION;

}
