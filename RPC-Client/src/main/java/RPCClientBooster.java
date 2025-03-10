import DTO.Product;
import DTO.User;
import RPCRequest.NettyRPCClient;
import RPCRequest.RPCClient;
import RPCRequest.SimpleRPCClient;
import RemoteService.RPCRequestProxy;
import RemoteService.*;

public class RPCClientBooster {
    public static void main(String[] args) {

        //BIO Way to RPC.
//        RPCClient client = new SimpleRPCClient("127.0.0.1",8808);

        RPCClient nettyClient = new NettyRPCClient();
        RPCRequestProxy proxy = new RPCRequestProxy(nettyClient);
        UserService remoteUserService = proxy.getProxy(UserService.class);

        User user2 = User.builder().userAge(1).userID(1).userName("EE Bond").userRole("God Man").build();
        if (remoteUserService.insertUser(user2).equals(1)){
            System.out.println("[Client] Insert User successfully");
        }

        int i = 0;
        while (i<3){
            i++;
            System.out.println("[Client] Search User successfully: User " + remoteUserService.getUserByUserId(i));
        }

        ProductService remoteProductService = proxy.getProxy(ProductService.class);

        System.out.println("[Client] Search Product successfully: Product " +remoteProductService.searchProduct(1));


    }
}
