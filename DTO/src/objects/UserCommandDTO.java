package objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserCommandDTO {
    public String Type;
    public String Date;
    public int Price;
    public int remainderBefore;
    public int remainderAfter;
    public enum CmdType {
       BUY,
        SELL,
        CHARGE
    };

    public UserCommandDTO(String type, String date, int price, int remainderBefore, int remainderAfter) {
        Type = type;
        Date = date;
        Price = price;
        this.remainderBefore = remainderBefore;
        this.remainderAfter = remainderAfter;
    }

    public UserCommandDTO() {
        this.Date=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public UserCommandDTO(String type, int price, int remainderBefore, int remainderAfter) {
        Type = type;
        this.Date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        Price = price;
        this.remainderBefore = remainderBefore;
        this.remainderAfter = remainderAfter;
    }
    public String getType() {
        return Type;
    }

    public String getDate() {
        return Date;
    }

    public int getPrice() {
        return Price;
    }

    public int getRemainderBefore() {
        return remainderBefore;
    }

    public int getRemainderAfter() {
        return remainderAfter;
    }


}
