package website.html;

import java.util.Calendar;
import java.util.Date;

public class DateHtlm {

	public  static String getDateHtml(Date dateJava){
		
	Calendar cal=Calendar.getInstance();
	cal.setTime(dateJava);
	int Heure=cal.get(Calendar.HOUR_OF_DAY);
	int minutes=cal.get(Calendar.MINUTE);
	int annee=cal.get(Calendar.YEAR);
	int jour=cal.get(Calendar.DAY_OF_MONTH);
	int mois=cal.get(Calendar.MONTH);
	
	return "new Date("+annee+","+mois+","+jour+","+Heure+","+minutes+")";
	}
}
