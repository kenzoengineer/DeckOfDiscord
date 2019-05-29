import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
class Entity extends Card {
  int hp;
  int armor;
  int range;
  int speed;
  int piercing;
  int attackSpeed;
  String des;
  String name;
  String empireDeck;
  int whichEmpire;
  File myFile;
  Scanner input;
  String [] cardUse= new String [30]; 
  ArrayList<Entity> deck = new ArrayList<>();
  
  public Entity(String name, String des, int h, int a, int r, int s, int p, int at) {
    super(name, des);
    hp = h;
    armor = a;
    range = r;
    speed= s;
    piercing = p;
    attackSpeed = at;
  }
  public void getDeck(){
    try{
      myFile = new File (empireDeck); 
      input = new Scanner(myFile);
    } catch (Exception e) {}
    for (int i=0; i<20; i++){
       //deck.add(new Entity(input.next()));
      cardUse[i]=input.next();
    }
  }
   public void getCard(){
    try{
      myFile = new File ("perisaCards/"+cardUse[1]+".txt"); 
      input = new Scanner(myFile);
    } catch (Exception e) {}
    for (int i=0; i<8; i++){
      if (i==0){
        name=input.next();
      }else if(i==1){
        des=input.next();
      }else if(i==2){
        hp=Integer.parseInt(input.next());
      }else if(i==3){
        armor=Integer.parseInt(input.next());
      }else if(i==4){
        range=Integer.parseInt(input.next());
      }else if(i==5){
        speed=Integer.parseInt(input.next());
      }else if(i==6){
        piercing=Integer.parseInt(input.next());
      }else if(i==7){
        attackSpeed=Integer.parseInt(input.next());
      }
    }
    deck.add(new Entity(name,des,hp,armor,range,speed,piercing,attackSpeed));
  }
  public void checkDeck(int whichEmpire){
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
}