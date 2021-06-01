package tasks;

import app.AppController;
import engine.Engine;
import exception.StockNegPriceException;
import exception.StockSymbolLowercaseException;
import exception.XMLException;
import generated.RizpaStockExchangeDescriptor;
import javafx.application.Platform;
import javafx.concurrent.Task;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class FileTask extends Task<Boolean> {

    private Engine engine;
    private String filePath;
    private AppController appController;

    public FileTask(Engine engine, String filePath, AppController appController) {
        this.engine = engine;
        this.filePath = filePath;
        this.appController = appController;
    }


    @Override
    protected Boolean call() throws Exception {
        try {
            RizpaStockExchangeDescriptor descriptor = engine.loadXML(this.filePath);
            long totalStocks = descriptor.getRseStocks().getRseStock().size();
            long totalUsers = descriptor.getRseUsers().getRseUser().size();
            long totalWork = totalStocks + totalStocks;
            long accumWork = 0;
            updateProgress(accumWork, totalWork);
            Platform.runLater(
                    () -> appController.addMessage("Fetching file..")
            );
            Thread.sleep(500);

            this.engine.convertXMLStocks(descriptor);
            accumWork += totalStocks;
            updateProgress(accumWork, totalWork);
            Platform.runLater(
                    () -> appController.addMessage("Fetching stocks..")
            );
            Thread.sleep(500);

            this.engine.convertXMLUsers(descriptor);
            accumWork += totalUsers;
            updateProgress(accumWork, totalWork);
            Platform.runLater(
                    () -> appController.addMessage("Fetching users..")
            );
            Thread.sleep(500);

            updateProgress(totalWork, totalWork);
            Platform.runLater(
                    () -> {
                        appController.clearMessages();
                        appController.addMessage("File loaded successfully");
                    }
            );
            Thread.sleep(500);

            return Boolean.TRUE;
        } catch (StockNegPriceException | XMLException | FileNotFoundException |
                JAXBException | StockSymbolLowercaseException e) {
            Platform.runLater(
                    () -> {
                        appController.clearMessages();
                        appController.addMessage(e.getMessage());
                        if (appController.isXMLLoaded == true)
                            appController.addMessage("The system will continue with the last version.");
                    }
            );
           // updateMessage(e.getMessage());
            return Boolean.FALSE;
        }
    }
}
