package JavaService;

import JavaService.requests.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * The RequestMessage class deal with receiving and processing requests from 
 * the client. It receives request as JSON objects over standard input. The
 * requests are then converted into request Message objects via gson.
 * It is also a base class for the individual request message classes.
 * Each request object will process itself , util a Goodbye request is received
 * The Goodbye request will close down the java program by causing the run
 * method return
 */
public abstract class RequestMessage extends Message {
    /**
     * An internal wrapper around the standard input stream used for receiving requests.
     * It can be changed via the setInputStream method.
     * Chhanging it is useful for Junit test cases that need to send data
     * in for propressing as part of a test
     */
    private static DataInputStream _inputStream = new DataInputStream(System.in);

    /**
     * This HashMap is used to help map from a name on a received JSON
     * request back to the class that GSON should encode it to. Any new
     * request message types should be added to this set. Some response
     * message types my also be added for the benefit of Junit unit tests.
     */
    private final static HashMap<String, Type> _requestMessages;

    // Initialize static data
    static {
        // Build map for processing requests and generating message classes from them
        _requestMessages = new HashMap();
        _requestMessages.put(GoodBye.class.getSimpleName(), GoodBye.class);
        _requestMessages.put(Hello.class.getSimpleName(), Hello.class);
        _requestMessages.put(GoodBye.class.getSimpleName(), GoodBye.class);
    }
    
    /**
     * The process method is only used by request objects. They must
     * override this method with an implementation that handles whatever
     * processing is necessary for that particular message type.
     * @return true if successful processed , otherwise false.
     */
    public abstract void process();

    /**
     * Overrides the input stream by the fetchRequest method as the source
     * of JSON input for requests. This is helpful for unit tests
     * and would be useful if we decide to use other transport machanisms.
     * @param dis The DataInputStream where requests will be retreived from.
     */
    public static void setInputStream(DataInputStream dis) {
        RequestMessage._inputStream = dis;
    }

    /**
     * Retreives a message from the input stream and converts it into a RequestMessage object.
     */
    public static RequestMessage fetchRequest() throws IOException {
        // the message is retrieved as two parts:
        // - A 4-byte (32-bit) integer that indicates the byte-length of the following JSON string
        // - A n-byte string of text in a JSON format
        // the code below retreives this length and then initializes a buffer json bytes
        int     jsonLength  = _inputStream.readInt();
        byte[]  bytes       = new byte[jsonLength];
        int     bytesRead   = 0;
        
        // Retreive all of the bytes. Incase we receive it in pieces, this is 
        // written as a loop to keep reading bytes untils all are received.
        while(bytesRead < jsonLength) {
            bytesRead += _inputStream.read(bytes, bytesRead, jsonLength - bytesRead);
        }

        return jsonToRequestMessage(new String(bytes));
    }

    /**
     * Use gson to deserialize a JSON message into a RequestMessage object.
     * @param jsonString A String containing a JSON formatted message
     * @return A ReuqestMessage object if successful , otherwise null
     */
    public static RequestMessage jsonToRequestMessage(String jsonString) {
        return (RequestMessage)jsonToMessage(jsonString, _requestMessages);
    }

    
    public static Message jsonToMessage(String jsonString, HashMap<String, Type> messageMap) {
        // Parse the json string to a json object
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(jsonString);
        JsonObject object = element.getAsJsonObject();

        // Get the messageName from the json object
        String messageName = object.get("messageName").getAsString();

        // Convert the name into a class type for gson to use
        Type messageType = messageMap.get(messageName);

        // If the type name isn't listed then we get a null and can't convert
        if(null == messageType) {
            return null;
        }

        // Convert to a message object and return it
        return _gson.fromJson(element, messageType);
    }
;}