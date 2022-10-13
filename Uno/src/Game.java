
import java.util.*;
public class Game {

    public static ArrayList<Player> players = new ArrayList<Player>();
    public static Stack<Card> deck = new Stack<Card>();
    public static Stack<Card> cardsInPlay = new Stack<Card>();
    public static Scanner input = new Scanner(System.in);
    public static boolean uno = false;
    public static boolean drawActive = false;
    public static int direction = -1;
    public Game() {

        initiate();
        while(!uno) {
            run();
        }
    }

    //create an initiate method which adds 4 new players to the list of players
    public void initiate() {
        players.add(new Player("Breeze"));
        players.add(new Player("Creed"));
        players.add(new Player("Chugs"));
        players.add(new Player("Swiss"));

        //create a deck of cards that goes up to 10 for 4 different colours
        //iterate through this loop twice
        for (int i = 0; i < 2; i++) {
            //iterate through this loop 4 times to get the colour
            for (int j = 0; j < 4; j++) {
                //iterate through this loop 10 times to get the value
                for (int k = 0; k < 10; k++) {
                    //add a new card to the deck
                    String value = Integer.toString(k);
                    deck.add(new Card(j, value));
                }
            }
        }
        //if there are two cards in the deck that both are 0 and have the same colour, remove one of them
        for (int i = 0; i < deck.size(); i++) {
            for (int j = 0; j < deck.size(); j++) {
                if (deck.get(i).value.equals("0") && deck.get(j).value.equals("0") && deck.get(i).colour.equals(deck.get(j).colour) && i != j) {
                    deck.remove(i);
                }
            }
        }

        //add skip, reverse and draw two cards to the deck
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                deck.add(new Card(j, "Skip"));
                deck.add(new Card(j, "Reverse"));
                deck.add(new Card(j, "Draw Two"));
            }
        }

        //add wild and wild draw four cards to the deck
        for (int i = 0; i < 4; i++) {
            deck.add(new Card(4, "Change Colour"));
            deck.add(new Card(4, "Draw Four"));
        }

        //shuffle the deck
        Collections.shuffle(deck);
        Collections.shuffle(players);


        //Deal 7 cards to each player
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < players.size(); j++) {
                players.get(j).hand.add(deck.pop());
            }
        }

        //add the first card to the cards in play
        cardsInPlay.add(deck.pop());
        System.out.println("Welcome to UNO!");
        System.out.println("The current order of players is:");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).name);
        }
        System.out.println();
    }
    private static void run(){
        //clear the console


        System.out.println("The current card is " + cardsInPlay.peek() + "\n");

        if(drawActive) {
            //if the current card is a draw two card, draw two cards
            if (cardsInPlay.peek().value.equals("Draw Two")) {
                //send a message to the player saying they have to pick up two cards
                System.out.println(players.get(0).name + " has to pick up two cards");
                for (int i = 0; i < 2; i++) {
                    players.get(0).hand.add(deck.pop());
                }
                Collections.rotate(players, direction);
                drawActive = false;
                return;
            }
            if (cardsInPlay.peek().value.equals("Draw Four")) {
                //send a message to the player saying they have to pick up two cards
                System.out.println(players.get(0).name + " has to pick up four cards");
                for (int i = 0; i < 4; i++) {
                    players.get(0).hand.add(deck.pop());
                }
                Collections.rotate(players, direction);
                drawActive = false;
                return;
            }
        }

        //print out which players have uno
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).uno) {
                System.out.println(players.get(i).name + " has uno!");
            }
        }
        //if the current card is a wild, display what the wild colur is set to
        if (cardsInPlay.peek().colour.equals("Wild")) {
            //if the wild colour is not set, run the wild method
            if (cardsInPlay.peek().wildColour.equals("not set")) {
                wild();
            }
            System.out.println("The wild colour is set to " + cardsInPlay.peek().wildColour + "\n");
        }
        System.out.println("It is " + players.get(0).name + "'s turn\n");
        //if the hand size is not 1 make the uno variable false
        if (players.get(0).hand.size() != 1) {
            players.get(0).uno = false;
        }
        System.out.println("Your hand is:");
        for (int i = 0; i < players.get(0).hand.size(); i++) {
            System.out.println(i + ": " + players.get(0).hand.get(i));
        }
        System.out.println();
        System.out.println("What would you like to play?");

        //get the input from the user
        String selection = input.nextLine();

        //if the selection matches a card in the player's hand, play the card
        if (selection.matches("[0-9]+")) {
            int selectionInt = Integer.parseInt(selection);
            if (selectionInt < players.get(0).hand.size()) {
                if (players.get(0).hand.get(selectionInt).colour.equals(cardsInPlay.peek().colour) || players.get(0).hand.get(selectionInt).value.equals(cardsInPlay.peek().value) || players.get(0).hand.get(selectionInt).colour.equals("Wild") || players.get(0).hand.get(selectionInt).colour.equals(cardsInPlay.peek().wildColour)) {
                    if(players.get(0).hand.get(selectionInt).value.equals("Draw Two") || players.get(0).hand.get(selectionInt).value.equals("Draw Four")){
                        drawActive = true;
                    }

                    cardsInPlay.add(players.get(0).hand.get(selectionInt));
                    players.get(0).hand.remove(selectionInt);

                    //if the card is a wild card, ask the user what colour they want to change it to. Give the player 4 options to choose from
                    if (cardsInPlay.peek().colour.equals("Wild")) {
                        wild();
                    }

                    if (players.get(0).hand.size() == 1) {
                        players.get(0).uno = true;
                        System.out.println("UNO!");
                    }
                    if (players.get(0).hand.size() == 0) {
                        System.out.println(players.get(0).name + " has won!");
                        uno = true;
                    }
                    //if the card is a reverse card, reverse the order of the players
                    if (cardsInPlay.peek().value.equals("Reverse")) {
                        //if the direction is -1, swap it to 1 and vice versa
                        if (direction == -1) {
                            direction = 1;
                        } else {
                            direction = -1;
                        }
                    }

                    //if the card is a skip card, rotate the players
                    if (cardsInPlay.peek().value.equals("Skip")) {
                        Collections.rotate(players, direction);
                    }


                    Collections.rotate(players, direction);
                } else {
                    System.out.println("You cannot play that card");
                }
            } else {
                System.out.println("You do not have that many cards");
            }

        } else if (selection.equals("Draw") || selection.equals("draw")) {
            players.get(0).hand.add(deck.pop());
            //progress to the next player
            Collections.rotate(players, direction);
        } else {
            System.out.println("That is not a valid selection");
        }

    }

    private static void wild(){
        //if the card is a wild card, ask the user what colour they want to change it to. Give the player 4 options to choose from
            System.out.println("What colour would you like to change the wild colour to?");
            System.out.println("1: Red");
            System.out.println("2: Blue");
            System.out.println("3: Green");
            System.out.println("4: Yellow");
            String colourSelection = input.nextLine();
            if (colourSelection.equals("1")) {
                cardsInPlay.peek().wildColour = "Red";
            } else if (colourSelection.equals("2")) {
                cardsInPlay.peek().wildColour = "Blue";
            } else if (colourSelection.equals("3")) {
                cardsInPlay.peek().wildColour = "Green";
            } else if (colourSelection.equals("4")) {
                cardsInPlay.peek().wildColour = "Yellow";
            } else {
                System.out.println("That is not a valid selection");
                wild();
            }
        }
}
