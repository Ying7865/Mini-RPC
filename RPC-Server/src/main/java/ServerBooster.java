import server.NettyRPCServer;
import server.PackageScanner;
import server.RPCServer;
import server.ServiceMap;

import java.lang.reflect.InvocationTargetException;

public class ServerBooster {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//        UserService userService = new UserServiceImpl();
//        ProductService productService = new ProductServiceImpl();
//        ServiceMap serviceMap = new ServiceMap();
//        serviceMap.setInterfaceMap(userService);
//        serviceMap.setInterfaceMap(productService);
        //This place could be optimized to automatically recognize the Service.
//        RPCServer rpcServer = new ThreadPoolRPCServer(serviceMap);


        ServiceMap serviceMap = new PackageScanner().scanPackageWithClassGraph("service");
        RPCServer rpcServer = new NettyRPCServer(serviceMap);
        Integer port = Integer.parseInt(System.getProperty("port"));
        int i = port.intValue();
        System.out.println(i);
        rpcServer.start(i);

    }
}
