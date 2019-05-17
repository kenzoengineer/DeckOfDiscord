import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Menu extends JFrame{
  static int x=0;
  static double px=0;
  static double py=0;
  Menu() {
        setSize(1366,768); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0,3));
        setResizable(false);
        GamePanel gp = new GamePanel();
        JPanel myPanel = new JPanel();
        add(myPanel);
        JPanel left = new ScrollComponent();
        left.setBorder(BorderFactory.createLineBorder(Color.black));
        left.setPreferredSize(new Dimension(300,300));
        this.add(gp);
        this.add(left);
        setVisible(true);
        
    }
  class GamePanel extends JPanel{
        GamePanel() {
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(false);
        }
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Image menu = Toolkit.getDefaultToolkit().getImage("menuImage.png");
            g.drawImage(menu,0,0,1366,100,this);
            g.setColor(Color.GREEN);
            g.fillRect(900,900,100,100);
            repaint();
        }
        
    }
  class ScrollComponent extends JPanel implements MouseListener {
        ScrollComponent() {
            this.addMouseListener(this);
        }
        public void mouseExited(MouseEvent e) {
            x = 0;
        }
        public void mouseEntered(MouseEvent e) {
            Point p = new Point(e.getLocationOnScreen());
            px= p.getX();
            py= p.getY();
            System.out.print(px);
            x=1;
        }
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseClicked(MouseEvent e) {
          if (px<950 && px>900 && py<950 && py<900){
            System.out.print("start");
          }
        }
    }
}