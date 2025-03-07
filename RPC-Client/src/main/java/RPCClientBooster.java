import DTO.User;
import RemoteService.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCClientBooster {
    public static void main(String[] args) {
        RPCRequestProxy proxy = new RPCRequestProxy("127.0.0.1",8808);
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


    }
}
