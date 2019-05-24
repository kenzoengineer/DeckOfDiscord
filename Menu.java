import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Menu extends JFrame{
  double px=0;
  double py=0;
  GamePanel gp;
  Menu() {
    setSize(1366,768); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
    gp = new GamePanel();
    JPanel myPanel = new JPanel();
    add(myPanel);
    this.add(gp);
    setVisible(true);   
  }
  class GamePanel extends JPanel implements MouseListener{
    
    
    GamePanel() {
      this.addMouseListener(this);
      setFocusable(true);
      requestFocusInWindow();
      setUndecorated(false);
    }
    public void mouseExited(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) { 
    }
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
      px= e.getX();
      py= e.getY();
      //System.out.println(px + " "+ py);
      if (px<1500 && px>950 && py<280 && py>200){
        System.out.print("start"+"\n");
         Empire game = new Empire();
         setVisible(false);
         dispose();
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
