package engine;

import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
import generated.RizpaStockExchangeDescriptor;
import generated.RseItem;
import generated.RseStock;
import objects.StockDTO;
import objects.UserCommandDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.*;

public class UserManager {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Map<String, User> Users;
    private List<String> Admins;
    private StockManager stocksManger;

    public UserManager() {
        Users = new HashMap<>();
        Admins = new ArrayList<>();
        stocksManger = new StockManager();
    }

    public synchronized void addAdmin(String AdminName)
    {
        Admins.add(AdminName);
    }

    public synchronized void addUser(String username)
    {
        User newUser = new User(username);
        Users.put(username, newUser);
    }

    public synchronized void removeUser(String username) {
        Users.remove(username);
    }

    public synchronized Map<String, User> getUsers() {
        return Collections.unmodifiableMap(Users);
    }
    public synchronized List<String> getAdmins() {
        return Collections.unmodifiableList(Admins);
    }

    private boolean isUserExists(String username) {
        return Users.containsKey(username);
    }
    private boolean isAdminExists(String adminName) {
        return Admins.contains(adminName);
    }
    public boolean isExists(String name)
{
    return isAdminExists(name) || isUserExists(name);
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
        Map<String, Stock> res=new HashMap<>();
        Set<String> setCompanies = new HashSet<>(); // a SET of company name, to check if already exists

        // Converting XML Stocks to Engine stocks
        for (int i = 0; i < stocks.size(); i++) {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());
                // if stock with same Symbol already exists
                if (!this.stocksManger.isStockExists(newStock.getSymbol())) {
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
            } catch (StockNegPriceException | StockSymbolLowercaseException e) {
                throw e;
            }

        }
        return res;
    }

    public void convertXMLUserHolding(RizpaStockExchangeDescriptor descriptor,User user,Map<String, Stock> newStocks) throws XMLException {
        List<RseItem> items = descriptor.getRseHoldings().getRseItem();

        for (RseItem item : items) {
           //if stocks exists in thw system or loaded in the new Xml file its ok
            if(this.stocksManger.isStockExists(item.getSymbol()) || newStocks.containsKey(item.getSymbol()))
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
        for(Stock stock : newStocks.values())
            this.stocksManger.addStock(stock.getSymbol(),stock);

       for (RseItem item : items) {
            Stock stockToHold = this.stocksManger.getStocks().get(item.getSymbol().toUpperCase());
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

    public StockDTO getStock(String Symbol) { return this.stocksManger.getStocks().get(Symbol).convertToDTO(); }

    public List<StockDTO> getStocks() {
        List<StockDTO> res=new ArrayList<>();
        for( String stockSymbol: this.stocksManger.getStocks().keySet())
        {
            res.add(getStock(stockSymbol));
        }
        return res;
    }

    public List<UserCommandDTO> getUserAccountMovementList (String userName) {

        return (this.Users.get(userName).getAllAccountMovements());
    }
    public int getUserFunds(String userName){
        return this.Users.get(userName).getFunds();
    }
    public void addUserFunds(String userName, int funds){
        this.Users.get(userName).AddFunds(funds);
    }

    public void addStock(String userName, String CompanyName, String Symbol, int Price, int Quantity) throws Exception {
        Stock newStock = new Stock(CompanyName, Symbol.toUpperCase(), Price/Quantity);
        if(this.stocksManger.isStockExists(newStock.getSymbol()))
            throw new Exception("The stock: "+ newStock.getSymbol() +" already exists in the system");
        this.stocksManger.addStock(newStock.getSymbol(), newStock);
        Holding newHolding = new Holding(Quantity, newStock);
        this.Users.get(userName).addHolding(newHolding);

    }
}
