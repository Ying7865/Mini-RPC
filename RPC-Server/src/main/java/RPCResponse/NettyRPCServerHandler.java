package RPCResponse;

import DTO.RPCRequest;
import DTO.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private ServiceMap serviceMap;

    NettyRPCServerHandler(ServiceMap serviceMap) {
        this.serviceMap = serviceMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCRequest rpcRequest) throws Exception {
        RPCResponse response = getResponse(rpcRequest);
        channelHandlerContext.writeAndFlush(response);
        channelHandlerContext.close();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    RPCResponse getResponse(RPCRequest request) {
        String interfaceName = request.getRequestInterface();
        Object service = serviceMap.getService(interfaceName);

        Method method = null;
        try {
            method = service.getClass().getMethod(request.getRequestMethod(),request.getRequestParamsType());
            Object invokeResult = method.invoke(service, request.getRequestParams());
            return RPCResponse.success(invokeResult);


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("[Server] "+e.getMessage());
            return RPCResponse.fail();
        }
    }

}
