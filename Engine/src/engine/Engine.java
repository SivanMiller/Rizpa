//package engine;
//
//import exception.*;
//import objects.NewCmdOutcomeDTO;
//import objects.StockDTO;
//
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.xml.bind.Unmarshaller;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.*;
//
//public class Engine implements RizpaMethods {
//
//    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";
//
//    private Map<String,Stock> Stocks;
//
//    public void loadXML(String FileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException {
//        try {
//            InputStream inputStream = new FileInputStream(new File(FileName));
//            //Getting XML Data
//            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, FileName);
//            //Filling stock collection
//            convertDescriptor(stockDescriptor);
//        } catch (JAXBException | FileNotFoundException | XMLException | StockNegPriceException | StockSymbolLowercaseException e) {
//            throw e;
//        }
//    }
//
//    //Converting XML Data with JAXB
//    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in, String FileName) throws JAXBException {
//        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
//        Unmarshaller u = jc.createUnmarshaller();
//        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
//    }
//r
//
//    //Converting JAXB Data to actual data
//    private void convertDescriptor(RizpaStockExchangeDescriptor stockDescriptor) throws StockNegPriceException, XMLException, StockSymbolLowercaseException {
//        Map<String,Stock> tempMapStocks=this.Stocks;
//        Stocks = new HashMap<>();
//        List<RseStock> stocks = stockDescriptor.getRseStocks().getRseStock();
//        Set<String> setCompanies = new HashSet<>(); // a SET of company name, to check if already exists
//
//        // Converting XML Stocks to Engine stocks
//        for (int i = 0; i < stocks.size(); i++)
//        {
//            try {
//                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());
//                // if stock with same Symbol already exists
//                if (Stocks.get(newStock.getSymbol()) == null) {
//                    // if stock with same Company Name already exists
//                    if (!setCompanies.contains(newStock.getCompanyName())) {
//                        //Insert stock to stock map
//                        Stocks.put(newStock.getSymbol(), newStock);
//                        //Insert company name to company name set
//                        setCompanies.add(newStock.getCompanyName());
//                    }
//                    else {
//                        Stocks = tempMapStocks;
//                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
//                                "Please make sure all stocks are from different companies");
//                    }
//                }
//                else {
//                    Stocks =tempMapStocks;
//                    throw new XMLException("There are two stocks with the same Symbol in the XML you are trying to load." +
//                            "Please make sure all stocks have different Symbols");
//                }
//            }
//            catch (StockNegPriceException | StockSymbolLowercaseException e) {
//                Stocks = tempMapStocks;
//                throw e;
//            }
//        }
//    }
//
//    public List<StockDTO> getAllStocks()  {
//        List<StockDTO> list = new ArrayList<>();
//        for (Stock stock : Stocks.values()) {
//            list.add(stock.convertToDTO());
//        }
//
//        return list;
//    }
//
//    public StockDTO getStock(String Symbol) throws NoSuchStockException {
//        Stock stock = this.Stocks.get(Symbol);
//
//        //Check if such stock exists in stock map
//        if (stock == null)
//            throw new NoSuchStockException();
//
//        return stock.convertToDTO();
//    }
//
//    public NewCmdOutcomeDTO addCommand(String Symbol, String Type , String CmdDirection, int Price, int Quantity) throws NoSuchStockException, StockNegQuantityException, CommandNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException {
//        Stock stock = this.Stocks.get(Symbol);
//
//        //Check if such stock exists in stock map
//        if (stock == null) {
//            throw new NoSuchStockException();
//        }
//        else {
//            try {
//                Command.CmdDirection Direction = convertStringToCmdDirection(CmdDirection);
//                int nType = Integer.parseInt(Type);
//                return stock.addNewCommand(nType, Direction, Price, Quantity);
//            } catch (StockNegQuantityException | CommandNegPriceException | NoSuchCmdDirectionException | NoSuchCmdTypeException e) {
//                throw e;
//            }
//        }
//    }
//
//    public Command.CmdDirection convertStringToCmdDirection(String CmdDirection) throws NoSuchCmdDirectionException {
//        //Turn the cmd direction to int
//        int nDirection = Integer.parseInt(CmdDirection) - 1; // the values of the enum start from 0
//
//        if (nDirection == Command.CmdDirection.BUY.ordinal()) {
//            return Command.CmdDirection.BUY;
//        } else if (nDirection == Command.CmdDirection.SELL.ordinal()) {
//            return Command.CmdDirection.SELL;
//        } else {
//            throw new NoSuchCmdDirectionException();
//        }
//    }
//
//    public boolean doesStockExists(String Symbol) {
//        //Check if such stock exists in stock map
//        return (this.Stocks.containsKey(Symbol));
//    }
//}
//
