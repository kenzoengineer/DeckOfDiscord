//s draw card
//z zoom in
//l pop first unit
//p end game

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

class Timer {
    long elapsedTime;
    long lastCheck;

    public Timer() {
        lastCheck = System.nanoTime();
        elapsedTime = 0;
    }

    public void update() {
        long currentT = System.nanoTime();
        elapsedTime = currentT - lastCheck;
        lastCheck = currentT;
    }

    public double getElapsedTime() {
        return elapsedTime/1.0E9;
    }
}

class Game extends JFrame{
    int placedCount;
    int x;
    int age;
    int dx;
    int dy;
    int offsetX;
    int offsetY;
    double cdSum;
    boolean left;
    boolean right;
    boolean hideCards;
    boolean cooldown;
    int dragCard;
    int empireNumber;
    int mana;
    int ageMultiplier;
    String empireName;
    String zoom;
    String imageBack;
    String alertText;
    Color dark;
    Image background;
    Image zoomedImage;
    Image special;
    Image overlay;
    Deck deck;
    Base base;
    ArrayList<DisplayCard> hand;
    ArrayList<Entity> units;
    ArrayList<Entity> enemy;
    Date startDate;
    Date endDate;
    Timer clock;
    Timer cd;
    Base playerB;
    Base enemyB;
    
    Game(int e) {
        empireNumber = e;
        imageBack="ageBackground1.jpg";
        switch (e) {
            case 1:
                empireName = "persia";
                break;
            case 2:
                empireName = "china";
                break;
            case 3:
                empireName = "mexico";
                break;
            default:
                empireName = "mars";
                break;
        }
        
        age = 1;
        placedCount = 0;
        mana = 500;
        ageMultiplier=0;
        cdSum = 0;
        zoom = "";
        alertText = "";
        left = false;
        right = false;
        hideCards = false;
        cooldown = false;
        background = Toolkit.getDefaultToolkit().getImage(imageBack);
        special = Toolkit.getDefaultToolkit().getImage(empireNumber + "Effect.png");
        overlay = Toolkit.getDefaultToolkit().getImage("overlay.png");
        deck = new Deck();
        dark = new Color(0,0,0,0);
        hand = new ArrayList<DisplayCard>();
        units = new ArrayList<Entity>();
        enemy = new ArrayList<Entity>();
        startDate = new Date();
        endDate = new Date();
        clock = new Timer();
        cd = new Timer();
        playerB = new Base();
        enemyB = new Base();
        
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        
        JPanel whole = new JPanel();
        whole.setLayout(new BoxLayout(whole, BoxLayout.X_AXIS));
        GamePanel gp = new GamePanel();
        gp.setPreferredSize(new Dimension(1000 + 80,768-500));
        
        JPanel left = new ScrollComponent();
        left.setPreferredSize(new Dimension(183-40,768-500));
                
        JPanel right = new ScrollComponent();
        right.setPreferredSize(new Dimension(183-40,768-500));
        
        whole.add(left);
        whole.add(gp);
        whole.add(right);
        add(whole);
        setVisible(true);
        
        initGame();
        for (int i = 0; i < 5; i++) {
            addHand();
        }
    }
    
