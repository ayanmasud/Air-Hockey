import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JPanel implements Runnable {

    int playerMalletX = 250;
    int playerMalletY = 800;
    float playerMalletSpeed = 0;
    Puck puck;
    Computer comp;
    AirHockey airhockey;
    public Table(AirHockey airhockey) {
        super();
        puck = new Puck(this, airhockey);
        comp = new Computer(this);
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                playerMalletX = e.getX();
                playerMalletY = e.getY();
                
                if(PlayerHitPuck()) {
                    puck.move();
                }
                
                
                
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
                //System.out.println(playerMalletX + "," + playerMalletY);
                repaint();
            }
        });
        Thread playerMalletThread = new Thread(this);
        playerMalletThread.start();
    }

    public boolean PlayerHitPuck() {
        double distance = Math.sqrt(Math.pow(playerMalletX - puck.puckX, 2) + Math.pow(playerMalletY - puck.puckY, 2));
        if(distance <= 55 && playerMalletY > 440) {
            return true;
        }
        return false;
    }
    public boolean CompHitPuck() {
    	double distance = Math.sqrt(Math.pow(comp.compx - puck.puckX, 2) + Math.pow(comp.compy - puck.puckY, 2));

    	if(distance <= 55 ) {
            return true;
        }
    	return false;
    }
    @Override
    public void run() { // used for determining how fast the player is moving
        while (true) {
            int posx = playerMalletX;
            int posy = playerMalletY;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int posxChange = Math.abs(playerMalletX-posx);
            int posyChange = Math.abs(playerMalletY-posy);
            double distance = Math.sqrt(Math.pow(posxChange, 2) + Math.pow(posyChange, 2)); // compares current position from position 20 millis ago
            playerMalletSpeed = (float)(distance);
        }
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
        puck.draw(g); // Draw the puck
        comp.draw(g);
    }

}
