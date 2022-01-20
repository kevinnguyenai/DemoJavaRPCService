package JavaService.requests;

import JavaService.RequestMessage;
import JavaService.GsonRpc;
import JavaService.responses.Error;
import JavaService.responses.Info;
import JavaService.ResponseMessage;

/**
 * A friendly hello from the client. We'll either reject with an error message
 * or accept with an Info message response
 * 
 */
public class Hello  extends RequestMessage {
    private String version;

    @Override
    public void process() {
        ResponseMessage response;
        if(!GsonRpc.APP_VERSION.equals(version)) {
            response = new Error(new Exception("Client does not match expected version"+ GsonRpc.APP_VERSION));
        } else {
            response = new Info("Ready to receive requests.");
        }
        
        response.send();
    }
}
