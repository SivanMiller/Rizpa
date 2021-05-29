package tasks;

import javafx.concurrent.Task;

public class FileTask extends Task<Boolean> {

    @Override
    protected Boolean call() throws Exception {
        Thread.sleep(1000);
        updateMessage("Fetching file..");
        Thread.sleep(1000);
        updateMessage("Fetching stocks..");
        Thread.sleep(1000);
        updateMessage("Fetching users..");
        Thread.sleep(1000);
        updateMessage("File Loaded!");
        updateProgress(100,100);
        return Boolean.TRUE;
    }
}
