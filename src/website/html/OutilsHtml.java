package website.html;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class OutilsHtml {
	private static final Logger LOG = Logger.getLogger(OutilsHtml.class);

	public static String convertRequeteToString(Object requetAttribute) {

		if (requetAttribute == null)
			return "";
		else

			return ((String) requetAttribute);

	}

	public static double convertRequeteToDouble(Object requetAttribute) {

		if (requetAttribute == null)
			return 0;
		else
			return (double) requetAttribute;
	}
	
	public static String convertStringHtml(String chaine){
		
		if (chaine == null ||chaine.isEmpty())
			return "";
		
		return chaine;
		
	}
	

	// Si la chaine est vide on m'est null dans la table
	public static String convertStringToNull(String chaine){
		
		if (chaine!=null)
			if (chaine.length()==0)
				return "";
		
		if (chaine==null)
			return("");
		
		return chaine;
		
	}
public static String convertISO85591(String chaine){
		
		if (chaine==null)
			return null;
	
		try {
			return  new String ( chaine.getBytes("ISO-8859-1"),"UTF-8"  );
		} catch (UnsupportedEncodingException e) {
		
			
			LOG.error(ExceptionUtils.getStackTrace(e));
			return "Conversion impossible";
		}
	}
}
