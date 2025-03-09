package RPCResponse;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyRPCServer implements RPCServer {
    private ServiceMap serviceMap;

    public NettyRPCServer(ServiceMap serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    public void start(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("Netty RPC Server started on port " + port);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyRPCServerInitializer(serviceMap));
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }
}
