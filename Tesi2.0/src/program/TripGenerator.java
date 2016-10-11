package program;
import java.io.FileNotFoundException;

import persistence.TripWriter;

public class TripGenerator {
	public static void main (String[] args) {
		try {
			TripWriter fg = new TripWriter();
			//fg.generateFileSummer();
			fg.generateFileLesson();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("fatto");
	}
}
