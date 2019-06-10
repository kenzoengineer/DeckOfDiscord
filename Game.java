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
    boolean left;
    boolean right;
    int dragCard;
    int empireNumber;
    int mana;
    int ageMultiplier;
    String empireName;
    String zoom;
    String imageBack;
    Color dark;
    Image background;
    Deck deck;
    Image zoomedImage;
    ArrayList<DisplayCard> hand;
    ArrayList<Entity> units;
    ArrayList<Entity> enemy;
    Date startDate;
    Date endDate;
    Timer clock;
    Game(int e) {
        empireNumber = e;
        imageBack="forest.jpg";
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
        
        placedCount = 0;
        mana = 2000;
        ageMultiplier=0;
        zoom = "";
        left = false;
        right = false;
        background = Toolkit.getDefaultToolkit().getImage(imageBack);
        deck = new Deck();
        dark = new Color(0,0,0,0);
        hand = new ArrayList<>();
        units = new ArrayList<>();
        enemy = new ArrayList<>();
        startDate = new Date();
        endDate = new Date();
        clock = new Timer();
        enemy.add(new Entity("Buffboy","tank.png",2,3,1,1,20,1));
        enemy.add(new Entity("Buffboy","tank.png",2,3,1,1,20,1));
        enemy.add(new Entity("Buffboy","tank.png",2,3,1,1,20,1));
        enemy.get(0).setX(2732);
        enemy.get(1).setX(2932);
        enemy.get(2).setX(3332);
        
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        JPanel whole = new JPanel();
        whole.setLayout(new BoxLayout(whole, BoxLayout.X_AXIS));
        GamePanel gp = new GamePanel();
        gp.setPreferredSize(new Dimension(1000 + 80,768-500));
        
        JPanel left = new ScrollComponent();
        //left.setBorder(BorderFactory.createLineBorder(Color.black));
        left.setPreferredSize(new Dimension(183-40,768-500));
                
        JPanel right = new ScrollComponent();
        //right.setBorder(BorderFactory.createLineBorder(Color.black));
        right.setPreferredSize(new Dimension(183-40,768-500));
        
        whole.add(left);
        whole.add(gp);
        whole.add(right);
        add(whole);
        setVisible(true);
        
        initGame();
    }
    
    public void initGame() {
        //load in the card list (1,1,1,2,2,2,2,2,3,4,4)
        File myFile;
        Scanner cardIn, cardLoader;
        try{
            myFile = new File(empireNumber + ".txt"); 
            cardIn = new Scanner(myFile);
            while (cardIn.hasNext()) {
                int card = cardIn.nextInt();
                card= card + ageMultiplier;
                //System.out.print(card);
                cardLoader = new Scanner(new File(empireName + "Cards/" + card+ ".txt"));
                deck.addCard(new Unit(cardLoader.next(),cardLoader.next(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt(),
                cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt()));
            }
            deck.shuffle();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
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
            if (age==2){
                imageBack="ageBackground.jpg";
                ageMultiplier=4;
                if (deck.deck.size() >0){
                    deck.deck.clear();
                }
                initGame();
            }else if (age==3){
                imageBack="ageBackground3.jpg";
                ageMultiplier=8;
                if (deck.deck.size() >0){
                    deck.deck.clear();
                }
                initGame();
            }else if (age==4){
                imageBack="ageBackground4.jpg";
                ageMultiplier=12;
                if (deck.deck.size() >0){
                    deck.deck.clear();
                }
                initGame();
            }else if (age==5){
                imageBack="ageBackground5.jpg";
            }
        }
        
        public void attack() {
            enemy.get(0).setHp(enemy.get(0).getHp() - units.get(0).getAttack());
            units.get(0).setHp(units.get(0).getHp() - enemy.get(0).getAttack());
        }
        public void drawAll(Graphics g) {
            for (int i = 0; i < enemy.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + enemy.get(i).des.substring(0,enemy.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + (enemy.get(i).getX()), 400, null);
                g.setColor(Color.RED);
                g.fillOval(x + enemy.get(i).getX() + 73, 350, 30, 30);
            }
            for (int i = 0; i < units.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + units.get(i).des.substring(0,units.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + (units.get(i).getX()), 400, null);
                g.setColor(Color.GREEN);
                g.fillOval(x + units.get(i).getX() + 73, 350, 30, 30);
            }
            for (int i = 0; i < hand.size() && i < 5; i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + hand.get(i).picture);
                if (!dragging || i != dragCard) {
                    g.drawImage(img, 25 + (i * 220), 500, null);
                } else {
                    g.drawImage(img, dx - offsetX, dy - offsetY, null);
                }
            }
            
            //drawing mana
            Graphics2D g2d = (Graphics2D) g;
            g.setColor(Color.BLACK);
            g.fillRect(853,33,195,15);
            g.setColor(Color.BLUE);
            g.fillRect(853,33,(int)(195 * (mana/2000.0)),15);
            g.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRect(850,30,200,20);
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2d.drawString("Mana: " + mana, 740, 47);
        }
        double timeSum = 0;
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, x,0,null);
            if (left && x < 0) x++;
            if (right && x > -1485) x--;
            g.setColor(dark);
            g.fillRect(0, 0, 1366, 768);
            if (units.size() > 0 && enemy.size() > 0 && (enemy.get(0).getX() - units.get(0).getX()) < 200) {
                units.get(0).setStop(true);
                enemy.get(0).setStop(true);
            } else {
                if (units.size() > 0) units.get(0).setStop(false);
                if (enemy.size() > 0) enemy.get(0).setStop(false);
            }
            
            drawAll(g);
            
            clock.update();
            double elapsed = clock.getElapsedTime();
            timeSum += elapsed;
            if (timeSum > 0.01) {
                for (int i = 0; i < enemy.size(); i++) {
                     if (!enemy.get(i).getStop()) enemy.get(i).setX(enemy.get(i).getX() - enemy.get(i).getSpeed());
                     if ((i < enemy.size() - 1) && enemy.get(i + 1).getX() - enemy.get(i).getX() < 200) {
                        enemy.get(i + 1).setX(enemy.get(i).getX() + 200);
                     }
                 }

                for (int i = 0; i < units.size(); i++) {
                    if (!units.get(i).getStop()) units.get(i).setX(units.get(i).getX() + units.get(i).getSpeed());
                    if ((i < units.size() - 1) && units.get(i + 1).getX() - units.get(i).getX() > -200) {
                        units.get(i + 1).setX(units.get(i).getX() - 200);
                    }
                }
                if (units.size() > 0 && enemy.size() > 0 && units.get(0).getStop() && enemy.get(0).getStop()) {
                //System.out.println((endDate.getTime() - startDate.getTime())/1000);
                endDate = new Date();
                if (((endDate.getTime() - startDate.getTime())/1000)> 1) {
                    attack();
                    System.out.println("attack!");
                    startDate = new Date();
                }
            }
                timeSum = 0;
            }
            
            if (!zoom.equals("")) {
                g.drawImage(zoomedImage, 400, 100, null);
            }
            if (placedCount % 5 == 0) {
                changeAge();
            }
            if (units.size() > 0 && units.get(0).getHp() <= 0) units.remove(0);
            if (enemy.size() > 0 && enemy.get(0).getHp() <= 0) enemy.remove(0);
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
                addHand();
            }
        }
        
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                Point p = MouseInfo.getPointerInfo().getLocation();
                double px = p.getX();
                double py = p.getY();
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
                    zoomedImage = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + zoom.substring(0,zoom.indexOf(".")) + "x.png");
                    done = true;
                }
            }
            if (e.getKeyChar() == 'd') {
                units.get(0).setStop(true);
            }
            if (e.getKeyChar() == 'l') {
                units.remove(0);
            }
        }
        
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                dark = new Color(0,0,0,0);
                zoom = "";
                done = false;
            }
            if (e.getKeyChar() == 'd') {
                units.get(0).setStop(false);
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
                placedCount++;
              }dragging = false;
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
                }
            }
        }
        
        public void mouseDragged (MouseEvent e) {
            dx = e.getX();
            dy = e.getY();       
        }
        
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
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
        }
        
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}