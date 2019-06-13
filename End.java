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
 * empires: Persia, China, Mexico and Mars. Each have different abilities and
 * play different songs during the game
 * 
 * @since June 12th, 2019
 * @author Souren A., Ken J.
 * @version 2.96
 */

class End extends JFrame{
    Image ending;
    AudioStream audioStream;
    String soundFile;
    boolean won;
    
    End(boolean won) {
        this.won = won;
        //run this if won
        if (won){
            ending = Toolkit.getDefaultToolkit().getImage("win.png");
            try {
                InputStream in = new FileInputStream("win.au");
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Can't Play");
            }
        } else { //run this if lost
            ending = Toolkit.getDefaultToolkit().getImage("lose.png");
            try {
                InputStream in = new FileInputStream("lose.au");
                audioStream = new AudioStream(in);
            } catch (IOException e) {
                System.out.println("Can't Play");
            }
        }
        
        //setting up jframe
        AudioPlayer.player.start(audioStream);
        setSize(1366,768);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        GamePanel gp = new GamePanel();
        add(gp);
        setVisible(true);
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
            //get cursor position
            int px = e.getX();
            int py = e.getY();
            //check if exit button is clicked
            if (px < 1352 && px > 1216 && py < 584 && py > 513) {
                System.exit(0);
            } else if (px < 1349 && px > 926 && py < 707 && py > 619) { //check if play again is clicked
                AudioPlayer.player.stop(audioStream);
                Menu menu = new Menu();
                setVisible(false);
                dispose();
            }
        }

        /**
         * redraws the screen
         * @param g graphics object
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(ending,0,0,1366,768,null);
            repaint();
        }

        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}

    }
}
