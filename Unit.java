class Unit extends Card {
    int hp;
    int armor;
    int range;
    int speed;
    int piercing;
    int attackSpeed;
    
    public Unit(String name, String des, int h, int a, int r, int s, int p, int at) {
        super(name, des);
        hp = h;
        armor = a;
        range = r;
        piercing = p;
        attackSpeed = at;
    }
    
    //upon an age change, a loop with iterate through all cards and change their values
}