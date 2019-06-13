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
 * The main menu of the game when you first open it. It leads to starting the
 * game, how to play, building the deck and exit. It also plays the menu music
 * 
 * @author Souren A., Ken J.
 * @since May 24th, 2019
 * @version 2.82
 */

class Menu extends JFrame{
    double px;
    double py;
    String soundFile;
    Image popup;
    GamePanel gp;
    AudioStream audioStream;
  
    Menu() {
        //load in the audio
        soundFile = "menu.au";
        try {
          InputStream in = new FileInputStream(soundFile);
          audioStream = new AudioStream(in);
        } catch (IOException e) {
            System.out.println("Can't play");
        }
        AudioPlayer.player.start(audioStream);

        //initialize variables
        px = 0;
        py = 0;
        popup = Toolkit.getDefaultToolkit().getImage("transparent.png");

        //jframe methods
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
            //add listener
            this.addMouseListener(this);
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(false);
        }

        /**
         * Called when the mouse is clicked
         * @param e the mouse event
         */
        public void mouseClicked(MouseEvent e) {
            //get mouse position
            px = e.getX();
            py = e.getY();
            
            //button listens
            if (px < 1500 && px > 950 && py < 280 && py > 200) { //if start game is clicked
                AudioPlayer.player.stop(audioStream);
                Empire empire = new Empire();
                setVisible(false);
                dispose();
            } else if (px < 1500 && px > 950 && py < 380 && py > 300) { //if instructions are clicked
                popup = Toolkit.getDefaultToolkit().getImage("Instructions.png");
            } else if (px < 1500 && px > 950 && py < 480 && py > 400){ //if deckbuilder is clicked
                AudioPlayer.player.stop(audioStream);
                DeckBuilder db = new DeckBuilder();
                setVisible(false);
                dispose();
            } else if (px < 1500 && px > 950 && py < 580 && py > 500) { //if exit is clicked
                System.exit(0);
            } else if (px > 1175 && py > 674) { //will display nothing if close is pressed
                popup = Toolkit.getDefaultToolkit().getImage("transparent.png");
            }
        }
        
        /**
        * Redraws the screen
        * @param g graphics
        */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw menu
            Image menu = Toolkit.getDefaultToolkit().getImage("menuImage.png");
            g.drawImage(menu,0,0,1366,768,this);
            g.drawImage(popup, 0, -30, 1366, 768, null);
            repaint();
        }
        
        //unused mouse event methods
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
    }
}

