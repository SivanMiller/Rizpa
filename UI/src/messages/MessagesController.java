package messages;

import app.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class MessagesController {

    @FXML private ListView<String> messageList;
    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    public void clearMessages(){ messageList.getItems().clear(); }

    public void addMessage(String message){
        messageList.getItems().add(message);
    }
}
