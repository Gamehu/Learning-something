package com.elane.smsTrans.bHttp;

import org.apache.log4j.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture; 
import io.netty.channel.ChannelInitializer; 
import io.netty.channel.ChannelOption; 
import io.netty.channel.EventLoopGroup; 
import io.netty.channel.nio.NioEventLoopGroup; 
import io.netty.channel.socket.SocketChannel; 
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;

import io.netty.handler.codec.http.HttpServerCodec; 

public class HttpServer {

	private static Logger log = Logger.getLogger(HttpServer.class);

	public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                                @Override
                                public void initChannel(SocketChannel ch) throws Exception {

                                	// Provides support for http objects:
                                	ch.pipeline().addLast("codec", new HttpServerCodec());
                                	// Deals with fragmentation in http traffic: 
                                	ch.pipeline().addLast("aggregator", new HttpObjectAggregator(Short.MAX_VALUE));
                                	// Deals with optional compression of responses:
                                	// ch.pipeline().addLast("aggregator", new HttpContentCompressor());
                                	ch.pipeline().addLast("http-handler",new HttpInboundHandler());                                  
                                    
                                    
                                }
                            }).option(ChannelOption.SO_BACKLOG, 128) 
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();

            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        log.info("Http Server listening on 8844 ...");
        server.start(8844);
    }

}
