package website.metier;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

public class Outils {
	private static final Logger LOG = Logger.getLogger(Outils.class);

	public static int nbrLigneParPage = 6;
	public static int nbrMaxPagination = 8;
	public static boolean clemapadmin = true;
	public static String mapKeyProduction = "AIzaSyA_K_75z5BiALmZbNnEHlP7Y7prhXd-vAc";
	public static String mapKeyTest = "AIzaSyD_kmPW9DeHyzFFU4wO1VT-PGYjYkAPvho";

	public static String getCleMap() {

		if (clemapadmin)
			return mapKeyProduction;

		return mapKeyTest;
	}

	public static JSONObject getJsonFromUrl(String urlString) {

		StringBuilder parsedContentFromUrl = new StringBuilder();

		LOG.info(urlString);

		URL url;
		JSONObject json;

		try {
			url = new URL(urlString);
			URLConnection uc;
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			uc.getInputStream();
			BufferedInputStream in = new BufferedInputStream(
					uc.getInputStream());
			int ch;
			while ((ch = in.read()) != -1)
				parsedContentFromUrl.append((char) ch);
		}

		catch (IOException e1) {
			LOG.error("Url face BOOK non valide: activité rejetée");

			return null;
		}

		try {
			json = new JSONObject(parsedContentFromUrl.toString());
		} catch (JSONException e1) {
			LOG.error("Le parcours du json de l'évenemt FB a échoué: activité rejetée");
			return null;

		}

		return json;
	}

	public static String jspAdapterCheked(boolean value) {

		if (value)
			return "checked";

		return "";

	}

	public static String jspAdapterListSelected(int selectedValue, int value) {

		if (value == selectedValue)
			return "selected";
		return "";

	}

	public static Date getDateFromString(String datestr) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date d = sdf.parse(datestr);
		Calendar caldate = Calendar.getInstance();
		caldate.setTime(d);

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

		if (photo == null)
			photo = "";
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

			LOG.error(ExceptionUtils.getStackTrace(e));
		}
		return imageString;
	}

	public String jspAdapterCheked(Boolean value) {
		if (value == null)
			return "";

		if (value.booleanValue() == false)
			return "";

		return "checked";

	}

	public static String getStringStatement(String chaine) {

		if (chaine == null)
			return null;

		if (chaine.length() == 0) {

			return null;
		}

		return chaine;
	}

	public static BufferedImage getImageFromURL(String photoUrl) {

		BufferedImage imageTailleNormale = null;
		URLConnection uc;
		URL url;
		try {
			url = new URL("https:"+photoUrl);
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			imageTailleNormale = ImageIO.read(uc.getInputStream());

		} catch (Exception e) {
			LOG.error(photoUrl);
			e.printStackTrace();
			return null;
		}

		return imageTailleNormale;

	}
	
	public static BufferedImage getImageMapodoFromURL(String photoUrl) {

		BufferedImage imageTailleNormale = null;
		URLConnection uc;
		URL url;
		try {
			url = new URL(photoUrl);
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			imageTailleNormale = ImageIO.read(uc.getInputStream());

		} catch (Exception e) {
			LOG.error(photoUrl);
			e.printStackTrace();
			return null;
		}

		return imageTailleNormale;

	}

}
