package tasks;

import engine.Engine;
import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
import generated.RizpaStockExchangeDescriptor;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class FileTask extends Task<Boolean> {

    private Engine engine;
    private String filePath;

    public FileTask(Engine engine, String file) {
        this.engine = engine;
        this.filePath = file;
    }

    @Override
    protected Boolean call() throws Exception {
        try {
            RizpaStockExchangeDescriptor descriptor = engine.loadXML(this.filePath);
            long totalStocks = descriptor.getRseStocks().getRseStock().size();
            long totalUsers = descriptor.getRseUsers().getRseUser().size();
            long totalWork = totalStocks + totalStocks + 10;
            updateProgress(0, totalWork);
            Thread.sleep(10);
            updateMessage("Fetching file..");
            updateProgress(10, totalWork);
            Thread.sleep(100);

            this.engine.convertXMLStocks(descriptor);
            updateProgress(totalStocks, totalWork);
            updateMessage("Fetching stocks..");
            Thread.sleep(1000);

            this.engine.convertXMLUsers(descriptor);
            updateProgress(totalUsers, totalWork);
            updateMessage("Fetching users..");
            Thread.sleep(1000);

            updateProgress(totalWork, totalWork);
            updateMessage("File loaded successfully");
            return Boolean.TRUE;
        } catch (StockNegPriceException | XMLException | FileNotFoundException |
                JAXBException | StockSymbolLowercaseException e) {
            updateMessage(e.getMessage());
        }
        return Boolean.FALSE;

    }
}
