package engine;

import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
import generated.RizpaStockExchangeDescriptor;
import generated.RseItem;
import generated.RseStock;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class UserManager {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Map<String, User> Users;
    private List<String> Admins;
    private StockManager stocksManger;
    public UserManager() {
        Users = new HashMap<>();
        Admins = new ArrayList<>();
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
    private boolean isAdminExists(String adminname) {
        return Admins.contains(adminname);
    }
    public boolean isExists(String name)
{
    return isAdminExists(name)|| isUserExists(name);
}

    public void loadXML(String FileName, String userName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException {
        try {
            if(!FileName.endsWith(".xml")){
                throw new XMLException("File must be .xml format");
            }
            InputStream inputStream = new FileInputStream(new File(FileName));
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, FileName);
            //Filling stock collection
        convertXMLStocks(stockDescriptor);

        convertXMLUserHolding(stockDescriptor,Users.get(userName));
        } catch (JAXBException | FileNotFoundException | XMLException  e) {
            throw e;
        }
    }
    //Converting XML Data with JAXB
    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in, String FileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }

    public void convertXMLStocks(RizpaStockExchangeDescriptor descriptor) throws XMLException, StockNegPriceException, StockSymbolLowercaseException {
        List<RseStock> stocks = descriptor.getRseStocks().getRseStock();
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
                        this.stocksManger.addStock(newStock.getSymbol(), newStock);
                        //Insert company name to company name set
                        setCompanies.add(newStock.getCompanyName());
                    }
                    //the company name is already exists
                    else {

                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                "Please make sure all stocks are from different companies");
                    }
                }
            } catch (StockNegPriceException | StockSymbolLowercaseException e) {
                throw e;
            }
        }
    }

    public void convertXMLUserHolding(RizpaStockExchangeDescriptor descriptor,User user) throws XMLException {
        List<RseItem> items =descriptor.getRseHoldings().getRseItem();

            for (RseItem item : items)
            {
                Stock stockToHold=this.stocksManger.getStocks().get(item.getSymbol().toUpperCase());
                //check if stock symbol exists
                if (stockToHold!= null) {           //stock Symbol exists
                    //check if the user is already have this stock
                    Holding holding = user.getHolding(item.getSymbol().toUpperCase());
                    if (holding != null)
                        holding.setQuantity(holding.getQuantity() + item.getQuantity());
                    else {
                        Holding newHolding = new Holding(item.getQuantity(), stockToHold);
                        user.addHolding(newHolding);
                    }
                }
                else {//stock dont exists!!

                   throw new XMLException("You are trying to load a user with a holding of stock '" +
                            item.getSymbol() + "'. This stock does not exist. Please make sure all holdings are of valid stocks" );
                }
            }

    }

}
