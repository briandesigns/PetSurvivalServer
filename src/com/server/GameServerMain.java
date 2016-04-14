package com.server;

import com.oracle.webservices.internal.api.message.PropertySet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.logging.Logger;
//DONE
/**
 * entry point
 * read in configuration file and set port and other things
 * start webSocket server on that port
 */
public class GameServerMain {
//    private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());

    public static void main(String[] args)  {
//        PropertyResourceBundle resourceBundle = null;
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("configuration.properties");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            resourceBundle = new PropertyResourceBundle(fis);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        final ResourceBundle configurationBundle = ResourceBundle.getBundle("configuration");
        int port = Integer.valueOf(8888);
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start(port);
//        LOG.info("server started");
        System.out.println("server started");
    }

}
