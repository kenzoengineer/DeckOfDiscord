class Persia extends Empire {  
  public void effect() {
    Deck deck = new Deck();
    Game game = new Game();
    for (int i=0; i<3; i++){
      game.hand.add(deck.pop());
    }
  }
}