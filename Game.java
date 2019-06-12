import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * The main program, holds everything that the game depends on and draws
 * it to the screen. It involves holding the deck, showing units, cards,
 * the GUI and backgrounds. It also handles all key listeners and mouse
 * listeners. Music selected in empire.java is also played.
 * @author Souren A., Ken J.
 * @since May 27th, 2019
 * @version 2.96
 */

//timer class to keep track of time
class Timer {
    long elapsedTime;
    long lastCheck;
    
    public Timer() {
        lastCheck = System.nanoTime();
        elapsedTime = 0;
    }
    
    /**
     * Updates the timer
     */
    public void update() {
        long currentT = System.nanoTime();
        elapsedTime = currentT - lastCheck;
        lastCheck = currentT;
    }
    
    /**
     * Checks how long since the last update
     * @return a double representing elapsed time in seconds
     */
    public double getElapsedTime() {
        return elapsedTime/1.0E9;
    }
}

//main class
class Game extends JFrame{
    private int placedCount;
    private int x;
    private int age;
    private int dx,dy;
    private int offsetX, offsetY;
    private double cdSum;
    private boolean left, right;
    private boolean hideCards;
    private boolean cooldown;
    private int dragCard;
    private int empireNumber;
    private int mana;
    private int ageMultiplier;
    private String empireName;
    private String zoom;
    private String imageBack;
    private String alertText;
    private String imageBase;
    private String soundFile;
    private Color dark;
    private Image background;
    private Image zoomedImage;
    private Image special;
    private Image overlay;
    private Image baseImage;
    private Deck deck;
    private ArrayList<DisplayCard> hand;
    private ArrayList<Entity> units;
    private ArrayList<Entity> enemy;
    private Date startDate, endDate;
    private Timer clock;
    private Timer cd;
    private Base playerB, enemyB;
    AudioStream as;
    
    Game(int e) {
        empireNumber = e;
        imageBase="ageBase1.png";
        imageBack="ageBackground1.jpg";
        soundFile = "";
        //checks for which empire was selected, sets the string and sets the music
        switch (e) {
            case 1:
                empireName = "persia";
                soundFile = "persia.au";
                break;
            case 2:
                empireName = "china";
                soundFile = "china.au";
                break;
            case 3:
                empireName = "mexico";
                soundFile = "mexico.au";
                break;
            default:
                empireName = "mars";
                soundFile = "mars.au";
                break;
        }
        
        //tries to load in the song
        try {
          InputStream in = new FileInputStream(soundFile);
          as = new AudioStream(in);
        } catch (IOException ex) {
            System.out.println("Can't play");
        }
        //plays song
        AudioPlayer.player.start(as);
        
        //variable setters
        age = 1;
        placedCount = 0;
        mana = 100;
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
        baseImage = Toolkit.getDefaultToolkit().getImage(imageBase);
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
        
        //jframe setup
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
        
        //loads deck
        initGame();
        //adds 5 cards to the hand
        for (int i = 0; i < 5; i++) {
            addHand();
        }
    }
    
