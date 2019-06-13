/**
 * An object which holds the health
 * of a base (either enemy or ally)
 * 
 * @author Souren A., Ken J.
 * @since June 1st, 2019
 * @version 2.66
 */

class Base{
    private int health;
    private int maxH;
    
    public Base() {
        health = 200;
        maxH = this.health;
    }
    
    /**
     * Sets the health based on age
     * @param age the current age
     */
    public void setBaseAge(int age) {
        health *= (age * 2);
        maxH *= (age * 2);
    }
    
    /**
     * Gets the current health of the base
     * @return health 
     */
    public int getBaseHealth(){
        return health;
    }
    
    /**
     * Damages the base. Can also be used for
     * healing if a negative int is used
     * @param damage amount to be damaged
     */
    public void damageBase(int damage){
        health -= damage;
    }
    
    /**
     * Returns the max health of the base
     * @return max health
     */
    public int getMaxH() {
        return maxH;
    }
}