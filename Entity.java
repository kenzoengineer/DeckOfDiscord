import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
class Entity {
  String name;
  String des;
  int hp;
  int armor;
  int range;
  int speed;
  int piercing;
  int attack;
  boolean stop;
  
  int x;

    public Entity(String name, String des, int h, int a, int r, int s, int p, int at) {
        this.name = name;
        this.des = des;
        hp = h;
        armor = a;
        range = r;
        speed= s;
        piercing = p;
        attack = at;
        x = 0;
        stop = false;
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

    public int getAttack() {
        return attack;
    }

    public void setAttackSpeed(int attack) {
        this.attack = attack;
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
    
    public boolean getStop() {
        return stop;
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
}