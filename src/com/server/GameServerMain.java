package com.server;


/**
 * entry point
 * start webSocket server on that port
 */
public class GameServerMain {
    public static void main(String[] args)  {
        int port = Integer.valueOf(8888);
        WebSocketServer webSocketServer = new WebSocketServer();
        webSocketServer.start(port);
        System.out.println("server started");
    }

}
