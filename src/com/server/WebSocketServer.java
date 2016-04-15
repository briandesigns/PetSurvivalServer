package com.server;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;


/**
 * web socket server
 */
public class WebSocketServer {

    private final ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup();
    private final GameManager gameManager = new GameManager();
    private Channel channel;

    /**
     * starts the server
     * netty initiation sequence
     * @param address
     * @return
     */
    public ChannelFuture start(int address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class)
                .childHandler(createInitializer(gameManager));
        ChannelFuture future = bootstrap.bind(new InetSocketAddress(address));
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }

    /**
     * set up channel handlers
     * channels are needed to communicated between client and server
     * @param gameManager
     * @return
     */
    protected ChannelInitializer<Channel> createInitializer(GameManager gameManager) {
        return new GameServerInitializer(gameManager);
    }

    public void destroy() {
        if (channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

}
