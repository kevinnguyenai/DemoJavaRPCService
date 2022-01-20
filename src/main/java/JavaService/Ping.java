package JavaService;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This bean asynchronously automatically call WebSocket
 * Server endpoint then send Ping msg
 */
public class Ping {
    private static final Logger log = Logger.getLogger("Ping");

    public void ping(String msg) {
        try {
            log.info("[Ping] send Ping msg");
            Socket.sendAll(msg);
        } catch (Exception ex) {
            log.log(Level.INFO, "[Ping] Exception: {0}", ex.getMessage());
        } 
    }
}
