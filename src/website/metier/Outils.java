package website.metier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Outils {

	public static String getStringWsFromDate(Date date) {
		if (date == null)
			return "Pas de date";
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy HH:mm");
		String datestr = formater.format(date);
		return datestr;
	}

	public static String convertStatistiqueBeanToString(
			ArrayList<StatistiqueBean> listStatistiqueBean) {

		StringBuilder retour = new StringBuilder();
		retour.append("['Temps', 'Nbr'],");

		Collections.sort(listStatistiqueBean);

		// for (StatistiqueBean statistiqueBean : listStatistiqueBean) {
		// retour = retour.append("['");
		//
		// retour.append("',");
		// retour.append(statistiqueBean.getValeur());
		// retour = retour.append("],");
		// }

		for (int f = 0; f < listStatistiqueBean.size(); f++) {
			retour = retour.append("['");
			if (f == 0 || f == listStatistiqueBean.size() - 1)
				retour.append(listStatistiqueBean.get(f).getDateStr() + "',");
			else
				retour.append("',");
			retour.append(listStatistiqueBean.get(f).getValeur());
			retour = retour.append("],");
		}

		retour.delete(retour.length() - 1, retour.length());
		System.out.println(retour.toString());
		return retour.toString();
	}
}
