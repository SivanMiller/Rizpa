package engine;

import com.sun.org.apache.xpath.internal.operations.Or;
import objects.ExchangeDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {

    static int order = 1;
    private int nPrice;
    private int nQuantity;
    private String sDate;
    private int nTurnover;
    protected int Order;

    public Transaction(){
        this.sDate=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
    public Transaction(int nPrice, int nQuantity, String sDate, int nTurnover, int Order) {
        this.nPrice = nPrice;
        this.nQuantity = nQuantity;
        this.nTurnover = nTurnover;
        this.sDate=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        this.Order = Order;
     }

    public int getPrice() {
        return nPrice;
    }

    public void setPrice(int nPrice) {
        this.nPrice = nPrice;
    }

    public int getQuantity() {
        return nQuantity;
    }

    public void setQuantity(int nQuantity) {
        this.nQuantity = nQuantity;
    }

    public String getDate() {
        return sDate;
    }

    public int getTurnover() {
        return nTurnover;
    }

    public void setTurnover(int nTurnover) {
        this.nTurnover = nTurnover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return nPrice == that.nPrice && nQuantity == that.nQuantity && nTurnover == that.nTurnover && sDate.equals(that.sDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nPrice, nQuantity, sDate, nTurnover);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                " sDate='" + sDate + '\'' +
                ", nQuantity=" + nQuantity +
                ", nPrice=" + nPrice +
                ", nTurnover=" + nTurnover +
                ", Order=" + Order +
                '}';
    }

    public ExchangeDTO convertToDTO()
    {
        return new ExchangeDTO(this.nPrice, this.nQuantity, this.sDate, this.nTurnover);
    }
}
