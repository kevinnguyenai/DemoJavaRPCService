package JavaService;

import JavaService.requests.GoodBye;
import JavaService.responses.Hello;
import JavaService.responses.Info;
import java.io.IOException;


/**
 * Our intent here is to create a simple RPC protocol that will be callable from 
 * a External JS script as a child process that communicates via standard IO Streams.
 * This will be done by the js script sending in a JSON encoded request object
 * that gets converted into a corresponding RequestMessage object. The RequestMessage
 * will process the request and then return any number of ResponseMessage objects,
 * as necessary to fulfill the request. This java program make use of Google's GSON library
 * to convert between JSON object and native Java objects.
 */
public class GsonRpc {
    public static final String APP_VERSION = "0.1";

    public static void main(String[] args) throws IOException {
        GsonRpc instance = new GsonRpc();

        instance.run();
    }

    private void run() throws IOException {
        // When started, we automatically send out a Hello message
        (new Hello()).send();
        (new Info("received OK")).send();


        // Now pump message until we receive a Goodbye
        RequestMessage message;
        while ((message = RequestMessage.fetchRequest()) != null ) {
            if (message instanceof GoodBye) {
                return;
            }
            message.process();
        }
    }
}