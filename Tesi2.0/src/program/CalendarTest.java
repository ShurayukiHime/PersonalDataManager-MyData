package program;

import java.time.LocalDateTime;

import model.*;
import persistence.MyData;

public class CalendarTest {
	
	public static void main (String[] args) {
		ICalendar calendar = MyData.getInstance().getDataVault("prova").getCalendar();
		System.out.println(calendar.getCommitmentByDate(LocalDateTime.of(2016,9,8,15,30)));
	}
	
}
