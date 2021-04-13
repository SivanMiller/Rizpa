package engine;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String Name;
    private List<Holding> Holdings;

    public User() {
        Holdings = new ArrayList<>();
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Holding> getHoldings() {
        return Holdings;
    }

    public void setHoldings(List<Holding> holdings) {
        Holdings = holdings;
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Holdings=" + Holdings.toString() +
                '}';
    }
}
