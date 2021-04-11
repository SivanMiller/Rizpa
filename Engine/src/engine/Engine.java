package engine;

import exceptions.*;
import generated.RizpaStockExchangeDescriptor;
import generated.RseStock;
import objects.StockDTO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class Engine implements RizpaMethods {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    private Map<String,Stock> mpStocks;

    public void LoadXML(String sFileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException {
        try {
            InputStream inputStream = new FileInputStream(new File(sFileName));
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, sFileName);
            //Filling stock collection
            convertDescriptor(stockDescriptor);
        } catch (JAXBException | FileNotFoundException | XMLException | StockNegPriceException | StockSymbolLowercaseException e) {
            throw e;
        }
    }

    //Converting XML Data with JAXB
    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in, String sFileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }


    //Converting JAXB Data to actual data
    private void convertDescriptor(RizpaStockExchangeDescriptor stockDescriptor) throws StockNegPriceException, XMLException, StockSymbolLowercaseException {
        Map<String,Stock> tempMapStocks=this.mpStocks;
        mpStocks = new HashMap<>();
        List<RseStock> stocks = stockDescriptor.getRseStocks().getRseStock();
        Set<String> setCompanies = new HashSet<>(); // a SET of company name, to check if already exists

        // Converting XML Stocks to Engine stocks
        for (int i = 0; i < stocks.size(); i++)
        {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());
                // if stock with same Symbol already exists
                if (mpStocks.get(newStock.getSymbol()) == null) {
                    // if stock with same Company Name already exists
                    if (!setCompanies.contains(newStock.getCompanyName())) {
                        //Insert stock to stock map
                        mpStocks.put(newStock.getSymbol(), newStock);
                        //Insert company name to company name set
                        setCompanies.add(newStock.getCompanyName());
                    }
                    else {
                        mpStocks=tempMapStocks;
                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                "Please make sure all stocks are from different companies");
                    }
                }
                else {
                    mpStocks=tempMapStocks;
                    throw new XMLException("There are two stocks with the same Symbol in the XML you are trying to load." +
                            "Please make sure all stocks have different Symbols");
                }
            }
            catch (StockNegPriceException | StockSymbolLowercaseException e)
            {
                mpStocks=tempMapStocks;
                throw e;
            }
        }
    }

    public List<StockDTO> getAllStocks()
    {
        List<StockDTO> list = new ArrayList<>();
        for (Stock stock : mpStocks.values()) {
            list.add(stock.convertToDTO());
        }

        return list;
    }

    public StockDTO getStock(String sSymbol) throws NoSuchStockException {
        Stock stock = this.mpStocks.get(sSymbol);

        //Check if such stock exists in stock map
        if (stock == null)
            throw new NoSuchStockException();

        return stock.convertToDTO();
    }

    public void addCommand(String sSymbol, String sType ,String sCmdDirection, int nPrice, int nQuantity) throws NoSuchStockException, StockNegQuantityException, CommandNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException {
        Stock stock = this.mpStocks.get(sSymbol);

        //Check if such stock exists in stock map
        if (stock == null)
        {
            throw new NoSuchStockException();
        }
        else {
            try {
                Command.CmdDirection Direction = convertStringToCmdDirection(sCmdDirection);
                int nType = Integer.parseInt(sType);
                stock.addNewCommand(nType, Direction, nPrice, nQuantity);
            } catch (StockNegQuantityException | CommandNegPriceException | NoSuchCmdDirectionException | NoSuchCmdTypeException e) {
                throw e;
            }
        }
    }

    public Command.CmdDirection convertStringToCmdDirection(String sCmdDirection) throws NoSuchCmdDirectionException {
        //Turn the cmd direction to int
        int nDirection = Integer.parseInt(sCmdDirection) - 1; // the values of the enum start from 0

        if (nDirection == Command.CmdDirection.BUY.ordinal()) {
            return Command.CmdDirection.BUY;
        } else if (nDirection == Command.CmdDirection.SELL.ordinal()) {
            return Command.CmdDirection.SELL;
        } else {
            throw new NoSuchCmdDirectionException();
        }
    }

    public boolean doesStockExists(String sSymbol)
    {
        //Check if such stock exists in stock map
        return (this.mpStocks.containsKey(sSymbol));
    }
}

