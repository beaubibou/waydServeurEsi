package website.metier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.axis.encoding.Base64;

import sun.misc.BASE64Encoder;

public class Outils {

	public static int nbrLigneParPage = 6;
	public static int nbrMaxPagination = 8;


	public static String jspAdapterCheked(boolean value){
		
		if (value)return "checked";
		return "";
		
	}
	
public static String jspAdapterListSelected(int selectedValue,int value){
		
		if (value==selectedValue)return "selected";
		return "";
		
	}
	
	
	public static Date getDateFromString(String datestr) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date d = sdf.parse(datestr);
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(d);
		System.out.println(datestr);
		System.out.println(d);

		return caldate.getTime();
	}

	public static String getStringWsFromDate(Date date) {
		if (date == null)
			return "Pas de date";
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yy HH:mm");
		String datestr = formater.format(date);
		return datestr;
	}

	public static boolean getBooleanValueOf(Object object) {
		if (object == null)
			return false;
		return true;
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
		
		return retour.toString();
	}
	
	public static String getUrlPhoto(String photo) {

		if (photo==null)photo="";
		byte[] bytes = Base64.decode(photo);
		String urlPhoto = "data:image/jpeg;base64," + Base64.encode(bytes);

		return urlPhoto;
	}
	
	public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
 
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
	
	public String jspAdapterCheked(Boolean value){
		if (value==null)return "";
		
		if (value.booleanValue()==false)
			return "";
		
		return "checked";
		
	}

	public static String getStringStatement(String chaine) {
		// TODO Auto-generated method stub
		if (chaine.length()==0) return null;
		if (chaine==null)return null;
		return chaine;
	}
	
}
