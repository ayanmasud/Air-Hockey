import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JPanel {

    int playerMalletX;
    int playerMalletY;

    public Table() {
        super();
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                playerMalletX = e.getX();
                playerMalletY = e.getY();
                if(playerMalletY < 470) { // restrictions to ensure players mallet doesn't go off the table
                    playerMalletY = 470;
                }
                if (playerMalletY > 853) {
                    playerMalletY = 853;
                }
                if(playerMalletX < 30) {
                    playerMalletX = 30;
                }
                if(playerMalletX > 454) {
                    playerMalletX = 454;
                }
                System.out.println(playerMalletX + "," + playerMalletY);
                repaint(); // Request a repaint whenever mouse moves
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawLine(0,440,500,440); // midline barrier
        g.drawLine(0,10,500,10); // goals
        g.drawLine(0,883,500,883);
        g.setColor(Color.darkGray);
        g.fillRect((500/7)*2,0,(500/7)*3,10);
        g.fillRect((500/7)*2,883,(500/7)*3,10);
        g.setColor(Color.RED); // Set color to red
        g.fillOval(playerMalletX - 30, playerMalletY - 30, 60, 60); // Draw mallet centered at mouse position
    }

}
