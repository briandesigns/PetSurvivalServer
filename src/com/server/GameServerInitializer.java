package com.server;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

//DONE
/**
 * game server channel initializer
 */
public class GameServerInitializer extends ChannelInitializer<Channel> {
	private final GameManager gameManager; 
	
    public GameServerInitializer(GameManager gameManager) {
    	this.gameManager = gameManager;
    }

    /**
     * setup a pipeline channel
     * decode http request and encode them back
     * aggregate fractions of http request and pass it on only when a request is assembled
     * last one is the highest level logic to handle requests
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("HttpObjectAggregator",new HttpObjectAggregator(64 * 1024));
        pipeline.addLast("ChunkedWriteHandler",new ChunkedWriteHandler());
        pipeline.addLast("WebSocketServerProtocolHandler",
                new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("TextWebSocketFrameHandler",
                new TextWebSocketFrameHandler(gameManager,
                new GameEventHandler(gameManager)));
    }
}
