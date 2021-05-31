package header;

import app.AppController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HeaderController {

    @FXML private Button LoadXMLButton;
    @FXML private ComboBox<String> comboTheme;
    @FXML private ProgressBar progressBar;
    @FXML private Label progressPercent;
    @FXML private Label rizpaLabel;
    private AppController mainController;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    private SimpleBooleanProperty progressProperty;

    public HeaderController()
    {
        this.progressProperty=new SimpleBooleanProperty(false);

    }
    @FXML
    private void initialize() {
        this.comboTheme.getItems().add("Default");
        this.comboTheme.getItems().add("Dark");
        this.comboTheme.getItems().add("Blue");
        this.comboTheme.getItems().add("Brown");
        this.progressBar.visibleProperty().bind(progressProperty);
        this.progressPercent.visibleProperty().bind(progressProperty);
    }

    @FXML
    public void openFileButtonAction() {
        progressProperty.set(true);
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

    public void hideProgressBar()
    {
        progressProperty.set(false);
    }

    @FXML
    public void onSelectTheme(){
        mainController.ChangeTheme(comboTheme.getValue());
    }

    public void bindTaskToUI(Task<Boolean> aTask){
        // task progress bar
        progressBar.progressProperty().bind(aTask.progressProperty());
        
       // task percent label
        progressPercent.textProperty().bind(
                Bindings.concat(
                Bindings.format("%.0f",
                        Bindings.multiply(
                                aTask.progressProperty(),
                                100)),
                " %"));
    }
}
