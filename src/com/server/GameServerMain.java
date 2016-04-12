package com.server;

import java.util.ResourceBundle;
import java.util.logging.Logger;
//DONE
/**
 * entry point
 * read in configuration file and set port and other things
 * start webSocket server on that port
 */
public class GameServerMain {
    private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());

    public static void main(String[] args) {
        final ResourceBundle configurationBundle = ResourceBundle.getBundle("configuration");
        int port = Integer.valueOf(configurationBundle.getString("port"));
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start(port);
        LOG.info("server started");
    }

}
