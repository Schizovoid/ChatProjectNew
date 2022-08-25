package Serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static final int port = 8189;
    private List<ClientHandler> clients;
    private static Connection connection;
    private static Statement stmt;

    public Server() {
        try {
            this.clients = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
            sqlConnect();
            createTable();
                new Thread(() -> {
                    try {
                        while (true) {
                            Socket socket = serverSocket.accept();
                            System.out.println("Client connected!");
                            ClientHandler c = new ClientHandler(this, socket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        sqlDisconnect();
                    }
                }).start();

        } catch (IOException | SQLException | ClassNotFoundException e){
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
    public static void sqlConnect() throws SQLException, ClassNotFoundException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/authUsers.db");
        stmt = connection.createStatement();
    }
    private static void createTable() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Users (\n" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " username TEXT,\n" +
                " password TEXT \n" +
                " );");
    }
    static void sqlAddUser(String userName, String password) throws SQLException {
                stmt.executeUpdate(String.format("INSERT INTO Users (username, password)\n" +
                "VALUES ('%s', '%s')", userName, password));
    }
        boolean isLoginUsed (String userName) throws SQLException {
            ResultSet rs = stmt.executeQuery(String.format("SELECT username FROM Users WHERE username LIKE ('%s')", userName));
            return rs.isBeforeFirst();
        }
        boolean isPasswordCorrect (String userName, String password) throws SQLException {
            ResultSet rs = stmt.executeQuery(String.format("SELECT password FROM Users WHERE username LIKE ('%s')", userName));
            return rs.getString(1).equals(password);
        }

    public static void sqlDisconnect() {
        try {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
