package JavaService;

import com.google.gson.Gson;

/**
 * The Message class is a abstract base class to help with both request
 * and response message classes
 */
public abstract class Message {
    /**
     * The messageName is used to determine the type of request or response
     * It is a critical piece of information for the client and server to 
     * distringuish the messages they are receiving from each other.
     */
    private String messageName = super.getClass().getSimpleName();

    /**
     * The instance of Gson used for endcoding and decoding messages
     * between JSON and Message objects.
     * fields marked `transient` will not be serialized by gson
     */
    protected transient static Gson _gson = new Gson();
}