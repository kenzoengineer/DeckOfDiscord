/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kenj8
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_BACK_SPACE;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.swing.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
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
    
    public void write(int s) {
        pw.println(s);
    }
    
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
        
        public void openMenu() {
            AudioPlayer.player.stop(as);
            Menu menu = new Menu();
            setVisible(false);
            dispose();
        }
        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg,0,0,null);
            if (displayS.length() < 40) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }
            g.setFont(new Font("Consolas Regular", Font.PLAIN, 70));
            g.drawString(displayS, 111, 334);
            repaint();
        }
        
        public void keyPressed(KeyEvent e) {
            if (displayS.length() < 40) {
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
                if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    displayS.substring(0,displayS.length() - 2);
                }
            }
        }
        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
        public void mouseExited(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
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
    }
}
