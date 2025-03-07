import DTO.User;
import Service.UserService;
import ServiceImpl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServerBooster {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8808);
            System.out.println("[Server Listener] Server Boot!");
            //using While to implement the BIO function;
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("[Server Listener] Socket Established");
                new Thread(()->{

                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                        Integer id = inputStream.readInt();
                        User user = userService.getUserByUserId(id);
                        System.out.println("[Server Listener] Client Searching User "+id);
                        outputStream.writeObject(user);
                        outputStream.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[Exception] IO Exception occur!!!");
        }
    }
}
