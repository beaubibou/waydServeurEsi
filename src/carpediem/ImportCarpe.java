package carpediem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import net.htmlparser.jericho.Attribute;
import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.apache.commons.lang3.exception.ExceptionUtils;

import website.dao.ActiviteDAO;
import website.metier.ActiviteCarpeDiem;

public class ImportCarpe {

	ArrayList<ActiviteCarpeDiem> listActivite = new ArrayList<ActiviteCarpeDiem>();
	ActiviteCarpeDiem activite;
	StringBuilder log=new StringBuilder();
	
	public String importActivites(String date, String ville){
		
		activite = new ActiviteCarpeDiem();

		StringBuilder parsedContentFromUrl = new StringBuilder();

		String urlString = "http://" + ville + ".carpediem.cd/events/?dt="
				+ date;
		log.append("Recherche sur "+ville+ ":"+ date+"\n");
		log.append("Url de recherche+ "+urlString+"\n");
		URL url;
		try {
			url = new URL(urlString);
			URLConnection uc;
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			uc.getInputStream();

			BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
			int ch;
			while ((ch = in.read()) != -1) {
				parsedContentFromUrl.append((char) ch);

				Source source = new Source(
						convertISO85591(parsedContentFromUrl.toString()));

				source.fullSequentialParse();

				List<Element> h2Elements = source.getAllElements("span");

				for (Element element : h2Elements) {

					instancieActivite(element, activite);

				}

				for (ActiviteCarpeDiem activiteCarpe : listActivite) {
					System.out.println(activiteCarpe.toString());

					getDetailActivite(activiteCarpe);

					ActiviteDAO.ajouteActiviteCarpeDiem(activiteCarpe);

					System.out.println(activiteCarpe.toString());

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.append(ExceptionUtils.getStackTrace(e));
			return log.toString();
		}
		
		return log.toString();
		
	}
	
	public ImportCarpe() throws IOException {

		

	}

	private void getDetailActivite(ActiviteCarpeDiem activiteCarpe)
			throws IOException {

		StringBuilder parsedContentFromUrl = new StringBuilder();
		String urlString = activiteCarpe.getUrl();
		URL url = new URL(urlString);
		URLConnection uc;
		uc = url.openConnection();
		uc.addRequestProperty("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		uc.connect();
		uc.getInputStream();
		BufferedInputStream in = new BufferedInputStream(uc.getInputStream());
		int ch;
		while ((ch = in.read()) != -1) {
			parsedContentFromUrl.append((char) ch);

			int start = parsedContentFromUrl.indexOf("id:");
			String numberStr = getNumber(parsedContentFromUrl, start);
			int id = Integer.parseInt(numberStr);

			start = parsedContentFromUrl.indexOf("lat:");
			numberStr = getNumber(parsedContentFromUrl, start);
			double lat = Double.parseDouble(numberStr);

			start = parsedContentFromUrl.indexOf("lng:");
			numberStr = getNumber(parsedContentFromUrl, start);
			double lng = Double.parseDouble(numberStr);

			activiteCarpe.setId(id);
			activiteCarpe.setLat(lat);
			activiteCarpe.setLng(lng);

		}

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

			activite.setImage(element.getContent().toString());

			break;
		case "description":

			activite.setDescription(element.getContent().toString());
			break;
		case "url":

			activite.setUrl(element.getContent().toString());
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
			log.append("Ajote une activite "+"\n");
			listActivite.add(activite);
			activite = new ActiviteCarpeDiem();
		}
	}

	public static String convertISO85591(String chaine) {

		try {

			return new String(chaine.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return "Conversion impossible";
		}
	}

	private String getNumber(StringBuilder chaine, int start) {

		boolean debut = false;
		String retour = "";
		for (int f = start; f < start + 200; f++) {

			String nombre = String.valueOf(chaine.charAt(f));
			System.out.println(nombre);
			if (nombre.equals(".")) {
				retour = retour + nombre;
				continue;
			}

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

}
