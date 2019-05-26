import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Empire extends JFrame{
  double px=0;
  double py=0;
  int empires = 0;
  Image empireSelect;
  Empire() {
    empireSelect = Toolkit.getDefaultToolkit().getImage("select.jpg");
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
      empireSelect = Toolkit.getDefaultToolkit().getImage("persia1.jpg");
    }else if (empires == 2){
      Image empireSelect = Toolkit.getDefaultToolkit().getImage("china.png");
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
      System.out.println(px + " "+ py);
      if (px<1300 && px>1000 && py<240 && py>150){
        System.out.print("persia"+"\n");
        empires=1;
        select();
      }else if(px<1300 && px>950 && py<380 && py>300){
        System.out.print("china"+"\n");
      }else if(px<1300 && px>950 && py<480 && py>400){
        System.out.print("mexico"+"\n");
      }else if(px<1300 && px>950 && py<640 && py>560){
        System.out.print("mars"+"\n");
      }else if(px<350 && px>50 && py<700 && py>600){
        System.out.print("back"+"\n");
        empireSelect = Toolkit.getDefaultToolkit().getImage("select.jpg");
      }
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(empireSelect,0,0,1366,768,this);
      repaint();
    }
    
  }
}
