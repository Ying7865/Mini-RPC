import DTO.RPCRequest;
import DTO.RPCResponse;
import DTO.User;
import Service.UserService;
import ServiceImpl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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


                        RPCRequest request = (RPCRequest) inputStream.readObject();
                        Method method = userService.getClass().getMethod(request.getRequestMethod(),request.getRequestParamsType());

                        Object invokeResult = method.invoke(userService, request.getRequestParams());
                        outputStream.writeObject(RPCResponse.success(invokeResult));
                        outputStream.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                             InvocationTargetException e) {
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
