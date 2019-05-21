import java.util.ArrayList;
class Deck {
    //create the deck
    ArrayList<Card> deck;
    
    public Deck() {
        deck = new ArrayList<Card>();
    }
    
    /**
     * add a card to the bottom of the deck
     * @param c the card to be added
     */
    public void addCard(Card c) {
        deck.add(c);
    }
    
    /**
     * peeks at the top of the deck without removing it
     * @return the card at the top
     */
    public Card peek() {
        return deck.get(0);
    }
    
    /**
     * looks at the top of the deck and removes that card
     * @return the card that was removed
     */
    public Card pop() {
        return deck.remove(0);
    }
    
    public void shuffle() {
        ArrayList<Card> temp = deck;
    }
}