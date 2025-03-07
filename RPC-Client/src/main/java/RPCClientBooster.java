import DTO.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCClientBooster {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1",8808);
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            //Send UserId 1 to Server
            outputStream.writeInt(1);
            outputStream.flush();
            //Server checking Data...

            User user = (User) inputStream.readObject();
            System.out.println(user);


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[Client Exception] Exception occur");
        }
    }
}
