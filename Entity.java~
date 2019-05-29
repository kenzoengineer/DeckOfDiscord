import java.io.File;
import java.util.Scanner;
class Entity extends Card {
    int hp;
    int armor;
    int range;
    int speed;
    int piercing;
    int attackSpeed;
    String empireDeck;
    int whichEmpire;
    String [] cardUse= new String [30]; 
    
    
    public Entity(String name, String des, int h, int a, int r, int s, int p, int at) {
        super(name, des);
        hp = h;
        armor = a;
        range = r;
        piercing = p;
        attackSpeed = at;
    }
    public void getDeck(){
      File myFile = new File (empireDeck);
      Scanner input;
      try {
        input = new Scanner(myFile);
        for (int i=0; i<20; i++){
          cardUse[i]=input.next();
        }
      } catch (Exception e) {}
      System.out.print(cardUse[3]);
    }
    public void checkEmpire(){
      Empire empire= new Empire();
      whichEmpire=empire.empireCheck();
      if (whichEmpire==1){
        empireDeck=("persia.txt");
      }else if (whichEmpire==2){
        empireDeck=("china.txt");
      }else if (whichEmpire==3){
        empireDeck=("mexico.txt");
      }else if (whichEmpire==4){
        empireDeck=("mars.txt");
      }
    }
    
    //upon an age change, a loop with iterate through all cards and change their values
}