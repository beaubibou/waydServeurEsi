package carpediem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import website.metier.ActiviteCarpeDiem;

public class ImportFaceBook implements Runnable {

	private static final Logger LOG = Logger.getLogger(ImportFaceBook.class);
	String idEvent;
	String tokenFb;

	public ImportFaceBook(String idEvent, String tokenFb) {

		super();
		this.idEvent = idEvent;
		this.tokenFb = tokenFb;

	}

	@Override
	public void run() {

		String urlString = "https://graph.facebook.com/v2.11/" + idEvent
				+ "?access_token=" + tokenFb;

		StringBuilder parsedContentFromUrl = new StringBuilder();

		URL url;
		String description = null;
		String titre = null;
		String nomDuSite = null;
		String ville = null;
		String pays = null;
		Double latitude = (double) 0;
		Double longitude = (double) 0;
		String rue;
		String codePostal;
		String adresseTotal = null;
		JSONObject json = null;
		String image = null;
		String nomLieu = null;
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
			//
			json = new JSONObject(parsedContentFromUrl.toString());

			description = json.getString("description");
			titre = json.getString("name");

			JSONObject place = json.getJSONObject("place");

			nomDuSite = place.getString("name");

			JSONObject location = place.getJSONObject("location");

			ville = location.getString("city");
			pays = location.getString("country");

			latitude = location.getDouble("latitude");
			longitude = location.getDouble("longitude");

			rue = location.getString("street");

			codePostal = location.getString("zip");

			adresseTotal = rue + " " + codePostal + " " + ville;

			// Recupere les dates

			image = null;
			nomLieu = null;

		} catch (IOException | JSONException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		String urlPhoto = getUrlPhotoFB(idEvent);

		boolean isEvent_times = false;
		try {

			JSONArray array = json.getJSONArray("event_times");
			isEvent_times = true;

			for (int i = 0; i < array.length(); i++) {

				String datedebut = array.getJSONObject(i).getString(
						"start_time");
				String datefin = array.getJSONObject(i).getString("end_time");
				String idEvent = array.getJSONObject(i).getString("id");
				ActiviteCarpeDiem activite = new ActiviteCarpeDiem(datedebut,
						datefin, image, description, titre, adresseTotal,
						nomLieu, ville, latitude, longitude, idEvent,urlPhoto);

				LOG.info("Tentative Ajoute activite");

				website.dao.ActiviteDAO.ajouteActiviteFaceBook(activite);

			}

		} catch (Exception e) {

			LOG.info("Pas de planification" + e);
		}
		LOG.info("iseve" + isEvent_times);

		// Si pas d'event time et quelle a ete valide on l'ajoute.

		if (!isEvent_times) {

			try {
				String datedebut = json.getString("start_time");
				String datefin = json.getString("end_time");
				ActiviteCarpeDiem activite = new ActiviteCarpeDiem(datedebut,
						datefin, image, description, titre, adresseTotal,
						nomLieu, ville, latitude, longitude, idEvent,urlPhoto);

				LOG.info("Ajoute activite alone sans events ");

				website.dao.ActiviteDAO.ajouteActiviteFaceBook(activite);

			} catch (JSONException | IOException e) {

				LOG.info("Pas de planification" + e);
			}

		}
	}

	private String getUrlPhotoFB(String idEvent) {

		JSONObject json = null;
		String urlString = "https://graph.facebook.com/v2.11/" +	idEvent+			"/?access_token=" + tokenFb+"&fields=cover";

		StringBuilder parsedContentFromUrl = new StringBuilder();

		URL url;
		String urlPhoto = null;

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
			//
			json = new JSONObject(parsedContentFromUrl.toString());

			JSONObject cover = json.getJSONObject("cover");
			System.out.println("**************************************************************");

			urlPhoto=cover.getString("source");
			System.out.println(urlPhoto);

			return urlPhoto;

		} catch (IOException | JSONException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		}

	}

}
