package messages;

import app.AppController;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MessagesController {

    @FXML private ListView<String> messageList;
    @FXML private Label messegeLabel;
    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void clearMessages(){ messageList.getItems().clear(); }

    public void addMessage(String message){
        messageList.getItems().add(message);
    }

}
