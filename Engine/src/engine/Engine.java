package engine;

import com.sun.deploy.security.MozillaJSSNONEwithRSASignature;
import exceptions.StockNegPriceException;
import exceptions.XMLException;
import generated.RizpaStockExchangeDescriptor;
import generated.RseStock;

import javax.transaction.xa.XAException;
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

    //Converting JAXB Data to actual data
    private void convertDescriptor(RizpaStockExchangeDescriptor stockDescriptor) throws StockNegPriceException, XMLException {
        mpStocks = new HashMap<>();
        List<RseStock> stocks = stockDescriptor.getRseStocks().getRseStock();
        Set<String> setCompanies = new HashSet<>();

        for (int i = 0; i < stocks.size(); i++)
        {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());

                if (mpStocks.get(newStock.getSymbol()) == null) {
                    if (!setCompanies.contains(newStock.getCompanyName())) {
                        mpStocks.put(newStock.getSymbol(), newStock);
                        setCompanies.add(newStock.getCompanyName());
                    }
                    else{
                        throw new XMLException("There are two stocks with the same Company Name in the XML you are trying to load." +
                                               "Please make sure all stocks are from different companies");
                    }
                }
                else{
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

}

