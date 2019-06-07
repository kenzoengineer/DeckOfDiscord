class Unit extends Card {
    int hp;
    int armor;
    int range;
    int speed;
    int price;
    int attack;
    
    public Unit(String name, String des, int h, int a, int r, int s, int p, int at) {
        super(name, des);
        hp = h;
        armor = a;
        range = r;
        speed = s;
        price = p;
        attack = at;
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPrice() {
        return price;
    }

    public void setPiercing(int price) {
        this.price = price;
    }

    public int getAttack() {
        return attack;
    }

    public void getAttack(int attack) {
        this.attack = attack;
    }
}