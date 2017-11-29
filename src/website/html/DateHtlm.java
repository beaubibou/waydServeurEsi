package website.html;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.dao.ActiviteDAO;

public class DateHtlm {
	private static final Logger LOG = Logger.getLogger(DateHtlm.class);

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
