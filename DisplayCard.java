import java.awt.Color;
class DisplayCard {
    String picture = "testCard.png";
    Object card;
    int cardType;

    DisplayCard(Entity c) {
        card = c;
        cardType=1;
        if ((int)(Math.random() * 2) == 0) picture = "testCard2.png";
    }
    DisplayCard(Card c) {
        card = c;
        cardType=1;
        if ((int)(Math.random() * 2) == 0) picture = "testCard2.png";
    }
}