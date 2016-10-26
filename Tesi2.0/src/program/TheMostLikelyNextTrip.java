package program;

import javax.swing.JFrame;

import gui.*;

public class TheMostLikelyNextTrip {
	public static void main (String[] args) {
		//MainFrame frame = new MainFrame(new Controller("prova"));
		//frame.setVisible(true);
		
		MyDataProfile loginFrame = new MyDataProfile(new MyController ());
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setVisible(true);
		loginFrame.setSize(400, 400);
	}
}
