package engine;
import exception.*;
import generated.RizpaStockExchangeDescriptor;
import generated.RseItem;
import generated.RseStock;
import javafx.util.Pair;
import objects.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class UserManager {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Map<String, User> Users;
    private List<String> Admins;
    private StockManager StocksManger;
    private ChatManager chatManager;

    public UserManager() {
        Users = new HashMap<>();
        Admins = new ArrayList<>();
        StocksManger = new StockManager();
    }

    public synchronized void addAdmin(String AdminName)
    {
        Admins.add(AdminName);
    }

    public synchronized void addUser(String UserName)
    {
        User newUser = new User(UserName);
        Users.put(UserName, newUser);
    }

    public synchronized Map<String, User> getUsers() {
        return Collections.unmodifiableMap(Users);
    }
    public synchronized List<String> getAdmins() {
        return Collections.unmodifiableList(Admins);
    }
    public int getUserHoldForStock(String userName, String Symbol)
    {
        Holding holding=this.Users.get(userName).getHolding(Symbol);
        if(holding==null)
            return 0;
        else
            return holding.getQuantity();
    }
    private boolean isUserExists(String UserName) {
        return Users.containsKey(UserName);
    }
    private boolean isAdminExists(String AdminName) {
        return Admins.contains(AdminName);
    }
    public boolean isExists(String Name)
{
    return isAdminExists(Name) || isUserExists(Name);
}

    public void loadXML(InputStream in, String userName) throws StockNegPriceException, XMLException, JAXBException, StockSymbolLowercaseException {
        try {
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(in);

            //Filling stock collection
            Map<String, Stock> newStocks=convertXMLStocks(stockDescriptor);
            //Filling user collection
            convertXMLUserHolding(stockDescriptor,Users.get(userName),newStocks);
        } catch (JAXBException | XMLException | StockNegPriceException | StockSymbolLowercaseException e) {
            throw e;
        }
    }

    //Converting XML Data with JAXB
    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }

    public Map<String, Stock> convertXMLStocks(RizpaStockExchangeDescriptor descriptor) throws XMLException, StockNegPriceException, StockSymbolLowercaseException {
        List<RseStock> stocks = descriptor.getRseStocks().getRseStock();
        Map<String, Stock> res = new HashMap<>();
        Set<String> setCompanies = new HashSet<>(); // a SET of company name, to check if already exists

        // Converting XML Stocks to Engine stocks
        for (int i = 0; i < stocks.size(); i++) {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());
                // if stock with same Symbol already exists
                if (!this.StocksManger.isStockExists(newStock.getSymbol())) {
                    // if stock with same Company Name already exists
                    if (!setCompanies.contains(newStock.getCompanyName())) {
                        //Insert stock to stock map
                        res.put(newStock.getSymbol(), newStock);
                        //Insert company name to company name set
                        setCompanies.add(newStock.getCompanyName());
                    }
                    //the company name already exists
                    else {
                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                "Please make sure all stocks are from different companies");
                    }
                }
                else
                    res.put(newStock.getSymbol(), newStock);
            } catch (StockNegPriceException | StockSymbolLowercaseException e) {
                throw e;
            }

        }
        return res;
    }

    public void convertXMLUserHolding(RizpaStockExchangeDescriptor descriptor, User user, Map<String, Stock> newStocks) throws XMLException {
        List<RseItem> items = descriptor.getRseHoldings().getRseItem();

        for (RseItem item : items) {
           //if stocks loaded in the new Xml file its ok
            if(newStocks.containsKey(item.getSymbol()))
            {
                continue;
            }
            //stock doesn't exist!!
            else {
                throw new XMLException("You are trying to load a holding of stock '" +
                        item.getSymbol() + "'. This stock does not exist. Please make sure all holdings are of valid stocks");
            }
       }
        //the file is proper and we can save the new data in the system
        for(Stock stock : newStocks.values()) {
            //enter to the file only the new stocks
            if(!this.StocksManger.isStockExists(stock.getSymbol()))
            this.StocksManger.addStock(stock.getSymbol(), stock);
        }

       for (RseItem item : items) {
            Stock stockToHold = this.StocksManger.getStocks().get(item.getSymbol().toUpperCase());
                //check if the user already has this stock
                Holding holding = user.getHolding(item.getSymbol().toUpperCase());
                if (holding != null)
                    holding.setQuantity(holding.getQuantity() + item.getQuantity());
                else {
                    Holding newHolding = new Holding(item.getQuantity(), stockToHold);
                    user.addHolding(newHolding);
                }
            }
    }
    public List<TransactionDTO> getStockTransactionsList(String stockSymbol){ return this.StocksManger.getStockTransactionsList(stockSymbol);}
    public List<CommandDTO> getStockBuyCommandsList(String stockSymbol){ return this.StocksManger.getStockBuyCommandList(stockSymbol);}
    public List<CommandDTO> getStockSellCommandsList(String stockSymbol){ return this.StocksManger.getStockSellCommandList(stockSymbol);}
    public StockDTO getStock(String Symbol) { return this.StocksManger.getStocks().get(Symbol).convertToDTO(); }

    public List<StockDTO> getStocks() {
        List<StockDTO> res = new ArrayList<>();
        for( String stockSymbol: this.StocksManger.getStocks().keySet())
        {
            res.add(getStock(stockSymbol));
        }
        return res;
    }

    public List<UserCommandDTO> getUserAccountMovementList (String UserName) {

        return (this.Users.get(UserName).getAccountMovements());
    }

    public int getUserFunds(String UserName){
        return this.Users.get(UserName).getFunds();
    }

    public void addUserFunds(String UserName, int Funds){
        this.Users.get(UserName).AddFunds(Funds);
    }

    public void addStock(String UserName, String CompanyName, String Symbol, int Price, int Quantity) throws Exception {
        Stock newStock = new Stock(CompanyName, Symbol.toUpperCase(), Price/Quantity);
        if(this.StocksManger.isStockExists(newStock.getSymbol()))
            throw new Exception("The stock: " + newStock.getSymbol() + " already exists in the system");
        this.StocksManger.addStock(newStock.getSymbol(), newStock);
        Holding newHolding = new Holding(Quantity, newStock);
        this.Users.get(UserName).addHolding(newHolding);

    }

    public Set<String> getUserStocksSymbols(String UserName) {
        return this.Users.get(UserName).getHoldings().keySet();
    }

    public int getUserHoldingQuantity(String UserName, String Symbol) {

        Holding holding= this.Users.get(UserName).getHolding(Symbol);
        if(holding==null)
            return 0;
        else
            return holding.getQuantity();
    }

    public NewCmdOutcomeDTO addNewCommand(String UserName, String Symbol, String strType,
                                          String strCmdDirection, int Price, int Quantity) throws NoSuchCmdTypeException, UserHoldingQuntityNotEnough, StockNegQuantityException, CommandNegPriceException, NoSuchStockException {
        Command.CmdType Type = this.convertStringToCmdType(strType);
        Command.CmdDirection CmdDirection = this.convertStringToCmdDirection(strCmdDirection);
        Stock Stock = this.StocksManger.getStocks().get(Symbol);
        User User = this.Users.get(UserName);

        //Check if such Stock exists in Stock map
        if (Stock == null) {
            throw new NoSuchStockException();
        }
        else {
            try {
                if (CmdDirection == Command.CmdDirection.SELL) {
                    int sellQuantity = 0;
                    for (CommandDTO command : Stock.getExchangeCollection().getSellCommand()) {
                        if (command.getUser() == UserName)
                            sellQuantity += command.getQuantity();
                    }
                    if ((User.getHolding(Stock.getSymbol()).getQuantity() - sellQuantity) < Quantity) {
                        throw new UserHoldingQuntityNotEnough(User.getName(), Stock.getCompanyName(), sellQuantity);
                    }
                }
                    NewCmdOutcomeDTO newCmdOutcomeDTO = Stock.addNewCommand(User, Type, CmdDirection, Price, Quantity);

                    //Commit transaction in users
                    for (TransactionDTO transaction : newCmdOutcomeDTO.getNewTransaction()) {
                        Users.get(transaction.getTransactionBuyUser()).commitBuyTransaction(Stock, transaction);
                        Users.get(transaction.getTransactionSellUser()).commitSellTransaction(Stock, transaction);
                    }

                    return newCmdOutcomeDTO;

            } catch (StockNegQuantityException | CommandNegPriceException | NoSuchCmdTypeException | UserHoldingQuntityNotEnough e) {
                throw e;
            }
        }
    }

    public List<Pair<String, TransactionDTO>> reportTransactions(String UserName) {
        return this.Users.get(UserName).reportTransactions();
    }

    public Command.CmdDirection convertStringToCmdDirection(String CmdDirection) {
        switch(CmdDirection){
            case("Sell"): {
                return Command.CmdDirection.SELL;
            }
            case("Buy"): {
                return Command.CmdDirection.BUY;
            }
        }
        return null;
    }

    public Command.CmdType convertStringToCmdType(String CmdType) {
        switch(CmdType) {
            case ("LMT"): {
                return Command.CmdType.LMT;
            }
            case ("MKT"): {
                return Command.CmdType.MKT;
            }
            case ("FOK"): {
                return Command.CmdType.FOK;
            }
            case ("IOC"): {
                return Command.CmdType.IOC;
            }
        }
        return null;
    }

    public void addMessage(String message,String userName)
    {

    }

    public List<Message> getChatList()
    {
        return  this.chatManager.getChatDataList();
    }
}
