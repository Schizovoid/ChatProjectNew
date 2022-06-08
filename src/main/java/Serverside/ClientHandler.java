package Serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String userName;

    public ClientHandler (Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true) {
                        String incoming = in.readUTF();
                        if (incoming.startsWith("/auth")){
                            userName = incoming.split("\\s+")[1];
                            sendMessage("/authOK " + userName);
                            sendMessage("You have entered with the name: " + userName);
                            server.Subscribe(this);
                            break;
                        } else {
                            sendMessage("Please authorize before entering.");
                        }
                    }
                    while (true) {
                        String incoming = in.readUTF();
                        if (incoming.startsWith("/")) {
                            continue;
                        }
                        server.broadcastMsg(userName + ": " + incoming);
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e ) {
            e.printStackTrace();
        } finally {
            server.Unsubscribe(this);
        }
    }

    public void sendMessage(String msg) {
        try {
        out.writeUTF(msg);
        } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
