import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
class Game extends JFrame{
    public String l = "";
    int x = 0;
    int y = 0;
    int dx = 0;
    int dy = 0;
    int offsetX = 0;
    int offsetY = 0;
    boolean left = false;
    boolean right = false;
    int dragCard;
    String zoom = "";
    Color dark = new Color(0,0,0,0);
    Image background = Toolkit.getDefaultToolkit().getImage("bg.png");
    Deck deck = new Deck();
    Image zoomedImage;
    ArrayList<DisplayCard> hand = new ArrayList<>();
    Game() {
        //add placeholder card
        //hand.add(new DisplayCard(new Unit("","",1,2,3,4,5,6)));
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        
        JPanel whole = new JPanel();
        whole.setLayout(new BoxLayout(whole, BoxLayout.X_AXIS));
        GamePanel gp = new GamePanel();
        gp.setPreferredSize(new Dimension(1000 + 80,768-500));
        
        JPanel left = new ScrollComponentL();
        //left.setBorder(BorderFactory.createLineBorder(Color.black));
        left.setPreferredSize(new Dimension(183-40,768-500));
                
        JPanel right = new ScrollComponentR();
        //right.setBorder(BorderFactory.createLineBorder(Color.black));
        right.setPreferredSize(new Dimension(183-40,768-500));
        
        whole.add(left);
        whole.add(gp);
        whole.add(right);
        add(whole);
        setVisible(true);
    }
    public void initGame() {
        //create deck of cards
        for (int i = 0; i < 20; i++) {
            deck.addCard(new Unit(Integer.toString((int) (Math.random() * 255)),"e",1,2,3,4,5,6));
        }
        System.out.println(deck.toString());
    }
    
    public void addHand() {
        hand.add(new DisplayCard(deck.pop()));
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
        
        public Image resize(Image i,double scale) {
            return i.getScaledInstance((int)(i.getWidth(null) * scale),(int) (i.getHeight(null) * scale), Image.SCALE_SMOOTH);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.drawImage(background, x,y,null);
            g.drawString(l, 20,400);
            if (left) x++;
            if (right) x--;
            for (int i = 0; i < hand.size() && i < 5; i++) {
                g.setColor(hand.get(i).color);
                Image img = Toolkit.getDefaultToolkit().getImage(hand.get(i).picture);
                if (!dragging || i != dragCard) {
                    g.drawRect(25 + (i * 220), 500,145,200);
                    g.drawImage(img, 25 + (i * 220), 500, null);
                } else {
                    g.drawImage(img, dx - offsetX, dy - offsetY, null);
                }
            }
            if (!zoom.equals("")) {
                g.drawImage(zoomedImage, 400, 200, null);
            }
            
            g.setColor(dark);
            g.fillRect(0, 0, 1366, 768);
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
                //System.out.println(px + " " + py);
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
                if (!done) {
                    zoomedImage = Toolkit.getDefaultToolkit().getImage(zoom);
                    zoomedImage = resize(zoomedImage,2);
                    done = true;
                }
            }
        }
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == 'z') {
                dark = new Color(0,0,0,0);
                zoom = "";
                done = false;
            }
        }
        boolean dragging = false;
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {
            if (e.getY() < 480 && dragging) {
                hand.remove(dragCard);
            }
            dragging = false;
        }
        public void mouseClicked(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {
            System.out.println("Pressed. x: " +  e.getX() + " y: " + e.getY());
            int px = e.getX();
            int py = e.getY();
            for (int i = 0; i < 5; i++) {
                if (px > 25 + (i * 220) && px < 170 + (i * 220) && py > 500 && hand.size() > i) {
                    dx = e.getX();
                    dy = e.getY();
                    offsetX = dx - (25 + (i * 220));
                    System.out.println(i);
                    offsetY = dy - 500;
                    System.out.println(offsetX + " " + offsetY);
                    dragging = true;
                    dragCard = i;
                }
            }
        }
        public void mouseDragged (MouseEvent e) {
            dx = e.getX();
            dy = e.getY();       
        }
        public void mouseMoved (MouseEvent me) {}
    }
    class ScrollComponentL extends JPanel implements MouseListener {
        ScrollComponentL() {
            this.addMouseListener(this);
            setOpaque(false);
            setBackground(new Color(0,0,0,0));
        }
        public void mouseExited(MouseEvent e) {
            left = false;
        }
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            left = true;
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
    class ScrollComponentR extends JPanel implements MouseListener {
        ScrollComponentR() {
            this.addMouseListener(this);
            setOpaque(false);
            setBackground(new Color(0,0,0,0));
        }
        public void mouseExited(MouseEvent e) {
            right = false;
        }
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            right = true;
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}