import DTO.User;
import RPCRequest.NettyRPCClient;
import RPCRequest.RPCClient;
import RPCRequest.SimpleRPCClient;
import RemoteService.RPCRequestProxy;
import RemoteService.UserService;

public class RPCClientBooster {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 8808;

        //BIO Way to RPC.
//        RPCClient client = new SimpleRPCClient("127.0.0.1",8808);

        RPCClient nettyClient = new NettyRPCClient(host,port);
        RPCRequestProxy proxy = new RPCRequestProxy(nettyClient);
        UserService remoteUserService = proxy.getProxy(UserService.class);


        User user = remoteUserService.getUserByUserId(1);
        System.out.println("[Client] Search User successfully: User " + user);

        User user2 = User.builder().userAge(1).userID(1).userName("EE Bond").userRole("God Man").build();
        Integer deleteResult = remoteUserService.deleteUser(1);
        if (deleteResult == 1){
            System.out.println("[Client] Delete User successfully: User " + user);
        }

        Integer insertResult = remoteUserService.insertUser(user2);
        if (insertResult == 1){
            System.out.println("[Client] Insert User successfully");
        }
        int i = 0;
        while (i<100){
            i++;
            System.out.println("[Client] Search User successfully: User " + remoteUserService.getUserByUserId(i));
        }

    }
}
