package Clientside;

import Serverside.ClientHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;



public class Client  {
    public static final int port = 8189;
    public static final String host = "localhost";
    private DataOutputStream out;
    private DataInputStream in;
    private Socket socket;
    public static Path chatLog = Path.of("src/main/resources/ChatLog");
    public static File Log = chatLog.toFile();
    @FXML
    public static ListView <String> contacts = new ListView<>();
    @FXML
    private VBox main;
    @FXML
    private TextField userMessage, usernameField, passwordField;
    @FXML
    private TextArea chatArea;
    @FXML
    private Button btnSend;
    @FXML
    private HBox authPanel, msgPanel;

    public void connect () {
        if (socket != null && !socket.isClosed()) {
            return;
        }
        try {
            Socket socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            chatLogCreation();
            Thread listen = new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String incoming = in.readUTF();
                            if (incoming.startsWith("/authOK ")) {
                                msgPanel.setVisible(true);
                                msgPanel.setManaged(true);
                                authPanel.setVisible(false);
                                authPanel.setManaged(false);
                                break;
                            }
                            chatArea.appendText(incoming + "\n");
                        }
                        updateChat();
                        while (true) {
                            String incoming = in.readUTF();
                            LogMessage(incoming);
                            chatArea.appendText(incoming + "\n");
                        }
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            };
            listen.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }
    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sendMessage(ActionEvent actionEvent) {
        try {
            out.writeUTF(userMessage.getText());
                userMessage.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void tryToAuth () {
        connect();
        try {
            out.writeUTF("/auth " + usernameField.getText());
            usernameField.clear();
            out.writeUTF("/pswd " + passwordField.getText());
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Could not send an authorization request.");
        }
    }
    public static void chatLogCreation () {
        try {
            if (!Log.exists()) {
                Files.createFile(Path.of("src/main/resources/ChatLog"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void updateChat () {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/ChatLog"))) {
            String str;
            while ((str = reader.readLine()) != null) {
                chatArea.appendText(str + "\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void LogMessage (String msg) {
        try (BufferedWriter writer = new BufferedWriter(new
                FileWriter("src/main/resources/ChatLog", true))) {
                writer.write(msg + "\n");
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    public void showError (String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}