    public void initGame() {
        //load in the card list (1,1,1,2,2,2,2,2,3,4,4)
        File myFile;
        Scanner cardIn, cardLoader;
        try{
            myFile = new File("cards.txt"); 
            cardIn = new Scanner(myFile);
            while (cardIn.hasNext()) {
                int card = cardIn.nextInt();
                card += ageMultiplier;
                cardLoader = new Scanner(new File("persiaCards/" + card + ".txt"));
                deck.addCard(new Unit(cardLoader.next(),cardLoader.next(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt()));
            }
            deck.shuffle();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
    
    public void addHand() {
        hand.add(new DisplayCard((Unit)deck.pop()));
    }
    
    class GamePanel extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
        GamePanel() {
            this.addKeyListener(this);
            this.addMouseListener(this);
            this.addMouseMotionListener(this);
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(false);
        }
        
        public Entity cardToEntity(Unit c) {
            String name = c.getName();
            String des = c.getDes();
            int hp = c.getHp();
            int armor = c.getArmor();
            int range = c.getRange();
            int speed = c.getSpeed();
            int price = c.getPrice();
            int attack = c.getAttack();
            return new Entity(name,des,hp,armor,range,speed,price,attack);
        }
        public void changeAge() {
            if (placedCount==10){
                age = 2;
                background = Toolkit.getDefaultToolkit().getImage(imageBack);
            }else if(placedCount==20){
                age = 3;
                background = Toolkit.getDefaultToolkit().getImage(imageBack);
            }else if(placedCount==30){
                age = 4;
                background = Toolkit.getDefaultToolkit().getImage(imageBack);
            }else if(placedCount==40){
                age = 5;
                background = Toolkit.getDefaultToolkit().getImage(imageBack);
            }
            
            imageBack = "ageBackground" + age + ".jpg";
            ageMultiplier = 4 * (age - 1);
            deck.clear();
            initGame();
        }
        
        public void newEnemy() {
            int card = (int)(Math.random() * 4 + 1);
            try {
                Scanner cardLoader = new Scanner(new File("persiaCards/" + (card + ageMultiplier) + ".txt"));
                enemy.add(cardToEntity(new Unit(cardLoader.next(),cardLoader.next(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt())));
                enemy.get(enemy.size() - 1).setX(2732);
            } catch (IOException e) {
                System.out.println("File not found enemy" + ": " + "persiaCards/" + (card + ageMultiplier) + ".txt");
            }
        }
        
        //0 1 true would be unit ranged
        public void attack(int def, int att, String target) {
            if (target.equals("enemy")) {
                enemy.get(def).setHp(enemy.get(def).getHp() - units.get(att).getAttack());
            } else if (target.equals("unit")) {
                units.get(def).setHp(units.get(def).getHp() - enemy.get(att).getAttack());
            } else if (target.equals("playerBase")) {
                playerB.damageBase(enemy.get(0).getAttack());
                if (enemy.size() > 1 && enemy.get(1).getRange() == 2) {
                    playerB.damageBase(enemy.get(1).getAttack());
                }
            } else {
                enemyB.damageBase(units.get(0).getAttack());
                if (units.size() > 1 && units.get(1).getRange() == 2) {
                    enemyB.damageBase(units.get(1).getAttack());
                }
            }
        }
        
        double cdL = 0;
        public void redrawAll(Graphics g) {
            for (int i = 0; i < enemy.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage("persiaCards/" + enemy.get(i).des.substring(0,enemy.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + (enemy.get(i).getX()), 400, null);
                g.setColor(Color.RED);
                g.fillOval(x + enemy.get(i).getX() + 73, 350, 30, 30);
                
                g.setColor(Color.RED);
                g.fillRect(x + enemy.get(i).getX(), 630, 145, 15);
                g.setColor(Color.GREEN);
                g.fillRect(x + enemy.get(i).getX(), 630, (int)(145 * (enemy.get(i).getHp()/(enemy.get(i).getMaxHP()*1.0))), 15);
            }
            for (int i = 0; i < units.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage("persiaCards/" + units.get(i).des.substring(0,units.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + (units.get(i).getX()), 400, null);
                g.setColor(Color.GREEN);
                g.fillOval(x + units.get(i).getX() + 73, 350, 30, 30);
                
                g.setColor(Color.RED);
                g.fillRect(x + units.get(i).getX(), 630, 145, 15);
                g.setColor(Color.GREEN);
                g.fillRect(x + units.get(i).getX(), 630, (int)(145 * (units.get(i).getHp()/(units.get(i).getMaxHP()*1.0))), 15);
            }
            if (!hideCards) {
                for (int i = 0; i < hand.size() && i < 5; i++) {
                    Image img = Toolkit.getDefaultToolkit().getImage("persiaCards/" + hand.get(i).picture);
                    if (!dragging || i != dragCard) {
                        g.drawImage(img, 25 + (i * 220), 500, null);
                    } else {
                        g.drawImage(img, dx - offsetX, dy - offsetY, null);
                    }
                }
            }
            
            g.setColor(new Color(200,200,200));
            g.fillRect(0,0,1366,50);
            
            //drawing mana
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.BLACK);
            g.fillRect(853,18,195,15);
            g.setColor(Color.BLUE);
            g.fillRect(853,18,(int)(195 * (mana/2000.0)),15);
            g.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(850,15,200,20);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2d.drawString("Mana: " + mana, 740, 32);
            
            //drawing alert text
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            g2d.drawString(alertText,360,32);
            
            //drawing special
            g.drawImage(special,0,0,null);
            g.setColor(new Color(0,0,0,50));
            g.fillRect(0,0,(int)(100.0 * cdL),100);
            g.drawImage(overlay,0,0,null);
            
            if (cooldown) {
                cd.update();
                if (cdSum < 30) {
                    cdSum += cd.getElapsedTime();
                    //percentage of cooldown
                    cdL = (1.0 - (cdSum/30.0));
                } else {
                    cooldown = false;
                }
            }
        }
        
        
        double timeSum = 0;
        double enemySum = 0;
        Timer enemyTimer = new Timer();
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, x,0,null);
            if (left && x < 0) { //scrolling left
                x++;
            }
            if (right && x > -1485) { //scrolling right
                x--;
            }
            g.setColor(dark);
            g.fillRect(0, 0, 1366, 768);
            
            if (units.size() > 0 && enemy.size() > 0 && (enemy.get(0).getX() - units.get(0).getX()) < 200){ //if units and enemies collide
                units.get(0).setStop(true);
                enemy.get(0).setStop(true);
            } else if (units.size() > 0 && units.get(0).getX() > 2200) { //units stop at enemy base
                units.get(0).setStop(true);
            } else if (enemy.size() > 0 && enemy.get(0).getX() < 200) { //enemies stop at player base
                enemy.get(0).setStop(true);
            } else { //in all other cases units should move
                if (units.size() > 0) {
                    units.get(0).setStop(false);
                }
                if (enemy.size() > 0) {
                    enemy.get(0).setStop(false);
                }
            }
            
            
            redrawAll(g);
            
            clock.update();
            double elapsed = clock.getElapsedTime();
            timeSum += elapsed;
            if (timeSum > 0.01) {
                for (int i = 0; i < enemy.size(); i++) {
                     if (!enemy.get(i).getStop()) {
                         enemy.get(i).setX(enemy.get(i).getX() - enemy.get(i).getSpeed());
                     }
                     if ((i < enemy.size() - 1) && enemy.get(i + 1).getX() - enemy.get(i).getX() < 200) {
                        enemy.get(i + 1).setX(enemy.get(i).getX() + 200);
                     }
                 }

                for (int i = 0; i < units.size(); i++) {
                    if (!units.get(i).getStop()) {
                        units.get(i).setX(units.get(i).getX() + units.get(i).getSpeed());
                    }
                    if ((i < units.size() - 1) && units.get(i + 1).getX() - units.get(i).getX() > -200) {
                        units.get(i + 1).setX(units.get(i).getX() - 200);
                    }
                }
                if ((units.size() > 0 && units.get(0).getStop()) || (enemy.size() > 0 && enemy.get(0).getStop())) {
                    endDate = new Date();
                    if (((endDate.getTime() - startDate.getTime())/1000)> 1) {
                        if (units.size() > 0 && enemy.size() > 0 && units.get(0).getStop() && enemy.get(0).getStop()) { //two units must be attacking each other
                            System.out.println("Melee attack.");
                            attack(0,0,"enemy");
                            attack(0,0,"unit");
                            if (units.size() > 1 && units.get(1).getRange() == 2) {
                                System.out.println("Unit ranged attack.");
                                attack(0,1,"enemy");
                            }
                            if (enemy.size() > 1 && enemy.get(1).getRange() == 2) {
                                System.out.println("Enemy ranged attack.");
                                attack(0,1,"unit");
                            }
                        } else if (units.size() > 0 && units.get(0).getStop()) { //unit must be attacking base
                            attack(0,0,"enemyBase");
                            System.out.println(enemyB.getBaseHealth());
                        } else if (enemy.size() > 0 && enemy.get(0).getStop()) { //enemy must be attacking base
                            attack(0,0,"playerBase");
                            System.out.println(playerB.getBaseHealth());
                        }
                        startDate = new Date();
                    }
                }
                timeSum = 0;
            }
            //enemies will only spawn after the first card is placed
            if (placedCount > 0) {
                enemyTimer.update();
                double enemyLapsed = enemyTimer.getElapsedTime();
                enemySum += enemyLapsed;
                if (enemySum > 4.0) {
                    //1 in 1000 chance 
                    if ((int)(Math.random() * 1000) == 5) {
                        if (enemy.size() < 8) { //enemy limit
                            newEnemy();
                        }
                        enemySum = 0;
                    }
                }
            }
            if (!zoom.equals("")) {
                g.drawImage(zoomedImage, 400, 100, null);
            }
            if (placedCount % 10 == 0) {
                changeAge();
            }
            
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getHp() <= 0) {
                    units.remove(i);
                }
            }
            for (int i = 0; i < enemy.size(); i++) {
                if (enemy.get(i).getHp() <= 0) {
                    mana += enemy.get(i).getPiercing() * 1.2;
                     enemy.remove(i);
                }
            }
            repaint();
        }
        
        boolean done = false;
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'p') {
                System.exit(0);
            }
            if (e.getKeyChar() == 'a') {
                initGame();
            }
            if (e.getKeyChar() == 's') {
                System.out.println(units.get(0).getX());
            }
            if (e.getKeyChar() == 'y') {
                //newEnemy();
                units.get(1).setHp(units.get(1).getHp() - 2);
            }
        }
        
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                Point p = MouseInfo.getPointerInfo().getLocation();
                double px = p.getX();
                double py = p.getY();
                alertText = "";
                if (px > 169 && px < 318 && py > 525 && hand.size() > 0) {
                    dark = new Color(0,0,0,50);
                    zoom = hand.get(0).picture;                  
                } else if (px > 389 && px < 535 && py > 525 && hand.size() > 1) {
                    dark = new Color(0,0,0,50);
                    zoom = hand.get(1).picture;                    
                }  else if (px > 609 && px < 755 && py > 525 && hand.size() > 2) {
                    dark = new Color(0,0,0,50);
                    zoom = hand.get(2).picture;                   
                }  else if (px > 829 && px < 975 && py > 525 && hand.size() > 3) {
                    dark = new Color(0,0,0,50);
                    zoom = hand.get(3).picture;
                }  else if (px > 1049 && px < 1195 && py > 525 && hand.size() > 4) {
                    dark = new Color(0,0,0,50);
                    zoom = hand.get(4).picture;
                }
                if (!done && !zoom.equals("")) {
                    System.out.println(zoom);
                    zoomedImage = Toolkit.getDefaultToolkit().getImage("persiaCards/" + zoom.substring(0,zoom.indexOf(".")) + "x.png");
                    done = true;
                }
            }
            if (e.getKeyChar() == 'l') {
                units.remove(0);
            }
            if (e.getKeyChar() == 'h') {
                hideCards = true;
                alertText = "";
            }
        }
        
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                dark = new Color(0,0,0,0);
                zoom = "";
                done = false;
            }
            if (e.getKeyChar() == 'h') {
                hideCards = false;
            }
        }
        
        boolean dragging = false;
        public void mouseReleased(MouseEvent e) {
            if (e.getY() < 480 && dragging){
                Unit look = hand.get(dragCard).getCard();
                if (look.getPrice()<=mana){
                    mana=mana-look.getPrice();
                    Unit c = hand.remove(dragCard).getCard();
                    units.add(cardToEntity(c));
                    addHand();
                    placedCount++;
                } else {
                    alertText = "Not enough mana";
                }
            }
            dragging = false;
        }
        
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed. x: " +  e.getX() + " y: " + e.getY());
            int px = e.getX();
            int py = e.getY();
            for (int i = 0; i < 5; i++) {
                if (px > 25 + (i * 220) && px < 170 + (i * 220) && py > 500 && hand.size() > i) {
                    dx = e.getX();
                    dy = e.getY();
                    offsetX = dx - (25 + (i * 220));
                    offsetY = dy - 500;
                    dragging = true;
                    dragCard = i;
                    alertText = "";
                }
            }
        }
        
        public void mouseDragged (MouseEvent e) {
            dx = e.getX();
            dy = e.getY();       
        }
        
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {
            double px = e.getX();
            double py = e.getY();
            if (Math.sqrt(Math.pow(50-px,2) + Math.pow(50-py,2)) < 50 && !cooldown) {
              //ABILITY GOES HERE
              if (empireNumber==1){
                base.damageBase(-1000);
              }else if (empireNumber==2){
                mana+=500;
              }else if (empireNumber==3){
                units.get(0).setSpeed(100);
              }else if (empireNumber==4){
                enemy.remove(0);
              }
              cooldown = true;
              cdSum = 0;
              cd.update();
            }
        }
        public void mouseMoved (MouseEvent e) {}
    }
    
    class ScrollComponent extends JPanel implements MouseListener {
        ScrollComponent() {
            this.addMouseListener(this);
            //change this to a picture later
            setBackground(new Color(0,0,0,255));
            setVisible(true);
        }
        
        public void mouseExited(MouseEvent e) {
            left = false;
            right = false;
        }
        
        public void mouseEntered(MouseEvent e) {
            if (e.getXOnScreen() < 500) {
                left = true;
            } else {
                right = true;
            }
            alertText = "";
        }
        
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}