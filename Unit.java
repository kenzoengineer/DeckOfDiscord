/**
 * Unit class holds values from the text file
 * in place of a unit on the field.
 * 
 * @author Souren A., Ken J.
 * @since May 28th, 2019
 * @version 9.78
 */

class Unit extends Card {
    private int hp;
    private int armor;
    private int range;
    private int speed;
    private int price;
    private int attack;
    
    public Unit(String name, String des, int h, int a, int r, int s, int p, int at) {
        super(name, des);
        hp = h;
        armor = a;
        range = r;
        speed = s;
        price = p;
        attack = at;
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
     * gets speed
     * @return speed
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
    public void setPiercing(int price) {
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
}