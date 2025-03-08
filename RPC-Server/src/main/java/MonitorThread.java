import DTO.RPCRequest;
import DTO.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class MonitorThread implements Runnable{
    private ServiceMap serviceMap;
    private Socket openingSocket;

    MonitorThread(Socket openingSocket,ServiceMap serviceMap) {
        this.serviceMap = serviceMap;
        this.openingSocket = openingSocket;
    }

    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(openingSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(openingSocket.getInputStream());

            RPCRequest request = (RPCRequest) inputStream.readObject();
            RPCResponse response = handleRequest(request);

            outputStream.writeObject(response);
            outputStream.flush();
            outputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Server Exception] " + e.getMessage());
        }
    }

    private RPCResponse handleRequest(RPCRequest request) {
        String targetInterface = request.getRequestInterface();
        Object service = serviceMap.getService(targetInterface);
        // Get the related service by the Interface name.
        Method method = null;

        try {
            method = service.getClass().getMethod(request.getRequestMethod(),request.getRequestParamsType());
            Object invokeResult = method.invoke(service,request.getRequestParams());
            return RPCResponse.success(invokeResult);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("[Server Exception] " + e.getMessage());
            return RPCResponse.fail();
        }


    }
}
