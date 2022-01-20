package JavaService;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;



/**
 * websocket server endpoint serve a bi-directional communication
 * with UI for demo RPC JSON (GSON) MSG 
 *
 */
@ServerEndpoint("/ws")
public class Socket {
    
    /*  Queue for all open websocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue();

    private static final Logger log = Logger.getLogger("RPCEndpoint");

    /* Called by Requestor */
    public static synchronized void send(String msg) {
        log.info("[RPCEndpoint] send()");
    }

    public static synchronized void sendAll(String msg) {
        log.info("[RPCEndpoint] sendAll()");
    }

    /* Called by Responder */
    public static synchronized void respond(String msg) {
        log.info("[RPCEndpoint] respond()");
    }

    /* Add and remove session from Queue */
    @OnOpen
    public void openConnection(Session session) {
        log.info("[RPCEndpoint] openConnection()");
        queue.add(session);
    }

    @OnClose
    public void closedConnection(Session session) {
        log.info("[RPCEndpoint] closedConnection()");
        queue.remove(session);
    }

    @OnError
    public void error(Session session, Throwable err) {
        queue.remove(session);
        log.info("[RPCEndpoint] error()");
    }

    /*
    public static void main( String[] args )
    {
        System.out.println( "RPC Server Start" );
    }
    */
}
