class Unit extends Card() {
    int hp;
    int armor;
    int range;
    int speed;
    int piercing;
    int attackSpeed;
    
    public Unit(int h, int a, int r, int s, int p, int a) {
        hp = h;
        armor = a;
        range = r;
        piercing = p;
        attackSpeed = a;
    }
    
    //upon an age change, a loop with iterate through all cards and change their values
}