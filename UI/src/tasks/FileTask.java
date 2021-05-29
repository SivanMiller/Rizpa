package tasks;

import javafx.concurrent.Task;

public class FileTask extends Task<Boolean> {

    @Override
    protected Boolean call() throws Exception {
        updateProgress(0,100);
        Thread.sleep(1000);
        updateProgress(20,100);
        updateMessage("Fetching file..");
        Thread.sleep(1000);
        updateProgress(40,100);
        updateMessage("Fetching stocks..");
        Thread.sleep(1000);
        updateProgress(60,100);
        updateMessage("Fetching users..");
        Thread.sleep(1000);
        updateProgress(80,100);
        updateMessage("File Loaded!");
        updateProgress(100,100);
        return Boolean.TRUE;
    }
}
