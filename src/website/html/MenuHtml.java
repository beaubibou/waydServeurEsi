package website.html;

import website.enumeration.MenuEnum;



public class MenuHtml {

	public static String getActiviteClass(MenuEnum menuItem,MenuEnum EnCours) {
		
	System.out.println(menuItem+" "+EnCours);
	if (menuItem==EnCours)return "class='active'";
		
	return "";
	}
	
}
