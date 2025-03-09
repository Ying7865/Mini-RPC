import RPCResponse.NettyRPCServer;
import RPCResponse.RPCServer;
import RPCResponse.ServiceMap;
import Service.ProductService;
import Service.UserService;
import ServiceImpl.ProductServiceImpl;
import ServiceImpl.UserServiceImpl;

public class ServerBooster {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        ProductService productService = new ProductServiceImpl();
        System.out.println(userService);
        System.out.println(productService);


        ServiceMap serviceMap = new ServiceMap();
        serviceMap.setInterfaceMap(userService);
        serviceMap.setInterfaceMap(productService);
        //This place could be optimized to automatically recognize the Service.


//        RPCServer rpcServer = new ThreadPoolRPCServer(serviceMap);
        RPCServer rpcServer = new NettyRPCServer(serviceMap);
        rpcServer.start(8808);


    }
}
