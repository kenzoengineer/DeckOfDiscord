import java.util.ArrayList;
class Deck {
    //create the deck
    ArrayList<Entity> deck;
    
    public Deck() {
        deck = new ArrayList<Entity>();
    }
    
    /**
     * add a card to the bottom of the deck
     * @param c the card to be added
     */
    public void addCard(Entity c) {
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
        ArrayList<Entity> temp = new ArrayList<>(deck);
        deck.clear();
        while (temp.size() > 0) {
            Entity c = temp.remove((int)(Math.random() * temp.size()));
            deck.add(c);
        }
    }
    
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < deck.size(); i++) {
            str += deck.get(i).getName() + " ";
        }
        return str;
    }
}