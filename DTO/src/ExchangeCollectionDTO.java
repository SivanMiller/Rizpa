import java.util.List;


public class ExchangeCollectionDTO {

    private List<ExchangeDTO> lstBuyCommand;
    private List<ExchangeDTO> lstSellCommand;
    private List<ExchangeDTO> lstTransaction;

    public List<ExchangeDTO> getBuyCommand() {
        return lstBuyCommand;
    }

    public List<ExchangeDTO> getSellCommand() {
        return lstSellCommand;
    }

    public List<ExchangeDTO> getTransaction() {
        return lstTransaction;
    }

    @Override
    public String toString() {
        return "ExchangeCollectionDTO{" +
                "BuyCommand=" + lstBuyCommand +
                ", SellCommand=" + lstSellCommand +
                ", Transaction=" + lstTransaction +
                '}';
    }
}
