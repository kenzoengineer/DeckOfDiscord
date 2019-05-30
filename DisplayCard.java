import java.awt.Color;
class DisplayCard {
    String picture = "testCard.png";
    Unit card;
    int cardType;

    DisplayCard(Unit c) {
        card = c;
        cardType=1;
        picture = card.getDes();
    }
    
    public Unit getCard() {
        return card;
    }
}