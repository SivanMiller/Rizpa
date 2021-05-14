package userTab.addCommand;

import engine.Command;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import userTab.UserTabController;

public class AddCommandController {
    private UserTabController mainController;
    @FXML private ComboBox<String> stockSymbol;
    @FXML private ComboBox<Command.CmdType> commandType;
    @FXML private RadioButton buyButton;
    @FXML private RadioButton sellButton;
    @FXML private TextField commandQuantity;
    @FXML private Button addCommandButton;
    @FXML private Label stockSymbolLabel;
    @FXML private Label commandTypeLabel;
    @FXML private Label commandQuantityLabel;
    @FXML private Label commandPriceLabel;
    @FXML private TextField commandPrice;

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
        stockSymbol.disableProperty().bind(commandValuesProperty.not());
        commandType.disableProperty().bind(commandValuesProperty.not());
        buyButton.disableProperty().bind(sellBuyProperty.not());
        sellButton.disableProperty().bind(sellBuyProperty.not());
        commandQuantity.disableProperty().bind(commandValuesProperty.not());
        addCommandButton.disableProperty().bind(addCommandProperty.not());
        stockSymbolLabel.disableProperty().bind(commandValuesProperty.not());
        commandQuantityLabel.disableProperty().bind(commandValuesProperty.not());
        commandTypeLabel.disableProperty().bind(commandValuesProperty.not());
        commandPrice.visibleProperty().bind(LMTProperty);
        commandPriceLabel.visibleProperty().bind(LMTProperty);

        // initialize command type chooser
        commandType.getItems().add(Command.CmdType.LMT);
        commandType.getItems().add(Command.CmdType.MKT);

        buyButton.setUserData(Command.CmdDirection.BUY);
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
        if (buyButton.isSelected()){
            data = FXCollections.observableArrayList(mainController.getAllStocks());
            stockSymbol.setItems(data);
            CmdDirection = (Command.CmdDirection)buyButton.getUserData();
        }
        if (sellButton.isSelected()) {
            data = FXCollections.observableArrayList(mainController.getUserStocks());
            stockSymbol.setItems(data);
            CmdDirection = (Command.CmdDirection)sellButton.getUserData();
        }
    }
    @FXML
    private void onActionCommandValues()
    {
        if(stockSymbol.getValue() != null                                &&
           commandType.getValue() != null                                &&
           !(commandQuantity.getText().trim().isEmpty())                 &&
           ((LMTProperty.get() && !commandPrice.getText().trim().isEmpty()) ||
            (!LMTProperty.get())) ) {
            addCommandProperty.set(true);
        }
        else {
            addCommandProperty.set(false);
        }

        if(commandType.getValue() == Command.CmdType.LMT) {
            LMTProperty.set(true);
        }
        else {
            commandPrice.setText("0");
            LMTProperty.set(false);
        }
    }

    public void resetVisibility()
    {
        buyButton.setSelected(false);
        sellButton.setSelected(false);
        stockSymbol.getSelectionModel().clearSelection();
        commandType.getSelectionModel().clearSelection();
        commandQuantity.clear();
        commandPrice.clear();
        sellBuyProperty.set(false);
        addCommandProperty.set(false);
        commandValuesProperty.set(false);
        LMTProperty.set(false);
    }

    @FXML
    private void onActionCommand() {
        if (mainController.addCommand(stockSymbol.getValue(), commandType.getValue(), CmdDirection,
                commandPrice.getText(), commandQuantity.getText())) {
            resetVisibility();
        }
    }

}

