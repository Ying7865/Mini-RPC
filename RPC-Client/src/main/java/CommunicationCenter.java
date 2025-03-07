import DTO.RPCRequest;
import DTO.RPCResponse;
import DTO.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationCenter {

    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try {
            Socket socket = new Socket(host,port);

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            //Send Request to Target Service
            outputStream.writeObject(request);
            outputStream.flush();
            System.out.println("[Client] Send Request Successfully");
            //Server checking Data...

            return (RPCResponse) inputStream.readObject();
        } catch (IOException e) {
            System.out.println("[Client] IO Exception");
        } catch (ClassNotFoundException e) {
            System.out.println("[Client] ClassNotFound Exception");
        }
        return RPCResponse.builder().responseCode(404).build();
    }
}
