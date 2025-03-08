import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServerBooster implements RPCServer{
    private final ThreadPoolExecutor threadPool;
    private ServiceMap serviceMap;

    //Default ThreadPool Creating.
    ThreadPoolRPCServerBooster(ServiceMap serviceMap) {
        this.serviceMap = serviceMap;
        threadPool = new ThreadPoolExecutor(3
                , 6
                , 60
                , TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(100));

    }

    //Should be Exiting a Constructor that could customize the Thread size. (To be continued...)
    // ThreadPoolRPCServerBooster(... ... ...)


    @Override
    public void start(int port) {
        System.out.println("[Server] ThreadPool RPC Server started on port " + port);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("[Server] Start to monitor the request......");
                Socket accept = serverSocket.accept();
                threadPool.execute(new MonitorThread(accept, serviceMap));
            }
        } catch (IOException e) {
            System.out.println("[Server] Could not start the thread pool");
        }
    }

    @Override
    public void stop() {

    }
}
