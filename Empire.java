import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import sun.audio.*;
class Empire extends JFrame{
  double px=0;
  double py=0;
  int empires = 0;
  Image empireSelect;
  AudioStream audioStream;
  String soundFile = "menu.au";
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
  
  public int empireCheck(){
    return empires;
  }
  
  public void abilty(){
    if (empires == 1){
      Persia nation = new Persia();
      nation.effect();
    }else if (empires == 2){
      China nation = new China();
      nation.effect();
    }else if (empires == 3){
      Mexico nation = new Mexico();
      nation.effect();
    }else if (empires == 4){
      Mars nation = new Mars();
      nation.effect();
    }
  }
  
  public void select(){
    if (empires == 1){
      empireSelect = Toolkit.getDefaultToolkit().getImage("persia.jpg");
      AudioPlayer.player.stop(audioStream);
      soundFile = "persia.au";
      try {
        InputStream in = new FileInputStream(soundFile);
        audioStream = new AudioStream(in);
      } catch (IOException e) {}
      AudioPlayer.player.start(audioStream);
    }else if (empires == 2){
      empireSelect = Toolkit.getDefaultToolkit().getImage("China.jpg");
      AudioPlayer.player.stop(audioStream);
      soundFile = "china.au";
      try {
        InputStream in = new FileInputStream(soundFile);
        audioStream = new AudioStream(in);
      } catch (IOException e) {}
      AudioPlayer.player.start(audioStream);
    }else if (empires == 3){
      empireSelect = Toolkit.getDefaultToolkit().getImage("mexico.jpg");
      AudioPlayer.player.stop(audioStream);
      soundFile = "mexico.au";
      try {
        InputStream in = new FileInputStream(soundFile);
        audioStream = new AudioStream(in);
      } catch (IOException e) {}
      AudioPlayer.player.start(audioStream);
    }else if (empires == 4){
      empireSelect = Toolkit.getDefaultToolkit().getImage("mars.jpg");
      AudioPlayer.player.stop(audioStream);
      soundFile = "mars.au";
      try {
        InputStream in = new FileInputStream(soundFile);
        audioStream = new AudioStream(in);
      } catch (IOException e) {}
      AudioPlayer.player.start(audioStream);
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
        empires=2;
        select();
      }else if(px<1300 && px>950 && py<480 && py>400){
        System.out.print("mexico"+"\n");
        empires=3;
        select();
      }else if(px<1300 && px>950 && py<640 && py>560){
        System.out.print("mars"+"\n");
        empires=4;
        select();
      }else if(px<350 && px>50 && py<700 && py>600){
        System.out.print("back"+"\n");
        empireSelect = Toolkit.getDefaultToolkit().getImage("select.jpg");
      }else if (px < 328 && px > 76 && py < 534 && py > 458){
        System.out.println("aaaa");
        dispose();
        Game game = new Game();
      }
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(empireSelect,0,0,1366,768,this);
      repaint();
    }
    
  }
}
