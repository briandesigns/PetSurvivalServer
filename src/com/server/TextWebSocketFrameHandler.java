package com.server;


import Stats.GameState;

import com.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Logger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * handles request from user
 * 2 kinds of user requests: login, or game event
 * if login, it will upgrade connection to tcp
 * otherwise, it will decode the request and hand it off to the game server
 */
//@Sharable 
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    //private static final AttributeKey<GameManager> GameManagerAttribute = AttributeKey.valueOf("gameManager");
    private final static Logger LOG = LoggerManager.GetLogger(GameServerMain.class.getName());
    private GameManager gameManager;
    private GameEventHandler gameEventHandler;

    public TextWebSocketFrameHandler(GameManager _gameManager, GameEventHandler _gameEventHandler) {
        this.gameManager = _gameManager;
        this.gameEventHandler = _gameEventHandler;

    }

    public TextWebSocketFrameHandler() {
        ;
    }

    /**
     * accept connnection and upgrade to tcp
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt == WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE) {

            ctx.pipeline().remove(HttpRequestHandler.class);
            JSONObject responseJson = new JSONObject();
            responseJson.put("event", GameState.HANDSHAKE_COMPLETE_SUCCESS);
            LOG.severe("HANDSHAKE_COMPLETE " + responseJson.toString());
            ctx.channel().writeAndFlush(new TextWebSocketFrame(responseJson.toString()));

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * send incoming client request to game event handler
     * then send a response back to client
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        msg.retain();
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        String jsonRequest = frame.text();
        LOG.severe("Recived from client :" + jsonRequest);
        try {
            int playerId = this.gameEventHandler.handleEvent(jsonRequest, ctx.channel());
            if (playerId != -1) {
                for (Player p : this.gameManager.getPlayerList()) {
                    if (p != null) {
                        @SuppressWarnings("rawtypes")
                        ArrayList<String> jsonList = p.getPlayerJsonList();
                        while (jsonList.size()>0) {
                            String jsonString = jsonList.remove(0);
                            responseToClient(p, jsonString);
                            LOG.severe("Sending to client id:" + String.valueOf(p.getId()) +
                                    " name:" + p.getUserName() + " json:" + jsonString);
                            int state = new JSONObject(jsonString).getInt("event");
                            int id = new JSONObject(jsonString).getInt("playerID");
                            if ((p.getId() == id) && (state == GameState.DEATH.ordinal())) {
                                this.gameManager.removePlayerByID(id);
                            }
                        }
                    }

                }
            } else {
                LOG.severe("Sending to clients Failed playerId is -1");
            }

        } finally {
            frame.release();
        }
    }

    private void responseToClient(Player _player, String responseJson) {
        _player.getChannel().writeAndFlush(new TextWebSocketFrame(responseJson));
    }
}
