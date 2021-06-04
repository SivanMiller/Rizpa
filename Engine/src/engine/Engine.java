package engine;
import exception.*;
import generated.*;
import javafx.util.Pair;
import objects.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
// TODO: הבעיה היא שפיצלנו את הטעינה של המניות והמשתמשים. אם המתודה של המניות מסתיימת בהצלחה,
// TODO: כלומר אין בעיה בנתונים של המניות, היא מחליפה את הנתונים הקיימים במניות החדשות, בלי
// TODO: תלות בזה שאולי יש שגיאות ביוזרים.
// TODO: הפתרונות שחשבתי עליהם-
// TODO: 1. כל אחת מהמתודות של הטעינה לא תטען נתונים חדשים אלא תחזיר אובייקטים מתאימים ונכתוב
// TODO: מתודה חדשה שמקבלת את שתי האובייקטים ומכניסה אותם למנוע.
// TODO: סוג של COMMIT
// TODO: הבעיה בפתרון הזה היא שהוא יוצר תלות ענקית בין כל המתודות האלה ומחייב לקרוא להן בסדר
// TODO: מסויים וזה פשוט מחורבןןן
// TODO: 2. ליצור עוד שתי אובייקטים במנוע שמכילים את מה שהיה פעם ובמקרה והייתה איזה שגיאה
// TODO: בקובץ היא תחזיר את האובייקטים למה שהיו לפני בטעינה.
// TODO: מן הסתם ברגע שהקובץ תקין אז צריך לשמור גם בהם וגם באובייקטים הקיימים את מה שנטען.
// TODO: הבעיה בפתרון הזה היא שאנחנו שומרים אובייקטים מיותרים וזה מכוער, אבל זה יוצר הרבה פחות
// TODO:  תלות בין המתודות ולא מצריך לכתוב שום מתודה חדשה אלא רק במצב שיש בעיה ביוזרים
// TODO:   בקובץ להחזיר גם את המניות למה שהיו קודם.

public class Engine implements RizpaMethods {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Map<String, Stock> Stocks;
    private Map<String, User> Users;

