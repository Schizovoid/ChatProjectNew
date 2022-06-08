package Serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int port = 8189;
    private List<ClientHandler> clients;

    public Server() {
        try {
            this.clients = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
                new Thread(() -> {
                    try {
                        while (true) {
                            Socket socket = serverSocket.accept();
                            System.out.println("Client connected!");
                            ClientHandler c = new ClientHandler(this, socket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Something went wrong Server-side!");
        }
    }
    public void Subscribe (ClientHandler c){
        clients.add(c);
    }
    public void Unsubscribe (ClientHandler c) {
        clients.remove(c);
    }
    public void broadcastMsg (String msg) {
        for (ClientHandler c: clients) {
            c.sendMessage (msg);
        }
    }
}
