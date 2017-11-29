package website.html;

import org.apache.log4j.Logger;

import wayde.dao.ActiviteDAO;
import website.enumeration.MenuEnum;



public class MenuHtml {
	private static final Logger LOG = Logger.getLogger(MenuHtml.class);

	public static String getActiviteClass(MenuEnum menuItem,MenuEnum EnCours) {
		
	
	if (menuItem==EnCours)return "class='active'";
		
	return "";
	}
	
}
