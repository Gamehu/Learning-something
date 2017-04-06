package com.elane.smsTrans.psms.process;

import org.apache.log4j.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class TransferClient {
	
	private static Logger log = Logger.getLogger(TransferClient.class);
	
	
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();
//    private Channel channel;
    private Bootstrap b;
	
	
	public void start(String host, int port) throws Exception {

		ChannelFuture f = null;
		try {
			b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
/*					
					ch.pipeline().addFirst(new ChannelInboundHandlerAdapter() {//  中间断开重连
	                    @Override
	                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	                    	log.trace("============Reconnecting==============");
	                        super.channelInactive(ctx);
	                        ctx.channel().eventLoop().schedule(() -> doConnect(SMSConfig.smsServiceIP,SMSConfig.smsServiceport), 1, TimeUnit.SECONDS);
	                    }
	                });					
*/	

					ch.pipeline().addLast(new IdleStateHandler(10,10,20));  //TODO  参数从配置文件读
                    ch.pipeline().addLast(new SMSSendingRequestEncoder());
					ch.pipeline().addLast(new TransferClientHandler());
					
				}
			});

//			doConnect(host,  port);
			
			// Start the client.
			f = b.connect(host, port).sync();

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			log.trace("============shutdownGracefully==============");
			workerGroup = new NioEventLoopGroup();

		}
	}
/*	
    protected void doConnect(String host, int port) {
        if (channel != null && channel.isActive()) {
            return;
        }

        ChannelFuture future = b.connect(host,  port);

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    log.info("Connect to server successfully!");
                } else {
                	log.info("Failed to connect to server, try connect after 10s");//连接失败重试

                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect(host,  port);
                        }
                    }, 1, TimeUnit.SECONDS);
                }
            }
        });
    }
    */
}

