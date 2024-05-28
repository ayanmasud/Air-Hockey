import java.awt.*;
import java.util.Random;

public class Puck implements Runnable{

    int puckX;
    int puckY;
    float puckAngle;
    float puckSpeed;
    float maxPuckSpeed = 10;
    Table table;
    Computer computer;
    AirHockey airhockey;

    public Puck(Table table, AirHockey airhockey) {
    	this.airhockey = airhockey;
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
            
            if(puckSpeed > maxPuckSpeed) { // ensures puck speed is never too high
            	puckSpeed = maxPuckSpeed;
            }
        }
        if(table.CompHitPuck()) {
        	
        	//puckAngle = (float) Math.atan2(puckY - table.playerMalletY, puckY -table.playerMalletX);
        	//puckSpeed /= 1.3;
        	//puckSpeed += (puckSpeed/4)*(computer.compSpeed/4); 
        	//if(puckSpeed > maxPuckSpeed) {
        		//puckSpeed = maxPuckSpeed;
        	//}
        	
        	
        }

        // Move the puck
        puckX += puckSpeed * Math.cos(puckAngle);
        puckY += puckSpeed * Math.sin(puckAngle);
        
        double distance = Math.sqrt(Math.pow(table.playerMalletX - puckX, 2) + Math.pow(table.playerMalletY - puckY, 2)); // ensures no overlaps occur when the puck is hit by player mallet
        if(distance < 55 && table.playerMalletY > 440) {
        	puckAngle = (float) Math.atan2(puckY - table.playerMalletY, puckX - table.playerMalletX);
            puckSpeed /= 1.3; // slows down after hitting
            puckSpeed += (puckSpeed/4)*(table.playerMalletSpeed/4); // puck speed with player speed
            if(puckSpeed > maxPuckSpeed) { // ensures puck speed is never too high
            	puckSpeed = maxPuckSpeed;
            }
        	double puckMoveX = Math.cos(puckAngle)*55;
        	double puckMoveY = Math.sin(puckAngle)*55;
        	puckX = (int)(table.playerMalletX + puckMoveX);
            puckY = (int)(table.playerMalletY + puckMoveY);
        }

        // collisions with table boundaries
        if (puckX < 25 || puckX > 460) {
            puckAngle = (float) Math.PI - puckAngle; // Reflect angle horizontally
            puckX = Math.max(25, Math.min(460, puckX));
            puckSpeed /= 1.3; // slows down after hitting
        }
        if (puckY < 35 && puckX < (500 / 7) * 2 + 25 || // allowing the puck to enter the goal (didnt include the puck radius yet)
                puckY < 35 && puckX > (500 / 7) * 5 - 25 ||
                puckY > 858 && puckX < (500 / 7) * 2 + 25 ||
                puckY > 858 && puckX > (500 / 7) * 5 - 25) {
            puckAngle = -puckAngle; // Reflect angle vertically
            puckY = Math.max(35, Math.min(858, puckY));
            puckSpeed /= 1.3; // slows down after hitting
        }
        
        // puck inside goal
        if(puckY < 35 - 25) { // player scored!
        	airhockey.PlayerScored();
        	puckX = 250;
            puckY = 650;
            puckAngle = 0;
            puckSpeed = 0;
        }
        if(puckY > 858 + 25) { // computer scored!
        	airhockey.ComputerScored();
        	puckX = 250;
            puckY = 650;
            puckAngle = 0;
            puckSpeed = 0;
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
            if(puckSpeed > maxPuckSpeed) { // ensures puck speed is never too high
            	puckSpeed = maxPuckSpeed;
            }
        }

        table.repaint(); // repaint the table to update puck position
    }

    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(puckX - 25, puckY - 25, 50, 50);
    }

}
