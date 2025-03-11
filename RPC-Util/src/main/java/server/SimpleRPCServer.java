package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleRPCServer implements RPCServer{

    private ServiceMap serviceMap;

    public SimpleRPCServer(ServiceMap serviceMap){
        this.serviceMap = serviceMap;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //Server Boot
            while(true){
                System.out.println("[Server] Start to monitor the request......");
                Socket accept = serverSocket.accept();
                new Thread(new MonitorThread(accept,serviceMap)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {

    }
}
