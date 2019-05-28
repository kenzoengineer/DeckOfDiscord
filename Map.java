import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.NameList;
class Map extends JFrame{
    public String l = "";
    int x = 0;
    int y = 0;
    boolean left = false;
    boolean right = false;
    Image background = Toolkit.getDefaultToolkit().getImage("bg.png");
    Deck deck = new Deck();
    ArrayList<Card> hand = new ArrayList<>();
    Game() {
        hand.add(new Unit("","",1,2,3,4,5,6));
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
        hand.add(deck.pop());
    }
    
    class GamePanel extends JPanel implements KeyListener{
        GamePanel() {
            this.addKeyListener(this);
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(true);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            //g.fillRect(x,y,200,200);
            g.drawImage(background, x,y,null);
            g.drawString(l, 20,400);
            if (left) x++;
            if (right) x--;
            
            for (int i = 0; i < hand.size() && i < 5; i++) {
                g.fillRect(25 + (i * 220), 500, 150, 200);
            }
            
            g.setColor(Color.BLACK);
            repaint();
        }
        
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
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
    }
    class ScrollComponentL extends JPanel implements MouseListener {
        ScrollComponentL() {
            this.addMouseListener(this);
            setOpaque(false);
            setBackground(new Color(0,0,0,0));
        }
        public void mouseExited(MouseEvent e) {
            l = "Mouse Exited";
            left = false;
        }
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            l = (p.toString());
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
            l = "Mouse Exited";
            right = false;
        }
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            l = (p.toString());
            right = true;
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}