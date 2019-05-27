import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import org.w3c.dom.NameList;
class Game extends JFrame{
    public String l = "";
    int x = 0;
    int y = 0;
    boolean left = false;
    boolean right = false;
    Image background = Toolkit.getDefaultToolkit().getImage("bg.png");
    Deck deck = new Deck();
    ArrayList<Card> hand = new ArrayList<>();
    CardPanel[] handCards;
    Game() {
        hand.add(new Unit("","",1,2,3,4,5,6));
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        
        JPanel whole = new JPanel();
        whole.setLayout(new BoxLayout(whole, BoxLayout.X_AXIS));
        
        HandPanel cards = new HandPanel();
        cards.setLayout(new BoxLayout(cards, BoxLayout.X_AXIS));
        
        handCards = new CardPanel[5];
        for (int i = 0; i < handCards.length; i++) {
            handCards[i] = new CardPanel(i);
            cards.add(handCards[i]);
        }
        
        
        GamePanel gp = new GamePanel();
        gp.setPreferredSize(new Dimension(1000,768-500));
        
        JPanel left = new ScrollComponentL();
        //left.setBorder(BorderFactory.createLineBorder(Color.black));
        left.setPreferredSize(new Dimension(183,768-500));
                
        JPanel right = new ScrollComponentR();
        //right.setBorder(BorderFactory.createLineBorder(Color.black));
        right.setPreferredSize(new Dimension(183,768-500));
        
        whole.add(left);
        whole.add(gp);
        whole.add(right);
        add(whole);
        add(cards);
        setVisible(true);
    }
    public void initGame() {
        //create deck of cards
        for (int i = 0; i < 20; i++) {
            deck.addCard(new Unit(Integer.toString((int) (Math.random() * 255)),"e",1,2,3,4,5,6));
        }
        System.out.println(deck.toString());
        for (int i = 0; i < 5; i++) {
            hand.add(deck.pop());
        }
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
            
            g.setColor(Color.BLACK);
            for (int i = 0; i < handCards.length; i++) {
                String name = handCards[i].getName();
                g.drawString(name, 100 + i * 150, 500);
            }
            
            repaint();
        }
        
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'p') {
                System.exit(0);
            }
            if (e.getKeyChar() == 'a') {
                initGame();
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
    
    class HandPanel extends JPanel {
    }
    
    class CardPanel extends JPanel implements MouseListener {
        int num;
        public CardPanel(int i) {
            setBackground(new Color(220,220,220));
            setPreferredSize(new Dimension(50,100));
            setBorder(BorderFactory.createLineBorder(Color.black));
            num = i;
        }
        
        public String getName() {
            if (hand.size() > num) {
                String name = hand.get(num).getName();
                return name;
            } else return "e";
        }
        
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}