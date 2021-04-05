package engine;

import exceptions.*;
import objects.StockDTO;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface RizpaMethods {

    public void LoadXML(String sFileName) throws StockNegPriceException, XMLException, FileNotFoundException, JAXBException, StockSymbolLowercaseException;
    public List<StockDTO> getAllStocks();
    public StockDTO getStock(String sSymbol) throws NoSuchStockException;
    public void addCommand(String sSymbol, String sType ,String sCmdDirection, int nPrice, int nQuantity) throws NoSuchStockException, StockNegQuantityException, StockNegPriceException, NoSuchCmdDirectionException, NoSuchCmdTypeException;
    public boolean doesStockExists(String sSymbol);

}
