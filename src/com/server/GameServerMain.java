package com.server;


/**
 * entry point
 * read in configuration file and set port and other things
 * start webSocket server on that port
 */
public class GameServerMain {
    public static void main(String[] args)  {
        int port = Integer.valueOf(8888);
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start(port);
//        LOG.info("server started");
        System.out.println("server started");
    }

}
