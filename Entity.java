import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
class Entity {
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
  int x;
  File myFile;
  Scanner input;
  String [] cardUse= new String [30]; 
  //ArrayList<Entity> deck = new ArrayList<>();
  
  public Entity(String name, String des, int h, int a, int r, int s, int p, int at) {
    this.name = name;
    this.des = des;
    hp = h;
    armor = a;
    range = r;
    speed= s;
    piercing = p;
    attackSpeed = at;
    int x = 0;
  }
  public void getDeck(){
    try{
      myFile = new File ("persia.txt"); 
      input = new Scanner(myFile);
    } catch (Exception e) {}
    while(input.hasNext()){
      String a= input.next();
      System.out.print(a);
      cardUse[x]=a;
      x++;
    }
  }
  public void getEntity(){
    try{
      myFile = new File ("persiaCards/"+cardUse[0]+".txt"); 
      input = new Scanner(myFile);
    } catch (Exception e) {}
    for (int i=0; i<8; i++){
      if (i==0){
        name=input.next();
        System.out.println(name);
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
    //Game.deck.add(new Entity(name,des,hp,armor,range,speed,piercing,attackSpeed));
    input.close();
  }
  //public int sendEntity(){
  //}
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPiercing() {
        return piercing;
    }

    public void setPiercing(int piercing) {
        this.piercing = piercing;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
  
}