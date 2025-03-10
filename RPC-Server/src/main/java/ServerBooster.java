import RPCResponse.NettyRPCServer;
import RPCResponse.RPCServer;
import RPCResponse.ServiceMap;
import Service.ProductService;
import Service.UserService;
import ServiceImpl.ProductServiceImpl;
import ServiceImpl.UserServiceImpl;

import java.net.InetSocketAddress;

public class ServerBooster {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProductService productService = new ProductServiceImpl();


        ServiceMap serviceMap = new ServiceMap();
        serviceMap.setInterfaceMap(userService);
        serviceMap.setInterfaceMap(productService);
        //This place could be optimized to automatically recognize the Service.



//        RPCServer rpcServer = new ThreadPoolRPCServer(serviceMap);
        RPCServer rpcServer = new NettyRPCServer(serviceMap);
        rpcServer.start(8808);


    }
}
