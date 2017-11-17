package website.html;

import javax.servlet.http.HttpServletRequest;

public class OutilsHtml {
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
