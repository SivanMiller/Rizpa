package addCommand;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import userDetails.StockDetails;
import userTab.UserTabController;

public class AddCommandController {
    private UserTabController mainController;
    @FXML private ComboBox<String> StockSymbol;
    @FXML private ComboBox<String> CommandType;
    @FXML private RadioButton BuyButton;
    @FXML private RadioButton sellButton;
    @FXML private TextField QuantityCommand;
    @FXML private Button addCommandButton;
    @FXML private Label stockSymbolLabel;
    @FXML private Label commandTypeLabel;
    @FXML private Label quantityCommandLabel;
    @FXML private Label priceLabel;
    @FXML private TextField price;


    private SimpleBooleanProperty sellBuyProperty;
    private SimpleBooleanProperty commandValuesProperty;
    private SimpleBooleanProperty addCommandProperty;
    private SimpleBooleanProperty LMTProperty;

    public void setMainController(UserTabController mainController) {
        this.mainController = mainController;
    }

    public AddCommandController() {
        this.sellBuyProperty = new SimpleBooleanProperty(false);
        this.commandValuesProperty = new SimpleBooleanProperty(false);
        this.addCommandProperty = new SimpleBooleanProperty(false);
        this.LMTProperty = new SimpleBooleanProperty(false);
    }

    @FXML
    private void initialize() {
        StockSymbol.disableProperty().bind(commandValuesProperty.not());
        CommandType.disableProperty().bind(commandValuesProperty.not());
        BuyButton.disableProperty().bind(sellBuyProperty.not());
        sellButton.disableProperty().bind(sellBuyProperty.not());
        QuantityCommand.disableProperty().bind(commandValuesProperty.not());
        addCommandButton.disableProperty().bind(addCommandProperty.not());
        stockSymbolLabel.disableProperty().bind(commandValuesProperty.not());
        quantityCommandLabel.disableProperty().bind(commandValuesProperty.not());
        commandTypeLabel.disableProperty().bind(commandValuesProperty.not());
        price.visibleProperty().bind(LMTProperty);
        priceLabel.visibleProperty().bind(LMTProperty);

        // initialize command type chooser
        CommandType.getItems().add("LMT");
        CommandType.getItems().add("MKT");

    }

    public void togggleDisplay()
    {
        sellBuyProperty.set(!sellBuyProperty.get());
    }

    @FXML
    private void onActionBuyORSell()
    {
        ObservableList<String> data;
        commandValuesProperty.set(true);
        if (BuyButton.isSelected()){
            data = FXCollections.observableArrayList(mainController.getAllStocks());
            StockSymbol.setItems(data);
        }
        if (sellButton.isSelected()) {
            data = FXCollections.observableArrayList(mainController.getUserStocks());
            StockSymbol.setItems(data);
        }
    }
    @FXML
    private void onActionCommandValues()
    {
        if(CommandType.getValue()!=null && CommandType.getValue()=="LMT")
        {
            LMTProperty.set(true);
        }
        else
        {
         LMTProperty.set(false);
        }
        if(StockSymbol.getValue()!=null && CommandType.getValue()!=null && !(QuantityCommand.getText().trim().isEmpty())
                                                                && ((LMTProperty.get() && !price.getText().trim().isEmpty()) || !LMTProperty.get()) )

           addCommandProperty.set(true);

    }
    @FXML
    private void onActionCommand()
    {

        String CmdDirection="";
        if (BuyButton.isSelected()){
            CmdDirection="1";
        }
        else
            CmdDirection="2";
        mainController.addCommand(StockSymbol.getValue(),CommandType.getValue(),CmdDirection,"0",QuantityCommand.getText());
    }

}

