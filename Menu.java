import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import sun.audio.*;
class Menu extends JFrame{
  double px=0;
  double py=0;
  Image popup;
  GamePanel gp;
  AudioStream audioStream;
  Menu() {
    String soundFile = "menu.au";
    try {
      InputStream in = new FileInputStream(soundFile);
      audioStream = new AudioStream(in);
    } catch (IOException e) {}
    
    popup = Toolkit.getDefaultToolkit().getImage("transparent.png");
    AudioPlayer.player.start(audioStream);
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
      System.out.println(px + " "+ py);
      if (px<1500 && px>950 && py<280 && py>200){
        System.out.print("start"+"\n");
        AudioPlayer.player.stop(audioStream);
        Empire game = new Empire();
        setVisible(false);
        dispose();
      }else if(px<1500 && px>950 && py<380 && py>300){
        System.out.print("options"+"\n");
        popup = Toolkit.getDefaultToolkit().getImage("Instructions.png");
      }else if(px<1500 && px>950 && py<480 && py>400){
        System.out.print("credits"+"\n");
      }else if(px<1500 && px>950 && py<580 && py>500){
        System.out.print("exit"+"\n");
        System.exit(0);
      } else if (px > 1175 && py > 674) {
          popup = Toolkit.getDefaultToolkit().getImage("transparent.png");
      }
    }
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Image menu = Toolkit.getDefaultToolkit().getImage("menuImage.png");
      g.drawImage(menu,0,0,1366,768,this);
      g.drawImage(popup, 0, -30, 1366, 768, null);
      repaint();
    }
  }
}
