package header;

import app.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class HeaderController {

    @FXML private Button LoadXMLButton;
    @FXML private ComboBox<String> comboTheme;
    private AppController mainController;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        this.comboTheme.getItems().add("Default");
        this.comboTheme.getItems().add("Dark");
        this.comboTheme.getItems().add("Blue");
        this.comboTheme.getItems().add("Brown");
    }

    @FXML
    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();

        mainController.loadXML(absolutePath);
    }

    @FXML
    public void onSelectTheme(){
        mainController.ChangeTheme(comboTheme.getValue());
    }
}
