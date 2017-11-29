package website.html;

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
}
