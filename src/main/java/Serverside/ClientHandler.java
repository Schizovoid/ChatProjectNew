package Serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String userName = "blank";
    private String password = "blank";
    private boolean userIsAuthorised = false;

    public ClientHandler (Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    server.Subscribe(this);
                    while (true) {
                        String incoming = in.readUTF();
                        runActivityCheck();
                        if (incoming.startsWith("/auth")){
                            userName = incoming.split("\\s+")[1];
                        } else if (incoming.startsWith("/pswd")) {
                            password = incoming.split("\\s+")[1];
                        }

                        if (!Objects.equals(userName, "blank") && !Objects.equals(password, "blank")) {
                           server.sqlAddUser(userName, password);
                            sendMessage("/authOK " + userName);
                            server.broadcastMsg(userName + " joins the chat!");
                            this.userIsAuthorised = true;
                            break;
                        }
                    }
                    while (true) {
                        String incoming = in.readUTF();
                        if (incoming.startsWith("/")) {
                            continue;
                        }
                        server.broadcastMsg(userName + ": " + incoming);
                    }
                } catch (IOException | SQLException e){
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
    public void disconnect () {
        try {
            if (this.in != null) {
                this.in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (this.out != null) {
                this.out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (this.socket != null) {
                this.socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void runActivityCheck () {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                            if (!userIsAuthorised) {
                                sendMessage("You have been disconnected due to inactivity. Please load the app again to reconnect.");
                                server.Unsubscribe(ClientHandler.this);
                                disconnect();
                            }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 180000);
            }
    }
