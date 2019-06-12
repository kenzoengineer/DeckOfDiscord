class Base{
    private int health;
    private int maxH;
    
    public Base() {
        health = 200;
        maxH = this.health;
    }
    
    public void setBaseAge(int age) {
        health *= (age * 2);
        maxH *= (age * 2);
    }
    
    public int getBaseHealth(){
        return health;
    }
    
    public void damageBase(int damage){
        health -= damage;
    }
    
    public int getMaxH() {
        return maxH;
    }
}