    /**
     * Loads in card list from cards.txt and creates
     * a deck with them depending on the age.
     */
    public void initGame() {
        File myFile;
        Scanner cardIn, cardLoader;
        try{
            myFile = new File("cards.txt"); 
            cardIn = new Scanner(myFile);
            //load next card
            while (cardIn.hasNext()) {
                int card = cardIn.nextInt();
                card += ageMultiplier;
                cardLoader = new Scanner(new File("persiaCards/" + card + ".txt"));
                //place card in deck
                deck.addCard(new Unit(cardLoader.next(),cardLoader.next(),
                                      cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt(),
                                      cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt()));
            }
            //shuffle the deck
            deck.shuffle();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
    
    /**
     * Adds a card to the hand from the deck
     */
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
        
        /**
         * Converts a card object to an entity object
         * to be displayed on the screen
         * @param c the card object to be converted
         * @return all data from the card as an entity object
         */
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
        
        /**
         * Changes the age of the game
         */
        public void changeAge() {
            //change depending on count
            switch (placedCount) {
                case 10:
                    age = 2;
                    background = Toolkit.getDefaultToolkit().getImage(imageBack);
                    baseImage=Toolkit.getDefaultToolkit().getImage(imageBase);
                    break;
                case 20:
                    age = 3;
                    background = Toolkit.getDefaultToolkit().getImage(imageBack);
                    baseImage=Toolkit.getDefaultToolkit().getImage(imageBase);
                    break;
                case 30:
                    age = 4;
                    background = Toolkit.getDefaultToolkit().getImage(imageBack);
                    baseImage=Toolkit.getDefaultToolkit().getImage(imageBase);
                    break;
                case 40:
                    age = 5;
                    background = Toolkit.getDefaultToolkit().getImage(imageBack);
                    baseImage=Toolkit.getDefaultToolkit().getImage(imageBase);
                    break;
                default:
                    break;
            }
            
            //change images depending on age
            imageBack = "ageBackground" + age + ".jpg";
            imageBase= "ageBase" + age + ".jpg";
            ageMultiplier = 4 * (age - 1);
            
            //redo deck
            deck.clear();
            initGame();
        }
        
        /**
         * Spawns a new enemy on the field
         */
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
        
        /**
         * Attacking between units and bases
         * @param def the defender, moot if attacking base
         * @param att the attacker, moot if attacking base
         * @param target a string denoting the target
         */
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
            if (enemyB.getBaseHealth()<0){
                AudioPlayer.player.stop(as);
                End end = new End(true);
                setVisible(false);
                dispose();
            }
            if (playerB.getBaseHealth()<0){
                AudioPlayer.player.stop(as);
                End end = new End(false);
                setVisible(false);
                dispose();
            }
        }
        
        double cdL = 0;
        
        /**
         * Draws everything on the screen
         * @param g graphics object
         */
        public void redrawAll(Graphics g) {
            //drawing base
            g.drawImage(baseImage, x + 10, 350, null);
            g.setColor(Color.RED);
            g.fillRect(x + 10, 350, 145, 15);
            g.setColor(Color.GREEN);
            g.fillRect(x + 10, 350, (int)(145 * (playerB.getBaseHealth()/(playerB.getMaxH()*1.0))), 15);
            g.drawImage(baseImage, x + 2300, 350, null);
            g.setColor(Color.RED);
            g.fillRect(x + 2300, 350, 145, 15);
            g.setColor(Color.GREEN);
            g.fillRect(x + 2300, 350, (int)(145 * (enemyB.getBaseHealth()/(enemyB.getMaxH()*1.0))), 15);
            
            //drawing enemies
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
            
            //drawing allies
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
            
            //drawing hand
            if (!hideCards) { //if not hidden
                for (int i = 0; i < hand.size() && i < 5; i++) {
                    Image img = Toolkit.getDefaultToolkit().getImage("persiaCards/" + hand.get(i).picture);
                    
                    //drawing cards if being dragged by mouse
                    if (!dragging || i != dragCard) {
                        g.drawImage(img, 25 + (i * 220), 500, null);
                    } else {
                        g.drawImage(img, dx - offsetX, dy - offsetY, null);
                    }
                }
            }
            
            //drawing top bar
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
            
            //cooldown bar logic for special ability
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
        
        /**
         * Redraw the window and run the logic
         * @param g graphics object
         */
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
            
            //draw everything on the screen
            redrawAll(g);
            
            clock.update();
            double elapsed = clock.getElapsedTime();
            timeSum += elapsed;
            
            //units move every 0.01 seconds
            if (timeSum > 0.01) {
                //move enemies
                for (int i = 0; i < enemy.size(); i++) {
                    if (!enemy.get(i).getStop()) {
                        enemy.get(i).setX(enemy.get(i).getX() - enemy.get(i).getSpeed());
                    }
                    if ((i < enemy.size() - 1) && enemy.get(i + 1).getX() - enemy.get(i).getX() < 200) {
                        enemy.get(i + 1).setX(enemy.get(i).getX() + 200);
                    }
                }
                
                //move allies
                for (int i = 0; i < units.size(); i++) {
                    if (!units.get(i).getStop()) {
                        units.get(i).setX(units.get(i).getX() + units.get(i).getSpeed());
                    }
                    if ((i < units.size() - 1) && units.get(i + 1).getX() - units.get(i).getX() > -200) {
                        units.get(i + 1).setX(units.get(i).getX() - 200);
                    }
                }
                
                //attacking logic
                if ((units.size() > 0 && units.get(0).getStop()) || (enemy.size() > 0 && enemy.get(0).getStop())) {
                    endDate = new Date();
                    if (((endDate.getTime() - startDate.getTime())/1000)> 1) {
                        if (units.size() > 0 && enemy.size() > 0 && units.get(0).getStop() && enemy.get(0).getStop()) { //two units must be attacking each other
                            attack(0,0,"enemy");
                            attack(0,0,"unit");
                            if (units.size() > 1 && units.get(1).getRange() == 2) {
                                attack(0,1,"enemy");
                            }
                            if (enemy.size() > 1 && enemy.get(1).getRange() == 2) {
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
            
            //if the player is zooming on a card
            if (!zoom.equals("")) {
                g.drawImage(zoomedImage, 400, 100, null);
            }
            
            //check if a new age can dawn
            if (placedCount % 10 == 0) {
                changeAge();
            }
            
            //check if any units have died
            for (int i = 0; i < units.size(); i++) {
                if (units.get(i).getHp() <= 0) {
                    units.remove(i);
                }
            }
            
            //check if any enemies have died
            for (int i = 0; i < enemy.size(); i++) {
                if (enemy.get(i).getHp() <= 0) {
                    mana += enemy.get(i).getPiercing() * 1.2;
                    enemy.remove(i);
                }
            }
            repaint();
        }
        
        boolean done = false;
        /**
         * Called when a key is typed. All of
         * these are debug commands which should not
         * be run by the player
         * @param e Key Event object
         */
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == VK_ESCAPE) {
                System.exit(0);
            }
            if (e.getKeyChar() == 'u') {
                units.remove(0);
            }
            if (e.getKeyChar() == 'i') {
                System.out.println(units.get(0).getX());
            }
            if (e.getKeyChar() == 'o') {
                units.get(1).setHp(units.get(1).getHp() - 2);
            }
            if (e.getKeyChar() == 'p') {
                attack(0,0,"enemyBase");
            }
            if (e.getKeyChar() == '[') {
                attack(0,0,"playerBase");
            }
        }
        
        /**
         * Called when a key is pressed down.
         * Holds the zooming and hiding logic
         * @param e Key Event object
         */
        public void keyPressed(KeyEvent e) {
            if (e.getKeyChar() == 'z') { //zooming
                Point p = MouseInfo.getPointerInfo().getLocation();
                double px = p.getX();
                double py = p.getY();
                alertText = "";
                
                //which card is clicked
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
                
                //zoom in
                if (!done && !zoom.equals("")) {
                    System.out.println(zoom);
                    zoomedImage = Toolkit.getDefaultToolkit().getImage("persiaCards/" + zoom.substring(0,zoom.indexOf(".")) + "x.png");
                    done = true;
                }
            }
            
            //hide cards
            if (e.getKeyChar() == 'h') {
                hideCards = true;
                alertText = "";
            }
        }
        
        /**
         * Called when a key is released.
         * Undos most key pressed
         * @param e Key Event object
         */
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
        /**
         * Called when the mouse is released.
         * Used to place a card down
         * @param e Mouse Event object
         */
        public void mouseReleased(MouseEvent e) {
            //places a card
            if (e.getY() < 480 && dragging){
                Unit look = hand.get(dragCard).getCard();
                if (look.getPrice()<=mana){ //if you have enough mana
                    //subtracts cost
                    mana -= look.getPrice();
                    //removes from hand
                    Unit c = hand.remove(dragCard).getCard();
                    //converts to enemy
                    units.add(cardToEntity(c));
                    //replenish hand
                    addHand();
                    //increment counter
                    placedCount++;
                } else {
                    //prints if not enough mana to place
                    alertText = "Not enough mana";
                }
            }
            dragging = false;
        }
        
        /**
         * Called when the left click button is pressed.
         * Used to initiate card dragging
         * @param e 
         */
        public void mousePressed(MouseEvent e) {
            //get mouse position
            int px = e.getX();
            int py = e.getY();
            
            //moves through all 5 cards
            for (int i = 0; i < 5; i++) {
                if (px > 25 + (i * 220) && px < 170 + (i * 220) && py > 500 && hand.size() > i) {
                    //set dragging coordinates
                    dx = e.getX();
                    dy = e.getY();
                    //keep card position relative to mouse so when dragged it does not jump
                    offsetX = dx - (25 + (i * 220));
                    offsetY = dy - 500;
                    dragging = true;
                    dragCard = i;
                    alertText = "";
                }
            }
        }
        
        /**
         * Called when the left click is pressed and
         * the mouse is moved. Used to update position
         * of a card as its being moved.
         * @param e Mouse Event object
         */
        public void mouseDragged (MouseEvent e) {
            dx = e.getX();
            dy = e.getY();       
        }
        
        //unused methods
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        
        /**
         * Called when the left click button is
         * clicked. Used to activate the special ability.
         * @param e Mouse Event object
         */
        public void mouseClicked(MouseEvent e) {
            //get mouse position
            double px = e.getX();
            double py = e.getY();
            
            //distance formula from center of the button
            if (Math.sqrt(Math.pow(50-px,2) + Math.pow(50-py,2)) < 50 && !cooldown) {
                //abilities
                if (empireNumber==1){
                    //heal base for 100
                    if (playerB.getMaxH() > playerB.getBaseHealth() + 100) {
                        playerB.damageBase(-100);
                    } else { //will not overheal
                        playerB.damageBase(playerB.getBaseHealth() - playerB.getMaxH());
                    }
                }else if (empireNumber==2){
                    //increase mana by 500
                    mana += 500;
                }else if (empireNumber==3){
                    //increase speed of first unit
                    units.get(0).setSpeed(100);
                }else if (empireNumber==4){
                    //vaporizes the first enemy
                    enemy.remove(0);
                }
                cooldown = true;
                cdSum = 0;
                cd.update();
            }
        }
        //unused
        public void mouseMoved (MouseEvent e) {}
    }
    
    //jpanel for scrolling left and right
    class ScrollComponent extends JPanel implements MouseListener {
        ScrollComponent() {
            this.addMouseListener(this);
            //change this to a picture later
            setBackground(new Color(0,0,0,255));
            setVisible(true);
        }
        
        /**
         * Called when the mouse exits
         * the panel. Used to stop scrolling
         * @param e Mouse Event object
         */
        public void mouseExited(MouseEvent e) {
            left = false;
            right = false;
        }
        
        /**
         * Called when the mouse enters
         * the panel. Used to start scrolling
         * @param e Mouse Event object
         */
        public void mouseEntered(MouseEvent e) {
            //checks if scrolling left or right
            if (e.getXOnScreen() < 500) {
                left = true;
            } else {
                right = true;
            }
            alertText = "";
        }
        
        //unused listeners
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}