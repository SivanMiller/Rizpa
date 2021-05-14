package addCommand;

import engine.Command;
import engine.Stock;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import userTab.UserTabController;

public class AddCommandController {
    private UserTabController mainController;
    @FXML private ComboBox<String> StockSymbol;
    @FXML private ComboBox<Stock.CmdType> CommandType;
    @FXML private RadioButton BuyButton;
    @FXML private RadioButton sellButton;
    @FXML private TextField QuantityCommand;
    @FXML private Button addCommandButton;
    @FXML private Label stockSymbolLabel;
    @FXML private Label commandTypeLabel;
    @FXML private Label quantityCommandLabel;
    @FXML private Label priceLabel;
    @FXML private TextField price;

    private Command.CmdDirection CmdDirection;

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
        CommandType.getItems().add(Stock.CmdType.LMT);
        CommandType.getItems().add(Stock.CmdType.MKT);

        BuyButton.setUserData(Command.CmdDirection.BUY);
        sellButton.setUserData(Command.CmdDirection.SELL);
    }

    public void DisplayBuySell()
    {
        sellBuyProperty.set(true);
    }

    @FXML
    private void onActionBuyORSell()
    {
        ObservableList<String> data;
        commandValuesProperty.set(true);
        if (BuyButton.isSelected()){
            data = FXCollections.observableArrayList(mainController.getAllStocks());
            StockSymbol.setItems(data);
            CmdDirection = (Command.CmdDirection)BuyButton.getUserData();
        }
        if (sellButton.isSelected()) {
            data = FXCollections.observableArrayList(mainController.getUserStocks());
            StockSymbol.setItems(data);
            CmdDirection = (Command.CmdDirection)sellButton.getUserData();
        }
    }
    @FXML
    private void onActionCommandValues()
    {
        if(StockSymbol.getValue() != null                                &&
           CommandType.getValue() != null                                &&
           !(QuantityCommand.getText().trim().isEmpty())                 &&
           ((LMTProperty.get() && !price.getText().trim().isEmpty()) ||
            (!LMTProperty.get())) ) {
            addCommandProperty.set(true);
        }
        else {
            addCommandProperty.set(false);
        }

        if(CommandType.getValue() == Stock.CmdType.LMT) {
            LMTProperty.set(true);
        }
        else {
            price.setText("0");
            LMTProperty.set(false);
        }
    }

    public void resetVisibility()
    {
        BuyButton.setSelected(false);
        sellButton.setSelected(false);
        StockSymbol.getSelectionModel().clearSelection();
        CommandType.getSelectionModel().clearSelection();
        QuantityCommand.clear();
        price.clear();
        sellBuyProperty.set(false);
        addCommandProperty.set(false);
        commandValuesProperty.set(false);
        LMTProperty.set(false);
    }

    @FXML
    private void onActionCommand() {
        if (mainController.addCommand(StockSymbol.getValue(), CommandType.getValue(), CmdDirection,
                price.getText(), QuantityCommand.getText())) {
            resetVisibility();
        }
    }

}

