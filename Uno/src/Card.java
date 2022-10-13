public class Card {
    //declare the variables
    public String colour;
    public String value;
    public String wildColour = "not set";


    //constructor
    public Card(int colour, String value) {
        //if the colour is 0, set the colour to red, if the colour is 1 set the colour to blue
        //if the colour is 2 set the colour to green, if the colour is 3 set the colour to yellow
        if (colour == 0) {
            this.colour = "Red";
        } else if (colour == 1) {
            this.colour = "Blue";
        } else if (colour == 2) {
            this.colour = "Green";
        } else if (colour == 3) {
            this.colour = "Yellow";
        }else if (colour == 4) {
            this.colour = "Wild";
        }
        this.value = value;
    }

    //toString method
    public String toString() {
        return colour + " " + value;
    }
}
