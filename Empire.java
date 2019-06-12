import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * The empire selection screen, which allows the user to pick from 4 different
 * empires: Perisa, China, Mexico and Mars. Each have different abilities and
 * play different songs during the game
 * @since May 25th, 2019
 * @author Souren A., Ken J.
 * @version 2.50
 */

class Empire extends JFrame{
    double px;
    double py;
    int empires;
    Image empireSelect;
    AudioStream audioStream;
    String soundFile = "menu.au";
    Empire() {
        px = 0;
        py = 0;
        empires = 0;
        empireSelect = Toolkit.getDefaultToolkit().getImage("select.jpg");
        setSize(1366,768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        GamePanel gp = new GamePanel();
        JPanel myPanel = new JPanel();
        add(myPanel);
        add(gp);
        setVisible(true);
    }
  
    public int empireCheck(){
        return empires;
    }
  
    public void select(){
        if (empires == 1){
            empireSelect = Toolkit.getDefaultToolkit().getImage("persia.jpg");
            AudioPlayer.player.stop(audioStream);
            soundFile = "persia.au";
            try {
                InputStream in = new FileInputStream(soundFile);
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Cannot play");
            }
            AudioPlayer.player.start(audioStream);
        }else if (empires == 2){
            empireSelect = Toolkit.getDefaultToolkit().getImage("China.jpg");
            AudioPlayer.player.stop(audioStream);
            soundFile = "china.au";
            try {
                InputStream in = new FileInputStream(soundFile);
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Cannot play");
            }
            AudioPlayer.player.start(audioStream);
        }else if (empires == 3){
            empireSelect = Toolkit.getDefaultToolkit().getImage("mexico.jpg");
            AudioPlayer.player.stop(audioStream);
            soundFile = "mexico.au";
            try {
                InputStream in = new FileInputStream(soundFile);
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Cannot play");
            }
            AudioPlayer.player.start(audioStream);
        }else if (empires == 4){
            empireSelect = Toolkit.getDefaultToolkit().getImage("mars.jpg");
            AudioPlayer.player.stop(audioStream);
            soundFile = "mars.au";
            try {
                InputStream in = new FileInputStream(soundFile);
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Cannot play");
            }
            AudioPlayer.player.start(audioStream);
        }
    }
  
  class GamePanel extends JPanel implements MouseListener{
    GamePanel() {
      this.addMouseListener(this);
      setFocusable(true);
      requestFocusInWindow();
      setUndecorated(false);
    }
    
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
        dispose();
        Game game = new Game(empireCheck());
      }
    }
    
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(empireSelect,0,0,1366,768,this);
      repaint();
    }
    
    public void mouseExited(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    
  }
}
