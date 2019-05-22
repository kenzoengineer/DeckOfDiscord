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
    setLayout(new BorderLayout());
    setResizable(false);
    GamePanel gp = new GamePanel();
    JPanel myPanel = new JPanel();
    add(myPanel);
    this.add(gp);
    setVisible(true);
    /*
     JPanel left = new ScrollComponent();
     left.setBorder(BorderFactory.createLineBorder(Color.black));
     left.setPreferredSize(new Dimension(300,300));
     this.add(gp);
     this.add(left);
     
     */
    
  }
  class GamePanel extends JPanel implements MouseListener{
    
    
    GamePanel() {
      this.addMouseListener(this);
      setFocusable(true);
      requestFocusInWindow();
      setUndecorated(false);
    }
    public void mouseExited(MouseEvent e) {
      x = 0;
    }
    public void mouseEntered(MouseEvent e) {
      
    }
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
      Point p = new Point(e.getLocationOnScreen());
      px= p.getX();
      py= p.getY();
      //System.out.println(px + " "+ py);
      if (px<1500 && px>950 && py<280 && py>200){
        System.out.print("start"+"\n");
      }else if(px<1500 && px>950 && py<380 && py>300){
        System.out.print("options"+"\n");
      }else if(px<1500 && px>950 && py<480 && py>400){
        System.out.print("credits"+"\n");
      }else if(px<1500 && px>950 && py<580 && py>500){
        System.out.print("exit"+"\n");
        System.exit(0);
      }
    }
      public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image menu = Toolkit.getDefaultToolkit().getImage("menuImage.png");
        g.drawImage(menu,0,0,1366,768,this);
        repaint();
      }
      
  }
  }
