package carpediem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import servlet.ImportCarpeDiem;
import website.dao.ActiviteDAO;
import website.metier.ActiviteCarpeDiem;

public class ImportCarpe {
	private static final Logger LOG = Logger.getLogger(ImportCarpe.class);

	ArrayList<ActiviteCarpeDiem> listActivite = new ArrayList<>();
	HashMap<String, ActiviteCarpeDiem> mapActivite = new HashMap<String, ActiviteCarpeDiem>();
	ActiviteCarpeDiem activite;
	StringBuilder log = new StringBuilder();
	String tokenFb;
	ArrayList<ActiviteCarpeDiem> listFinal = new ArrayList<>();

	public void importActivitesByPageNew(String ville, String tokenfb,
			int nbrJour) {

		this.tokenFb = tokenfb;
		listFinal.clear();
		int page = 0;
		Integer status = 1;

		DateTime ajourdui = new DateTime();

		for (int nbrJours = 0; nbrJours < nbrJour; nbrJours++) {

			DateTime dateDapres = ajourdui.plusDays(nbrJours);
			String dateEventStr = ImportCarpeDiem.getFormatDate(dateDapres);

			do {

				try {
					LOG.info("*********************CHARGE ***********PAGE"
							+ ville + "N°page:" + page + " du" + dateEventStr);
					page++;
					String ur = "http://" + ville + ".carpediem.cd/events/?dt="
							+ dateEventStr;

					String post = "mode=load_content&page=" + page
							+ "&_csrf=getCsrf()";

					URL url = new URL(ur);
					URLConnection conn = url.openConnection();
					conn.addRequestProperty("User-Agent",
							"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

					conn.setDoOutput(true);
					OutputStreamWriter writer = new OutputStreamWriter(
							conn.getOutputStream());
					writer.write(post);
					writer.flush();

					StringBuilder parsedContentFromUrl = new StringBuilder();

					BufferedInputStream in = new BufferedInputStream(
							conn.getInputStream());
					int ch;

					while ((ch = in.read()) != -1)
						parsedContentFromUrl.append((char) ch);

					JSONObject json = new JSONObject(
							parsedContentFromUrl.toString());

					status = (Integer) json.get("status");

					String reponse = (String) json.get("html");

					if (status == 1)
						charge(reponse);

				}

				catch (Exception e) {

					LOG.error(ExceptionUtils.getStackTrace(e));
				}

			} while (status == 1 && page < 30);

		}

		int g = 0;

		for (ActiviteCarpeDiem activiteCarpe : mapActivite.values()) {
			try {
				g++;
				LOG.info(ville + ":" + g + "/" + mapActivite.values().size());
				getDetailActivite(activiteCarpe);
				getListEvenementFaceBook(activiteCarpe.getIdEventFaceBook(),
						activiteCarpe);

			} catch (Exception e) {
				LOG.error("Detail activite non disponible");
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
		}

		g = 0;

		for (ActiviteCarpeDiem activiteCarpe : listFinal) {
			try {
				g++;
				LOG.info(ville + ":" + g + "/" + listFinal.size());

				ActiviteDAO.ajouteActiviteFaceBook(activiteCarpe);

			} catch (Exception e) {
				LOG.error("Detail activite non disponible");
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
		}

		listActivite.clear();
		mapActivite.clear();

	}

	public void getListEvenementFaceBook(String ideventfacebook,
			ActiviteCarpeDiem activite) throws IOException, JSONException {

		String urlString = "https://graph.facebook.com/v2.11/"
				+ ideventfacebook + "?access_token=" + tokenFb;

		StringBuilder parsedContentFromUrl = new StringBuilder();

		URL url = new URL(urlString);
		URLConnection uc;
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.connect();
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1)
			parsedContentFromUrl.append((char) ch);
		//

		JSONObject json = new JSONObject(parsedContentFromUrl.toString());

		// Recupere les dates

		boolean iseventtimes = false;
		try {

			JSONArray array = json.getJSONArray("event_times");

			for (int i = 0; i < array.length(); i++) {

				String datedebut = array.getJSONObject(i).getString(
						"start_time");
				String datefin = array.getJSONObject(i).getString("end_time");
				String idactiviteFB = array.getJSONObject(i).getString("id");
				ActiviteCarpeDiem tmp = new ActiviteCarpeDiem(activite);
				tmp.setIdEventFaceBook(ideventfacebook);
				tmp.setStartDate(datedebut);
				tmp.setEndDate(datefin);
				tmp.setIdactiviteFB(idactiviteFB);

				LOG.info("Ajoute activite");
				listFinal.add(tmp);

			}
			iseventtimes = true;

		} catch (Exception e) {

			iseventtimes = false;
			LOG.info("Pas de planification" + e);
		}

		// Si pas d'event time et quelle a ete valide on l'ajoute.

		try {
			if (!valideActivite(activite.getDateDebut(), activite.getDateFin())) {
				LOG.info("Activite ignorée");
				return;
			}
		} catch (ParseException e1) {
			LOG.info("Les dates ne n'ont pas été parsé dans la méthode getListEvenementFaceBook ");
			return;
		}

		if (!iseventtimes) {

			activite.setIdEventFaceBook(ideventfacebook);
			activite.setIdactiviteFB(ideventfacebook);
			listFinal.add(activite);
		}

	}

	public void charge(String sourcehtml) throws IOException {

		activite = new ActiviteCarpeDiem();

		Source source = new Source(sourcehtml);

		source.fullSequentialParse();

		List<Element> h2Elements = source.getAllElements("span");

		for (Element element : h2Elements) {

			instancieActivite(element, activite);

		}

	}

	public ImportCarpe() throws IOException {

	}

	private void getDetailActivite(ActiviteCarpeDiem activiteCarpe)
			throws IOException {

		StringBuilder parsedContentFromUrl = new StringBuilder();
		String urlString = activiteCarpe.getUrlCarpeDiem().replace(
				"carpediem.cd/events", "carpediem.cd/events/");
		URL url;

		url = new URL(urlString);
		URLConnection uc;
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.connect();
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1)
			parsedContentFromUrl.append((char) ch);

		String fulldescription = getFullDescrition(parsedContentFromUrl);

		activiteCarpe.setFulldescription(fulldescription);

		int start = parsedContentFromUrl.indexOf("id:");
		String numberStr = getNumber(parsedContentFromUrl, start);
		int id = Integer.parseInt(numberStr);

		start = parsedContentFromUrl.indexOf("lat:");
		numberStr = getNumber(parsedContentFromUrl, start);
		double lat = Double.parseDouble(numberStr);

		start = parsedContentFromUrl.indexOf("lng:");
		numberStr = getNumber(parsedContentFromUrl, start);
		double lng = Double.parseDouble(numberStr);

		String lienFaceBook = getLienFaceBook(parsedContentFromUrl);

		activiteCarpe.setId(id);
		activiteCarpe.setLat(lat);
		activiteCarpe.setLng(lng);
		activiteCarpe.setLienFaceBook(lienFaceBook);

		String idEventFaceBook = getIdEventFacebook(new StringBuilder(
				lienFaceBook));
		activiteCarpe.setIdEventFaceBook(idEventFaceBook);

	}

	private String getLienFaceBook(StringBuilder parsedContentFromUrl) {

		String chaine = "https://www.facebook.com/events/";
		int start = parsedContentFromUrl.indexOf(chaine) + 32;

		if (start == -1)
			return null;

		StringBuilder lien = new StringBuilder();
		String charactere;

		do {

			charactere = parsedContentFromUrl.substring(start, start + 1);
			lien.append(charactere);
			start++;

		} while (!charactere.equals("/"));

		return chaine + lien;
	}

	public String getFullDescritionold(StringBuilder parsedContentFromUrl) {

		String[] mo = convertISO85591(parsedContentFromUrl.toString()).split(
				"<br/>");
		String tmpdescription = "";
		for (int f = 1; f < mo.length - 1; f++) {

			tmpdescription = tmpdescription + mo[f];
		}
		String description = tmpdescription;
		int debutBalise = tmpdescription.indexOf("<a");
		int finBalise = tmpdescription.indexOf("</a>");

		if (debutBalise != -1 && finBalise != -1) {
			description = new StringBuilder(tmpdescription).delete(debutBalise,
					finBalise + 4).toString();

		}

		return description;
	}

	public static String getFullDescrition(StringBuilder parsedContentFromUrl) {

		String[] mo = convertISO85591(parsedContentFromUrl.toString()).split(
				"<br/>");
		String tmpdescription = "";
		if (mo.length == 0)
			return "Pas de description";

		for (int f = 0; f < mo.length; f++) {

			tmpdescription = tmpdescription + mo[f];
		}
		String description = tmpdescription;

		String[] mi = description.split("<div class=\"messages-list\"");

		String[] me = mi[0].split("</div>");

		return me[me.length - 1];
	}

	public void instancieActivite(Element element, ActiviteCarpeDiem activite) {

		StartTag startTag = element.getStartTag();
		Attributes attributes = startTag.getAttributes();
		Attribute idAttribute = attributes.get("itemprop");

		if (idAttribute == null)
			return;

		String attribute = idAttribute.getValue();

		switch (attribute) {

		case "startDate":

			activite.setStartDate(element.getContent().toString());

			break;

		case "endDate":

			activite.setEndDate(element.getContent().toString());

			break;
		case "image":

			activite.setUrlPhotoFB((element.getContent().toString()));

			break;
		case "description":

			activite.setDescription(element.getContent().toString());
	
			break;
		case "url":

			activite.setUrlCarpeDiem(element.getContent().toString());
			break;

		case "name":

			activite.setName(element.getContent().toString());

			break;
		case "address":
			activite.setAddress(element.getContent().toString());

			break;

		default:

		}
		if (activite.isComplete()) {

			mapActivite.put(activite.getUrlCarpeDiem(), new ActiviteCarpeDiem(
					activite));
			activite.reset();
			return;
		}

	}

	public static String convertISO85591(String chaine) {

		try {

			return new String(chaine.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return "Conversion impossible";
		}
	}

	private String getNumber(StringBuilder chaine, int start) {

		boolean debut = false;
		String retour = "";
		for (int f = start; f < start + 50; f++) {

			String nombre = String.valueOf(chaine.charAt(f));

			if (nombre.equals(".")) {
				retour = retour + nombre;
				continue;
			}
			if (nombre.equals("-")) {
				retour = retour + nombre;
				continue;
			}

			try {

				int testConvert = Integer.parseInt(nombre);

				retour = retour + nombre;
				if (!debut)
					debut = true;

			} catch (Exception e) {

				if (debut )
					return retour;
			}

		}

		return retour;
	}

	private String getIdEventFacebook(StringBuilder chaine) {

		boolean debut = false;
		String retour = "";
		int start = 0;
		for (int f = start; f < start + 2000; f++) {

			String nombre = String.valueOf(chaine.charAt(f));

			try {

				int testConvert = Integer.parseInt(nombre);

				retour = retour + nombre;
				if (debut == false)
					debut = true;

			} catch (Exception e) {

				if (debut == true)
					return retour;
			}

		}

		return retour;
	}

	public void detailNew(ActiviteCarpeDiem activiteCarpe) throws IOException {

		String ur = activiteCarpe.getUrlCarpeDiem().replace(
				"carpediem.cd/events", "carpediem.cd/events/");

		URL url = new URL(ur);
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");

		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream());

		writer.flush();

		StringBuilder parsedContentFromUrl = new StringBuilder();

		BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
		int ch;
		while ((ch = in.read()) != -1)
			parsedContentFromUrl.append((char) ch);

	}

	public static boolean valideActivite(Date dateDebut, Date dateFin) {

		DateTime maitenant = new DateTime().withHourOfDay(0)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(00);

		if (dateFin.before(maitenant.toDate()))
			return false;

		long Heure = 3600000;

		if (dateDebut.after(maitenant.plusDays(3).toDate()))
			return false;
		
		if (dateFin.getTime() - dateDebut.getTime() > 24 * Heure)
			return false;

		else

			return true;

	}
	
	public static boolean valideActivite(ActiviteCarpeDiem activite) throws ParseException {

		DateTime maitenant = new DateTime().withHourOfDay(0)
				.withMinuteOfHour(0).withSecondOfMinute(0)
				.withMillisOfSecond(00);

		if (activite.getDateDebut().before(maitenant.toDate()))
			return false;

		long Heure = 3600000;

		if (activite.getDateFin().getTime() - activite.getDateDebut().getTime() > 24 * Heure)
			return false;

		else

			return true;

	}


	public StringBuilder getSourceHtml(String urlString) throws IOException {

		StringBuilder parsedContentFromUrl = new StringBuilder();
		URL url;

		url = new URL(urlString);
		URLConnection uc;
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.connect();
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1)
			parsedContentFromUrl.append((char) ch);

		return parsedContentFromUrl;

	}
}
