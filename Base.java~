class Base extends Game {
  int health = 200;
  int turretDamage=0;
  int turretSpeed=0;
  public void setBaseAge(int age) {
    health=health*age;
  }
  public int getBaseHealth(){
    return health;
  }
  public void damageBase(int damage){
    health=health-damage;
  }
  public int turretShoot(int enemyHealth){
    return enemyHealth=enemyHealth-turretDamage;
  }
  public int turretRefreshRate(){
    return turretSpeed;
  }
  public void setDamage (int turretDamage){
    this.turretDamage=turretDamage;
  }
  public void setSpeed (int turretSpeed){
    this.turretSpeed=turretSpeed;
  }
}