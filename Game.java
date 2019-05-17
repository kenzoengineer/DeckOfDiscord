import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Game extends JFrame{
    public String l = "";
    Game() {
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,3));
        setResizable(false);
        GamePanel gp = new GamePanel();
        JPanel left = new ScrollComponent();
        left.setBorder(BorderFactory.createLineBorder(Color.black));
        left.setPreferredSize(new Dimension(300,300));
        
        JPanel right = new ScrollComponent();
        right.setBorder(BorderFactory.createLineBorder(Color.black));
        right.setPreferredSize(new Dimension(300,768));
        this.add(left);
        this.add(gp);
        this.add(right);
        setVisible(true);
    }
    class GamePanel extends JPanel implements KeyListener, MouseListener{
        GamePanel() {
            this.addKeyListener(this);
            this.addMouseListener(this);
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(true);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            g.fillRect(50,50,100,100);
            g.drawString(l, 0,400);
            repaint();
        }
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'p') {
                System.exit(0);
            }
        }
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            l = (p.toString());
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
    class ScrollComponent extends JPanel implements MouseListener {
        ScrollComponent() {
            this.addMouseListener(this);
        }
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            l = (p.toString());
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {}
    }
}