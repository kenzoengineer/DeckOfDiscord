import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Empire extends JFrame{
  double px=0;
  double py=0;
  int empires = 0;
  Empire() {
    setSize(1366,768); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setResizable(false);
    GamePanel gp = new GamePanel();
    JPanel myPanel = new JPanel();
    add(myPanel);
    this.add(gp);
    setVisible(true);
  }
  
  public void abilty(){
  }
  
  public void select(){
    if (empires == 1){
    }
  }
  
  public void openGame(){
    Game startGame = new  Game();
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
