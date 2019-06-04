import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
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
    boolean stop;
    
    Game(int e) {
        enemy = new ArrayList<>();
        enemy.add(new Entity("Buffboy","tank.png",1,2,3,4,5,6));
        
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
        zoom = "";
        left = false;
        right = false;
        background = Toolkit.getDefaultToolkit().getImage(imageBack);
        deck = new Deck();
        dark = new Color(0,0,0,0);
        hand = new ArrayList<>();
        units = new ArrayList<>();
        stop = false;
        
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
        age=1;
        //load in the card list (1,1,1,2,2,2,2,2,3,4,4)
        File myFile;
        Scanner cardIn, cardLoader;
        try{
            myFile = new File(empireNumber + ".txt"); 
            cardIn = new Scanner(myFile);
            while (cardIn.hasNext()) {
                int card = cardIn.nextInt();
                cardLoader = new Scanner(new File(empireName + "Cards/" + card + ".txt"));
                deck.addCard(new Unit(cardLoader.next(),cardLoader.next(),
                    cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt(),
                    cardLoader.nextInt(),cardLoader.nextInt(),cardLoader.nextInt()));
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        System.out.println(deck.toString());
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
            int attackSpeed = c.getAttackSpeed();
            return new Entity(name,des,hp,armor,range,speed,price,attackSpeed);
        }
        
        public void enemyKilled() {
          
        
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, x,0,null);
            if (left && x < 0) x++;
            if (right && x > -1485) x--;
            g.setColor(dark);
            g.fillRect(0, 0, 1366, 768);
            
            for (int i = 0; i < units.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + units.get(i).des.substring(0,units.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + 100 + (units.get(i).getX()/10), 400, null);
                units.get(i).setX(units.get(i).getX() + units.get(i).getSpeed());
                if (stop) units.get(0).setX(units.get(0).getX() - units.get(0).getSpeed());
                if (i < (units.size() - 1) && units.size() > 1 && units.get(i).getX() - units.get(i + 1).getX() < 1100) {
                    units.get(i + 1).setX(units.get(i).getX() - 1100);
                }
            }
            
            for (int i = 0; i < enemy.size(); i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + enemy.get(i).des.substring(0,enemy.get(i).des.indexOf(".")) + "p.png");
                g.drawImage(img, x + 2000 + (enemy.get(i).getX()/10), 400, null);
                enemy.get(i).setX(enemy.get(i).getX() - enemy.get(i).getSpeed());
            }
            
            
            for (int i = 0; i < hand.size() && i < 5; i++) {
                Image img = Toolkit.getDefaultToolkit().getImage(empireName + "Cards/" + hand.get(i).picture);
                if (!dragging || i != dragCard) {
                    g.drawImage(img, 25 + (i * 220), 500, null);
                } else {
                    g.drawImage(img, dx - offsetX, dy - offsetY, null);
                }
            }
            
            if (!zoom.equals("")) {
                g.drawImage(zoomedImage, 400, 100, null);
            }
            if (placedCount==5){
                age++;
                background = Toolkit.getDefaultToolkit().getImage(imageBack);
            }
            if (age==2){
                imageBack="ageBackground.jpg";
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
                stop = true;
            }
        }
        
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                dark = new Color(0,0,0,0);
                zoom = "";
                done = false;
            }
            if (e.getKeyChar() == 'd') {
                stop = false;
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
        public void mouseMoved (MouseEvent me) {}
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