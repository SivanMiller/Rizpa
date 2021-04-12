package engine;

import exceptions.*;
import objects.NewCmdOutcomeDTO;
import objects.StockDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface RizpaMethods {

    public void loadXML(String FileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException;
    public List<StockDTO> getAllStocks();
    public StockDTO getStock(String Symbol) throws NoSuchStockException;
    public NewCmdOutcomeDTO addCommand(String Symbol, String Type , String CmdDirection, int Price, int Quantity) throws NoSuchStockException, StockNegQuantityException, CommandNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException;
    public boolean doesStockExists(String Symbol);

}
