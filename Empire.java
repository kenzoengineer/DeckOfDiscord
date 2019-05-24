import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Empire extends JFrame{
  double px=0;
  double py=0;
  int empires = 0;
  String imageChange="menuImage.png";
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
    if (empires == 1){
      Persia persia = new Persia();
      imageChange="persia.jpg";
      repaint();
    }else if (empires == 2){
      Image empireSelect = Toolkit.getDefaultToolkit().getImage("china.png");
    }
  }
  
  public void select(){
    if (empires == 1){
      Persia persia = new Persia();
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
        System.out.print("persia"+"\n");
        empires=1;
        setVisible(false);
        dispose();
        abilty();
        
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
      Image empireSelect = Toolkit.getDefaultToolkit().getImage(imageChange);
      g.drawImage(empireSelect,0,0,1366,768,this);
      repaint();
    }
    
  }
}
