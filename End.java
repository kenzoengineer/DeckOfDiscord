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

class End extends JFrame{
    Image ending;
    String endImage;
    AudioStream audioStream;
    String soundFile;
    
    End() {
        //soundFile = "menu.au";
        ending = Toolkit.getDefaultToolkit().getImage(endImage);
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
  
  
    public void endGame(int winLose){
      if (winLose==0){
        endImage="win.jpg";
        ending = Toolkit.getDefaultToolkit().getImage(endImage);
      }else if (winLose==1){
         endImage="lose.jpg";
        ending = Toolkit.getDefaultToolkit().getImage(endImage);
      }
       
    }
  
    class GamePanel extends JPanel implements MouseListener{
        GamePanel() {
          //add listeners
          this.addMouseListener(this);
          setFocusable(true);
          requestFocusInWindow();
          setUndecorated(false);
        }

        public void mouseClicked(MouseEvent e) {
            
        }

        /**
         * redraws the screen
         * @param g graphics object
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(ending,0,0,1366,768,this);
            repaint();
        }

        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}

    }
}
