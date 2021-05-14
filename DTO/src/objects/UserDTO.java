package objects;

import engine.Holding;

import java.util.List;
import java.util.Map;

public class UserDTO {

    private String Name;
    private int HoldingsTurnover;
    private List<HoldingDTO> Holdings;

    public UserDTO(String name, int holdingsTurnover, List<HoldingDTO> holdings) {
        Name = name;
        HoldingsTurnover = holdingsTurnover;
        Holdings = holdings;
    }

    public String getName() {
        return Name;
    }

    public int getHoldingsTurnover() {
        return HoldingsTurnover;
    }

    public List<HoldingDTO> getHoldings() {
        return Holdings;
    }
}
