import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AirHockey implements ActionListener {

	JFrame frame = new JFrame();
    Table table = new Table(this);
    Container north = new Container();
    JLabel difficultyL = new JLabel("Difficulty:");
    JButton easyB = new JButton("Easy");
    JButton mediumB = new JButton("Medium");
    JButton hardB = new JButton("Hard");
    JLabel[] cPoints = new JLabel[11]; // Array to hold computers Points
    Container south = new Container();
    JLabel[] pPoints = new JLabel[11]; // Array to hold players Points
    int computerPoints = 0;
    int playerPoints = 0;

    public AirHockey() {
        frame.setSize(500, 1000);
        frame.setLayout(new BorderLayout());
        frame.add(table, BorderLayout.CENTER);

        // First row with 4 columns
        JPanel row1 = new JPanel(new GridLayout(1, 4));
        row1.add(difficultyL);
        row1.add(easyB);
        easyB.setBackground(Color.green);
        easyB.addActionListener(this);
        row1.add(mediumB);
        mediumB.setBackground(Color.lightGray);
        mediumB.addActionListener(this);
        row1.add(hardB);
        hardB.setBackground(Color.lightGray);
        hardB.addActionListener(this);

        // Second row with 11 columns
        JPanel row2 = new JPanel(new GridLayout(1, 11));
        for (int i = 0; i < 11; i++) {
            cPoints[i] = new JLabel(Integer.toString(i)); // Initialize cPoints
            cPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
            cPoints[i].setBackground(Color.BLACK); // Set background color to black
            cPoints[i].setForeground(Color.WHITE); // Set text color to white
            row2.add(cPoints[i]);
        }
        cPoints[0].setBackground(Color.red);

        north.setLayout(new GridLayout(2, 1)); // Two rows
        north.add(row1);
        north.add(row2);

        frame.add(north, BorderLayout.NORTH);

        south.setLayout(new GridLayout(1, 11)); // One row with 11 columns
        for (int i = 0; i < 11; i++) {
            pPoints[i] = new JLabel(Integer.toString(i)); // Initialize pPoints
            pPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
            pPoints[i].setBackground(Color.BLACK); // Set background color to black
            pPoints[i].setForeground(Color.WHITE); // Set text color to white
            south.add(pPoints[i]); // Add to south container
        }
        pPoints[0].setBackground(Color.red);

        frame.add(south, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        new AirHockey();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(easyB)) { // easy difficulty
            easyB.setBackground(Color.green);
            mediumB.setBackground(Color.lightGray);
            hardB.setBackground(Color.lightGray);

        }
        if(e.getSource().equals(mediumB)) { // medium difficulty
            mediumB.setBackground(Color.green);
            easyB.setBackground(Color.lightGray);
            hardB.setBackground(Color.lightGray);

        }
        if(e.getSource().equals(hardB)) { // hard difficulty
            hardB.setBackground(Color.green);
            easyB.setBackground(Color.lightGray);
            mediumB.setBackground(Color.lightGray);

        }
    }
    
    public void PlayerScored() {
    	for (int i = 0; i < 11; i++) { // reset all the player points
            pPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
            pPoints[i].setBackground(Color.BLACK); // Set background color to black
            pPoints[i].setForeground(Color.WHITE); // Set text color to white
        }
    	playerPoints += 1; // set it to the new score
    	pPoints[playerPoints].setBackground(Color.red);
    	
    	if(playerPoints == 10) {
    		JOptionPane.showMessageDialog(frame, "You Win!");
    		for (int i = 0; i < 11; i++) { // reset all the player points
                pPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
                pPoints[i].setBackground(Color.BLACK); // Set background color to black
                pPoints[i].setForeground(Color.WHITE); // Set text color to white
            }
        	playerPoints = 0; // set it to the new score
        	pPoints[playerPoints].setBackground(Color.red);
    	}
    }
    
    public void ComputerScored() {
    	for (int i = 0; i < 11; i++) { // reset all of the computer points
            cPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
            cPoints[i].setBackground(Color.BLACK); // Set background color to black
            cPoints[i].setForeground(Color.WHITE); // Set text color to white
        }
    	computerPoints += 1; // set it to the new score
    	cPoints[computerPoints].setBackground(Color.red);
    	
    	if(computerPoints == 10) {
    		JOptionPane.showMessageDialog(frame, "Computer Win!");
    		for (int i = 0; i < 11; i++) { // reset all of the computer points
                cPoints[i].setOpaque(true); // Set opaque to true to allow setting background color
                cPoints[i].setBackground(Color.BLACK); // Set background color to black
                cPoints[i].setForeground(Color.WHITE); // Set text color to white
            }
        	computerPoints = 0; // set it to the new score
        	cPoints[computerPoints].setBackground(Color.red);
    	}
    }
}
