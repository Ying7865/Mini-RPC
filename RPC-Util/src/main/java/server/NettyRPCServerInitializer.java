package server;

import serializer.JsonSerializer;
import serializer.RPCDecoder;
import serializer.RPCEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyRPCServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceMap serviceMap;

    public NettyRPCServerInitializer(ServiceMap serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

//        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
//        pipeline.addLast(new LengthFieldPrepender(4));
//        pipeline.addLast(new ObjectEncoder());
//        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
//            @Override
//            public Class<?> resolve(String className) throws ClassNotFoundException {
//                return Class.forName(className);
//            }
//        }));

        pipeline.addLast(new RPCDecoder());
        pipeline.addLast(new RPCEncoder(new JsonSerializer()));
        pipeline.addLast(new NettyRPCServerHandler(serviceMap));
    }
}
