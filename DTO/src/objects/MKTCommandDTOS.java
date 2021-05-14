package objects;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
//NOT RELVANTI!!!
// Uniform class for Transaction DTO and Command DTO
public class MKTCommandDTOS extends CommandDTOS {

    public MKTCommandDTOS(int Price, int Quantity, String Date, int Turnover) {
        this.Price = new SimpleIntegerProperty(Price);
        this.Quantity = new SimpleIntegerProperty(Quantity);
        this.Date =new SimpleStringProperty(Date);
        //this.Turnover = new SimpleIntegerProperty(Turnover);
    }


}
