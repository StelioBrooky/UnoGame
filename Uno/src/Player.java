import java.util.*;
public class Player {
    //declare the variables
    public String name;
    public boolean uno = false;
    public ArrayList<Card> hand = new ArrayList<Card>();

    //constructor
    public Player(String name) {
        this.name = name;
    }
}
