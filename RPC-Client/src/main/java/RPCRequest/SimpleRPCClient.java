package RPCRequest;

import DTO.RPCRequest;
import DTO.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;

    public SimpleRPCClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public RPCResponse sendRequest(RPCRequest request) {

        try {
            Socket socket = new Socket(host, port);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            //Send Request to Target Service
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("[Client] Send Request Successfully");
            //Server checking Data...

            return (RPCResponse) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Client] " + e.getMessage());
        }
        return RPCResponse.builder().responseCode(404).build();

    }
}
