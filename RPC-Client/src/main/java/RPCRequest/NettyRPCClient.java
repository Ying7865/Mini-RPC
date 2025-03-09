package RPCRequest;

import DTO.RPCRequest;
import DTO.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * First, we should construct the framework of Netty channel:
 *  1. Boostrap: to Boot the Netty Client
 *  2. EventLoopGroup: To specify which event we need to handle.
 *  3. Channel Standard: To stipulate the Message construction.
 *  4. Handler: to handle the specific message or send message to other side.
 */
public class NettyRPCClient implements RPCClient {

    private static final EventLoopGroup eventLoopGroup;
    private static final Bootstrap bootstrap;
    private String host;
    private int port;

    public NettyRPCClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    static{
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }


    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try {
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            System.out.println("[Client] Connect Server successfully");
            channel.writeAndFlush(request);
            System.out.println("[Client] Send message successfully and waiting for response...");
            channel.closeFuture().sync();
            // Should not get the result by Synchronize. Still is BIO.
            System.out.println("[Client] Get response!!!");
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = (RPCResponse) channel.attr(key).get();

            System.out.println(response);
            return response;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
