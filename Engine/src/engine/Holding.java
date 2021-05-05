package engine;

import java.util.Objects;

public class Holding {
    private int Quantity;
    private Stock Stock;

    public Holding(int quantity, engine.Stock stock) {
        Quantity = quantity;
        Stock = stock;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public engine.Stock getStock() {
        return Stock;
    }

    public void setStock(engine.Stock stock) {
        Stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Holding)) return false;
        Holding holding = (Holding) o;
        return getQuantity() == holding.getQuantity() && Objects.equals(getStock(), holding.getStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getStock());
    }

    @Override
    public String toString() {
        return "Holding{" +
                "Quantity=" + Quantity +
                ", Stock=" + Stock +
                '}';
    }
}
