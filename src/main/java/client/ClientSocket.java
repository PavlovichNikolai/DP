package client;

import java.io.IOException;
import java.net.Socket;

public class ClientSocket {
    private static Socket socket;

    public static void initSocket() {
        try {
            socket = new Socket("127.0.0.1", 1024);
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Socket getSocket() {
        return socket;
    }
}
