import java.awt.Color;
class DisplayCard {
    String picture = "testCard.png";
    Color color;
    Card card;

    DisplayCard(Card c) {
        color = new Color(50,50,50);
        card = c;
    }
}