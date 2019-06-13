import java.util.ArrayList;

/**
 * Holds an arraylist which serves as the deck
 * of all the cards. It is filled in Game.java
 * and is popped from to the hand
 * 
 * @author Souren A., Ken J.
 * @since May 26th, 2019
 * @version 2.58
 */

class Deck {
    //create the deck
    private ArrayList<Card> deck;
    public Deck() {
        deck = new ArrayList<Card>();
    }
    
    /**
     * Add a card to the bottom of the deck
     * @param c the card to be added
     */
    public void addCard(Card c) {
        deck.add(c);
    }
    
    /**
     * Peeks at the top of the deck without removing it
     * @return the card at the top
     */
    public Card peek() {
        return deck.get(0);
    }
    
    /**
     * Looks at the top of the deck and removes that card
     * @return the card that was removed
     */
    public Card pop() {
        return deck.remove(0);
    }
    
    /**
     * Shuffles the deck
     */
    public void shuffle() {
        ArrayList<Card> temp = new ArrayList<>(deck);
        deck.clear();
        while (temp.size() > 0) {
            Card c = temp.remove((int)(Math.random() * temp.size()));
            deck.add(c);
        }
    }
    
    /**
     * clears the whole deck
     */
    public void clear() {
        if (!deck.isEmpty()) {
            deck.clear();
        }
    }
    
    /**
     * Debug method used to print the
     * deck as a string in the console
     * @return a string with the deck
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < deck.size(); i++) {
            str += deck.get(i).getName() + " ";
        }
        return str;
    }
}