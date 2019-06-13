/**
 * Used to display a card
 * onto the screen with hp
 * @author kenj8
 */

class Entity {
    String name;
    String des;
    int hp;
    int armor;
    int range;
    int speed;
    int price;
    int attack;
    int maxHP;
    boolean stop;
    int x;

    public Entity(String name, String des, int h, int a, int r, int s, int p, int at) {
        this.name = name;
        this.des = des;
        hp = h;
        armor = a;
        range = r;
        speed= s;
        price = p;
        attack = at;
        x = 0;
        stop = false;
        maxHP = h;
    }
    
    /**
     * gets hp
     * @return hp 
     */
    public int getHp() {
        return hp;
    }
    
    /**
     * sets hp
     * @param hp hp value
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    /**
     * gets armor
     * @return armor
     */
    public int getArmor() {
        return armor;
    }
    
    /**
     * sets armor
     * @param armor armor value
     */
    public void setArmor(int armor) {
        this.armor = armor;
    }
    
    /**
     * gets armor
     * @return armor
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * sets speed
     * @param speed speed value
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * gets price
     * @return price
     */
    public int getPrice() {
        return price;
    }
    
    /**
     * sets price
     * @param price price value
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
    /**
     * gets attack
     * @return attack
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * sets attack
     * @param attack attack value
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    /**
     * gets range
     * @return range
     */
    public int getRange() {
        return range;
    }   
    
    /**
     * sets range
     * @param range range value
     */
    public void setRange(int range) {
        this.range = range;
    }
    
    /**
     * gets x
     * @return x
     */
    public int getX() {
        return x;
    }
    
    /**
     * sets x
     * @param x x value
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * gets stop
     * @return stop
     */    
    public boolean getStop() {
        return stop;
    }
    
    /**
     * sets stop
     * @param stop stop value
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
    /**
     * gets maxHP
     * @return maxHP
     */    
    public int getMaxHP() {
        return maxHP;
    }  
    
    /**
     * sets maxHP
     * @param maxHP maxHP value
     */
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }
    

}