package wayde.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Parametres {
	public static SimpleDateFormat formatDateWs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static String getStringWsFromDate (Date date){
		if (date==null)	 return "Pas de date";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datestr = formater.format(date);
		return datestr; 
	}
	
	public static Date getDateFromString(String dateStr)  {
        // String lFormatTemplate = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        String lFormatTemplate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat lDateFormat = new SimpleDateFormat(lFormatTemplate);
        try {
			return lDateFormat.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		
		}
		return new Date();

    }
	
	
	
}
