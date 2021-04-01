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

public class Engine {

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    Map<String,Stock> mpStocks;

    // TODO: SAVE LAST XML FILE IN TEMP, CHECK UPPER CASE
    public void LoadXML(String sFileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException {
        try {
            InputStream inputStream = new FileInputStream(new File(sFileName));
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, sFileName);
            //Filling stock collection
            convertDescriptor(stockDescriptor);
        } catch (JAXBException | FileNotFoundException | XMLException | StockNegPriceException e) {
            throw e;
        }
    }

    //Converting XML Data with JAXB
    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in, String sFileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }

    //TODO: CHECK WHAT HAPPENS IF FILE NOT GOOD
    //Converting JAXB Data to actual data
    private void convertDescriptor(RizpaStockExchangeDescriptor stockDescriptor) throws StockNegPriceException, XMLException {
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
                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                "Please make sure all stocks are from different companies");
                    }
                }
                else {
                    throw new XMLException("There are two stocks with the same Symbol in the XML you are trying to load." +
                            "Please make sure all stocks have different Symbols");
                }
            }
            catch (StockNegPriceException e)
            {
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

    public void addCommand(String sSymbol, String sCmdType, int nPrice, int nQuantity) throws NoSuchStockException, StockNegQuantityException, StockNegPriceException, NoSuchCmdTypeException {
        Stock stock = this.mpStocks.get(sSymbol);

        //Check if such stock exists in stock map
        if (stock == null)
        {
            throw new NoSuchStockException();
        }
        else {
            try {
                Command.CmdType type = converStringtToCmdType(sCmdType);
                stock.addNewCommand(type, nPrice, nQuantity);
            } catch (StockNegQuantityException | StockNegPriceException | NoSuchCmdTypeException e) {
                throw e;
            }
        }
    }

    public Command.CmdType converStringtToCmdType(String sCommandType) throws NoSuchCmdTypeException {
        int nType = Integer.parseInt(sCommandType) - 1;

        if (nType == Command.CmdType.BUY.ordinal()) {
            return Command.CmdType.BUY;
        } else if (nType == Command.CmdType.SELL.ordinal()) {
            return Command.CmdType.SELL;
        } else {
            throw new NoSuchCmdTypeException();
        }
    }

    public boolean doesStockExists(String sSymbol)
    {
        //Check if such stock exists in stock map
        return (this.mpStocks.containsKey(sSymbol));
    }
}

