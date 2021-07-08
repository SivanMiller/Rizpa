package engine;

import objects.CommandDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IOCCommand extends Command{
  IOCCommand(){this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());}
    @Override
    public CommandDTO convertToDTO() {
        return new CommandDTO(this.Date, "IOC", this.Quantity, this.Price,this.user.getName());

    }

    @Override
    public String toString() {
        return null;
    }
}
