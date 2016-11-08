package program;

import java.io.IOException;

import javax.swing.JFrame;

import gui.*;
import model.MyLogger;

public class TheMostLikelyNextTrip {
	public static void main (String[] args)  {
		//MainFrame frame = new MainFrame(new Controller("prova"));
		//frame.setVisible(true);
		
		UserInteractor userInteractor = new SwingUserInteractor();
		try {
			MyLogger logger = new MyLogger();
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyDataProfile loginFrame = new MyDataProfile(new MyController (userInteractor));
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setVisible(true);
	}
}
