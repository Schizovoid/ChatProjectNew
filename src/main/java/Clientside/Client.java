package Clientside;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Client implements Initializable {
        @FXML
        public ListView<String> contacts;
        @FXML
        private VBox main;
        @FXML
        private TextField userMessage;
        @FXML
        private TextArea chatArea;
        @FXML
        private Button btnSend;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mockAction(ActionEvent actionEvent) {
        System.out.println("mock");
    }

    public void closeApplication(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void sendMessage(ActionEvent actionEvent) {
    }
}
