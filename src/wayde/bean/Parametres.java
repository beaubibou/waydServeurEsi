package wayde.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class Parametres {
	private static final Logger LOG = Logger.getLogger(Parametres.class);

	public static SimpleDateFormat formatDateWs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static String getStringWsFromDate (Date date){
		if (date==null)
			return "Pas de date";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formater.format(date);
	}
	
	public static Date getDateFromString(String dateStr)  {
        String lFormatTemplate = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat lDateFormat = new SimpleDateFormat(lFormatTemplate);
        try {
			return lDateFormat.parse(dateStr);
		} catch (ParseException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
		
		}
		return new Date();

    }
	
	
	
}
