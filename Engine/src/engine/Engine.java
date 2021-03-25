package engine;

import com.sun.deploy.security.MozillaJSSNONEwithRSASignature;
import exceptions.StockNegPriceException;
import generated.RizpaStockExchangeDescriptor;
import generated.RseStock;

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

    public void LoadXML(String sFileName) throws StockNegPriceException {
        try {
            InputStream inputStream = new FileInputStream(new File(sFileName));
            //Getting XML Data
            RizpaStockExchangeDescriptor stockDescriptor = deserializeFrom(inputStream, sFileName);
            //Filling stock collection
            try {
                convertDescriptor(stockDescriptor);
            }
            catch (StockNegPriceException e){
                throw e;
            }
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Converting XML Data with JAXB
    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in, String sFileName) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }

    //Converting JAXB Data to actual data
    private void convertDescriptor(RizpaStockExchangeDescriptor stockDescriptor) throws StockNegPriceException {
        mpStocks = new HashMap<>();
        List<RseStock> stocks = stockDescriptor.getRseStocks().getRseStock();
        Set<String> setCompanies = new HashSet<>();

        for (int i = 0; i < stocks.size(); i++)
        {
            try {
                Stock newStock = new Stock(stocks.get(i).getRseCompanyName(), stocks.get(i).getRseSymbol(), stocks.get(i).getRsePrice());

                if (mpStocks.get(newStock.getSymbol()) == null
                        && !setCompanies.contains(newStock.getCompanyName())) {
                    mpStocks.put(newStock.getSymbol(), newStock);
                    setCompanies.add(newStock.getCompanyName());
                }
            }
            catch (StockNegPriceException e)
            {
                throw e;
            }
        }
    }

}

