import java.awt.*;
import java.util.Random;

public class Puck implements Runnable{

    int puckX;
    int puckY;
    float puckAngle;
    float puckSpeed;
    Table table;

    public Puck(Table table) {
        this.table = table;
        // Initialize puck variables
        puckX = 250;
        puckY = 650;
        puckAngle = 0;
        puckSpeed = 0;

        Thread puckThread = new Thread(this);
        puckThread.start();
    }

    @Override
    public void run() {
        while (true) {
            move();
            try {
                Thread.sleep(20); // Control the speed of puck updates
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move() {
        // Check for puck hitting the player's mallet
        if (table.PlayerHitPuck()) {
            //System.out.println("hit");
            // Calculate angle between the mallet and the puck
            puckAngle = (float) Math.atan2(puckY - table.playerMalletY, puckX - table.playerMalletX);
            puckSpeed /= 1.3; // slows down after hitting
            puckSpeed += (puckSpeed/4)*(table.playerMalletSpeed/4); // puck speed with player speed
        }

        // Move the puck
        puckX += puckSpeed * Math.cos(puckAngle);
        puckY += puckSpeed * Math.sin(puckAngle);

        // collisions with table boundaries
        if (puckX < 25 || puckX > 460) {
            puckAngle = (float) Math.PI - puckAngle; // Reflect angle horizontally
            puckX = Math.max(25, Math.min(460, puckX));
            puckSpeed /= 1.3; // slows down after hitting
        }
        if (puckY < 35 && puckX < (500 / 7) * 2 || // allowing the puck to enter the goal (didnt include the puck radius yet)
                puckY < 35 && puckX > (500 / 7) * 5 ||
                puckY > 858 && puckX < (500 / 7) * 2 ||
                puckY > 858 && puckX > (500 / 7) * 5) {
            puckAngle = -puckAngle; // Reflect angle vertically
            puckY = Math.max(35, Math.min(858, puckY));
            puckSpeed /= 1.3; // slows down after hitting
        }

        // friction to slow down the puck over time
        puckSpeed *= 0.999;

        // random rotation and speed
        if (puckSpeed < 0.3) {
            Random rand = new Random();
            double changeAngle = rand.nextDouble(-0.0872665,0.0872665);
            double changeSpeed = rand.nextDouble(-0.2, 0.2);

            puckAngle += changeAngle;
            puckSpeed += changeSpeed;
        }

        table.repaint(); // repaint the table to update puck position
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(puckX - 25, puckY - 25, 50, 50);
    }

}
