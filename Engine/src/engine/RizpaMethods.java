package engine;

import exception.*;
import generated.RizpaStockExchangeDescriptor;
import objects.NewCmdOutcomeDTO;
import objects.StockDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface RizpaMethods {

    public RizpaStockExchangeDescriptor loadXML(String FileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException;
    public List<StockDTO> getAllStocks();
    public StockDTO getStock(String Symbol) throws NoSuchStockException;
    public NewCmdOutcomeDTO addCommand(String userName, String Symbol, Command.CmdType Type, Command.CmdDirection CmdDirection, int Price, int Quantity) throws NoSuchStockException, StockNegQuantityException, CommandNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException, UserHoldingQuntityNotEnough;
    public boolean doesStockExists(String Symbol);

}
