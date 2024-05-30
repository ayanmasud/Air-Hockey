import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Table extends JPanel implements Runnable {

    int playerMalletX = 250;
    int playerMalletY = 800;
    float playerMalletSpeed = 0;

    int compX = 250;
    int compY = 100;
    float compAngle = 0;
    float compSpeed = 5;

    Puck puck;
    AirHockey airhockey;
    public Table(AirHockey airhockey) {
        super();
        puck = new Puck(this, airhockey);
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
        double distance = Math.sqrt(Math.pow(compX - puck.puckX, 2) + Math.pow(compY - puck.puckY, 2));

        if(distance <= 55) {
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
            if(playerMalletSpeed > 10) { // prevents it from being way too fast
                playerMalletSpeed = 10;
            }

            if(puck.puckY < 430) { // computer mallet will go chase the puck and hit it
                compAngle = (float) Math.atan2((puck.puckY-20) - compY, puck.puckX - compX);

                // Move the computer mallet
                compX += compSpeed * Math.cos(compAngle);
                compY += compSpeed * Math.sin(compAngle);

                if(compX < 30) { // restrictions to ensure computer mallet doesn't go off the table
                    compX = 30;
                }
                if(compY < 40) {
                    compY = 40;
                }
                if(compX > 454) {
                    compX = 454;
                }
                if(compY > 420) {
                    compY = 420;
                }
            }
            else { // go back to initial position (250, 100)
                if(compX != 250 && compY != compX) {
                    compAngle = (float) Math.atan2(100 - compY, 250 - compX);

                    // Move the computer mallet
                    compX += compSpeed * Math.cos(compAngle);
                    compY += compSpeed * Math.sin(compAngle);
                }
            }
            repaint(); // will be utilized for repainting the computer mallet
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

        g.setColor(Color.RED);
        g.fillOval(compX - 30, compY - 30, 60, 60);
    }

}
