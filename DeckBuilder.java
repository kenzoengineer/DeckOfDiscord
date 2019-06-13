import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Allows to user to create their own deck of cards, meaning endless
 * combinations and play styles. Read and writes to a text file 
 * 
 * @see cards.txt
 * @author Souren A., Ken J.
 * @since June 11th, 2019
 * @version 2.80
 */


public class DeckBuilder extends JFrame {
    FileWriter fw;
    PrintWriter pw;
    Image bg;
    GamePanel gp;
    String displayS;
    AudioStream as;
    public DeckBuilder() {
        displayS = "";
        try {
            InputStream in = new FileInputStream("deckbuilder.au");
            as = new AudioStream(in);
        } catch (IOException e) {
            System.out.println("Can't play");
        }
        AudioPlayer.player.start(as);
        bg = Toolkit.getDefaultToolkit().getImage("deckBuilder.png");
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
    
    /**
     * Writes an integer to cards.txt
     * @param i is the integer being written to the file
     */
    public void write(int i) {
        pw.println(i);
    }
    
    /**
     * Closes the File Writer and Print Writer
     * which saves the changes
     */
    public void close() {
        try {
            fw.close();
            pw.close();
        } catch (IOException e) {
            System.out.println("Close error");
        }
    }
    
    class GamePanel extends JPanel implements KeyListener, MouseListener {
        public GamePanel() {
            this.addKeyListener(this);
            this.addMouseListener(this);
            setFocusable(true);
            requestFocusInWindow();
            setUndecorated(false);
        }
        
        /**
         * Opens the menu window after this window is closed
         */
        public void openMenu() {
            AudioPlayer.player.stop(as);
            Menu menu = new Menu();
            setVisible(false);
            dispose();
        }
        
        /**
         * Redraws the screen
         * @param g graphic object
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg,0,0,null);
            if (displayS.length() < 40) {
                g.setColor(Color.RED);
            } else { //goes green if valid
                g.setColor(Color.GREEN);
            }
            g.setFont(new Font("Consolas Regular", Font.PLAIN, 70));
            g.drawString(displayS, 111, 334);
            repaint();
        }
        
        /**
         * Called when a key is pressed
         * @param e Key Event object
         */
        public void keyPressed(KeyEvent e) {
            if (displayS.length() < 40) {
                //input from the user
                if (e.getKeyChar() == '1') {
                    displayS += "1 ";
                } else if (e.getKeyChar() == '2') {
                    displayS += "2 ";
                } else if (e.getKeyChar() == '3') {
                    displayS += "3 ";
                } else if (e.getKeyChar() == '4') {
                    displayS += "4 ";
                }
            }
            if (displayS.length() > 0) {
                //backspace removes a character
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    displayS = displayS.substring(0,displayS.length() - 2);
                }
            }
        }
        
        /**
         * Called when the left click button is clicked
         * @param e Mouse Event object
         */
        public void mouseClicked(MouseEvent e) {
            //Point p = e.getPoint();
            //System.out.println(p.toString());
            int px = e.getX();
            int py = e.getY();
            if (px > 880 && px < 1062 && py > 669 && py < 738) {
                openMenu();
            } else if (px > 1077 && px < 1259 && py > 667 && py < 736) {
                if (displayS.length() == 40) {
                    try {
                        fw = new FileWriter(new File("cards.txt"));
                        pw = new PrintWriter(fw);
                    } catch (IOException ex) {
                        System.out.println("File Error");
                    }
                    for (int i = 0; i < 40; i += 2) {
                        write(Character.getNumericValue(displayS.charAt(i)));
                    }
                    close();
                    openMenu();
                }
            }
        }
        
        //unused event listeners
        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
    }
}