    public RizpaStockExchangeDescriptor loadXML(String FileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException {
        try {
            if(!FileName.endsWith(".xml")){
                 throw new XMLException("File must be .xml format");
            }
            InputStream inputStream = new FileInputStream(new File(FileName));
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, FileName);
            //Filling stock collection
            return stockDescriptor;
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
        Map<String, Stock> tempMapStocks = this.Stocks;
        Stocks = new HashMap<>();
        Set<String> setCompanies = new HashSet<>(); // a SET of company name, to check if already exists

        // Converting XML Stocks to Engine stocks
        for (int i = 0; i < stocks.size(); i++) {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());
                // if stock with same Symbol already exists
                if (Stocks.get(newStock.getSymbol()) == null) {
                    // if stock with same Company Name already exists
                    if (!setCompanies.contains(newStock.getCompanyName())) {
                        //Insert stock to stock map
                        Stocks.put(newStock.getSymbol(), newStock);
                        //Insert company name to company name set
                        setCompanies.add(newStock.getCompanyName());
                    } else {
                        Stocks = tempMapStocks;
                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                "Please make sure all stocks are from different companies");
                    }
                } else {
                    Stocks = tempMapStocks;
                    throw new XMLException("There are two stocks with the same Symbol in the XML you are trying to load. " +
                            "Please make sure all stocks have different Symbols");
                }
            } catch (StockNegPriceException | StockSymbolLowercaseException e) {
                Stocks = tempMapStocks;
                throw e;
            }
        }
    }

    public void convertXMLUsers(RizpaStockExchangeDescriptor descriptor) throws XMLException {
        List<RseUser> users = descriptor.getRseUsers().getRseUser();
        Map<String, User> tempMapUsers = this.Users;
        Users = new HashMap<>();

        // Converting XML Stocks to Engine stocks
        for (int i = 0; i < users.size(); i++) {

            Map<String, Holding> userHoldings = new HashMap<>();
            List<RseItem> items = users.get(i).getRseHoldings().getRseItem();

            for (RseItem item : items)
            {
                if (Stocks.containsKey(item.getSymbol().toUpperCase())) {
                    Holding holding = new Holding(item.getQuantity(), Stocks.get(item.getSymbol().toUpperCase()));
                    userHoldings.put(holding.getStock().getSymbol(), holding);
                } else {
                    Users = tempMapUsers;
                    throw new XMLException("You are trying to load a user with a holding of stock '" +
                            item.getSymbol() + "'. This stock does not exist. Please make sure all holdings are of valid stocks" );
                }
            }

            User newUser = new User(users.get(i).getName(), userHoldings);

            // if user with same Name already exists
            if (Users.get(newUser.getName()) == null) {
                //Insert user to user map
                Users.put(newUser.getName(), newUser);
            } else {
                Users = tempMapUsers;
                throw new XMLException("There are two users with the same Name in the XML you are trying to load. " +
                        "Please make sure all users have different Names");
            }
        }
    }

    public List<StockDTO> getAllStocks()  {
        List<StockDTO> list = new ArrayList<>();
        for (Stock stock : Stocks.values()) {
            list.add(stock.convertToDTO());
        }

        return list;
    }

    public StockDTO getStock(String Symbol) throws NoSuchStockException {
        Stock stock = this.Stocks.get(Symbol);

        //Check if such stock exists in stock map
        if (stock == null)
            throw new NoSuchStockException();

        return stock.convertToDTO();
    }

    public List<Pair<String, Integer>> getStockHistory(String Symbol){
        return this.Stocks.get(Symbol).getPriceHistory();
    }

    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        for (User user : Users.values()) {
            list.add(user.convertToDTO());
        }

        return list;
    }

    public UserDTO getUser(String Name) {
        User user = this.Users.get(Name);

        return user.convertToDTO();
    }

    public NewCmdOutcomeDTO addCommand(String userName, String Symbol, Command.CmdType Type , Command.CmdDirection CmdDirection, int Price, int Quantity) throws NoSuchStockException, StockNegQuantityException, CommandNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException, UserHoldingQuntityNotEnough {
        Stock stock = this.Stocks.get(Symbol);
        User user = this.Users.get(userName);

        //Check if such stock exists in stock map
        if (stock == null) {
            throw new NoSuchStockException();
        }
        else {
            try {
                if (CmdDirection == Command.CmdDirection.SELL) {
                    int sellQuantity = 0;
                    for (CommandDTO command : stock.getExchangeCollection().getSellCommand()) {
                        if (command.getUser() == userName)
                            sellQuantity += command.getQuantity();
                    }
                    if ((user.getHolding(stock.getSymbol()).getQuantity() - sellQuantity) < Quantity) {
                        throw new UserHoldingQuntityNotEnough(user.getName(), stock.getCompanyName(), sellQuantity);
                    }
                }
                    NewCmdOutcomeDTO newCmdOutcomeDTO = stock.addNewCommand(user, Type, CmdDirection, Price, Quantity);

                    //Commit transaction in users
                    for (TransactionDTO transaction : newCmdOutcomeDTO.getNewTransaction()) {
                        Users.get(transaction.getTransactionBuyUser()).commitBuyTransaction(stock, transaction);
                        Users.get(transaction.getTransactionSellUser()).commitSellTransaction(stock, transaction);
                    }

                    return newCmdOutcomeDTO;

            } catch (StockNegQuantityException | CommandNegPriceException | NoSuchCmdTypeException | UserHoldingQuntityNotEnough e) {
                throw e;
            }
        }
    }

    public Command.CmdDirection convertStringToCmdDirection(String CmdDirection) throws NoSuchCmdDirectionException {
        //Turn the cmd direction to int
        int nDirection = Integer.parseInt(CmdDirection) - 1; // the values of the enum start from 0

        if (nDirection == Command.CmdDirection.BUY.ordinal()) {
            return Command.CmdDirection.BUY;
        } else if (nDirection == Command.CmdDirection.SELL.ordinal()) {
            return Command.CmdDirection.SELL;
        } else {
            throw new NoSuchCmdDirectionException();
        }
    }

    public boolean doesStockExists(String Symbol) {
        //Check if such stock exists in stock map
        return (this.Stocks.containsKey(Symbol));
    }

    public ExchangeCollectionDTO getExchangeCollectionDTO(String stockSymbol)
    {
        return this.Stocks.get(stockSymbol).getExchangeCollection();
    }
}

