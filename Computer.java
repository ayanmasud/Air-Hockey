import java.awt.Color;
import java.awt.Graphics;

public class Computer implements Runnable{
	int compx;
	int compy;
	float compangle;
	float compSpeed;
	Table table;
	
	public Computer(Table table) {
		this.table = table;

		compx = 100;
		compy = 500;
		compangle = 0;
		compangle =0;
		
		Thread compThread = new Thread(this);
		compThread.start();
	}

	@Override
	public void run() {
		while (true) {
			move();

			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void move() {

		
	}
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(compx-25, compy-25, 60, 60);
	}
}
