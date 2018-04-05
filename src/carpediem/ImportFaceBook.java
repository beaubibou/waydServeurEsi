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
	private int total;
	private int courant;

	public ImportFaceBook(String idEvent, String tokenFb, int total, int courant) {

		super();
		this.idEvent = idEvent;
		this.tokenFb = tokenFb;
		this.total = total;
		this.courant = courant;

	}

	@Override
	public void run() {

		LOG.info("*********    Acquisition ****** EventId:" + idEvent + " :"
				+ courant + "/" + total);

		String urlString = "https://graph.facebook.com/v2.11/" + idEvent
				+ "?access_token=" + tokenFb;

		StringBuilder parsedContentFromUrl = new StringBuilder();

		URL url;
		String description = null;
		String titre = null;
		String ville = null;
		String pays = null;
		double latitude;
		double longitude;
		String rue = null;
		String codePostal = null;
		String adresseTotal;
		JSONObject json;
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
		}

		catch (IOException e1) {
			LOG.error("Url face BOOK non valide: activité rejetée");
			return;
		}

		//
		try {
			json = new JSONObject(parsedContentFromUrl.toString());
		} catch (JSONException e1) {
			LOG.error("Le parcours du json de l'évenemt FB a échoué: activité rejetée");
			return;

		}

		try {
			description = json.getString("description");
		} catch (JSONException e2) {

			LOG.error("La description est non présente");

		}
		try {
			titre = json.getString("name");
		} catch (JSONException e1) {

			LOG.error("La titre est non présent");

		}

		JSONObject place = null;
		try {
			place = json.getJSONObject("place");
		} catch (JSONException e1) {

			LOG.error("L'objet place n'est pas présent dans le JSON: activité rejetée");
			return;
		}

		try {
			nomLieu = place.getString("name");
		} catch (JSONException e1) {

			LOG.error("Le nom du site n'est pas présent dans le JSON");
		}

		JSONObject location = null;
		try {
			location = place.getJSONObject("location");
		} catch (JSONException e1) {

			LOG.error("L'objet location n'est pas présent dans le JSON: activité rejetée");
			return;
		}

		try {
			ville = location.getString("city");
		} catch (JSONException e1) {

			LOG.error("La ville n'est pas présente dans le JSON");

		}
		try {
			pays = location.getString("country");
		} catch (JSONException e1) {

			LOG.error("Le pays  n'est pas présente dans le JSON");

		}

		try {
			latitude = location.getDouble("latitude");
		} catch (JSONException e1) {

			LOG.error("La latitude  n'est pas présente dans le JSON: activité rejetée");
			return;
		}
		try {
			longitude = location.getDouble("longitude");
		} catch (JSONException e1) {

			LOG.error("La longitude  n'est pas présente dans le JSON: activité rejetée");
			return;
		}

		try {
			rue = location.getString("street");
		} catch (JSONException e1) {

			LOG.error("La rue  n'est pas présente dans le JSON");
		}

		try {
			codePostal = location.getString("zip");
		} catch (JSONException e1) {

			LOG.error("Le code postal  n'est pas présente dans le JSON");

		}

		adresseTotal = rue + " " + codePostal + " " + ville;

		String urlPhoto = getUrlPhotoFB(idEvent);

		if (urlPhoto == null) {

			LOG.error("La photo  n'est pas pas disponilbe");
			return;
		}

		boolean iseventtime = false;
		try {

			JSONArray array = json.getJSONArray("event_times");

			for (int i = 0; i < array.length(); i++) {

				String datedebut = array.getJSONObject(i).getString(
						"start_time");
				String datefin = array.getJSONObject(i).getString("end_time");
				String idactivite = array.getJSONObject(i).getString("id");
				ActiviteCarpeDiem activite = new ActiviteCarpeDiem(datedebut,
						datefin, image, description, titre, adresseTotal,
						nomLieu, ville, latitude, longitude, idactivite,
						urlPhoto, idEvent);

				LOG.info("Tentative Ajoute activite");

				website.dao.ActiviteDAO.ajouteActiviteFaceBook(activite);

			}
			iseventtime = true;

		} catch (Exception e) {

			iseventtime = false;
			LOG.info("Pas de planification de prévue pour cette activite");
		}

		LOG.info("iseve" + iseventtime);

		// Si pas d'event time et quelle a ete valide on l'ajoute.

		if (!iseventtime) {

			try {
				String datedebut = json.getString("start_time");
				String datefin = json.getString("end_time");
				ActiviteCarpeDiem activite = new ActiviteCarpeDiem(datedebut,
						datefin, image, description, titre, adresseTotal,
						nomLieu, ville, latitude, longitude, idEvent, urlPhoto,
						idEvent);

				LOG.info("Ajoute activite alone sans events "+activite.toString());

				website.dao.ActiviteDAO.ajouteActiviteFaceBook(activite);

			} catch (JSONException | IOException e) {

				LOG.info("Pas de planification");
			}

		}
	}

	private String getUrlPhotoFB(String idEvent) {

		JSONObject json = null;
		String urlString = "https://graph.facebook.com/v2.11/" + idEvent
				+ "/?access_token=" + tokenFb + "&fields=cover";

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

			urlPhoto = cover.getString("source");
			// System.out.println(urlPhoto);

			return urlPhoto;

		} catch (IOException | JSONException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		}

	}

}
