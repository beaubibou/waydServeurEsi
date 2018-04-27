package carpediem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import website.metier.Outils;

public class ImportMapadoEvent implements Runnable {

	String ville;
	private String uuid;
	private String access_token;

	private EvenementOpenAGenda evenement;
	private static final Logger LOG = Logger.getLogger(ImportMapadoEvent.class);
	private static String REQUETE_EVENT = "https://api.mapado.com/v1/activities/monuuid?access_token=montoken";

	public ImportMapadoEvent(String acces_token, String uuid) {

		super();

		this.access_token = acces_token;
		this.uuid = uuid;

	}

	public void start() {

		{
			try {

				JSONObject json;
				String urlString = REQUETE_EVENT.replace("monuuid", uuid)
						.replace("montoken", access_token);
				LOG.info(urlString);
				json = Outils.getJsonFromUrl(urlString);
				getEvenement(json);

			} catch (Exception e) {
				LOG.error(e.getMessage());

			}
		}

	}

	public void getEvenement(JSONObject json) {

		String uidEvenement = null;
		String description = null;
		String titre = null;
		String ville = null;
		String lienurl = null;
		double latitude = 0;
		double longitude = 0;

		String adress = null;
		String freetext = null;
		String image = null;
		String nomLieu = null;
		String formatted_schedule = "";
		String datedebutstr = "";

		Date dateDebut = null, dateFin = null;

		try {
			nomLieu = json.getString("front_place_name");
		} catch (JSONException e) {
			LOG.error(e.getMessage());
		}

		try {
			uidEvenement = json.getString("uuid");
		} catch (JSONException e) {
			LOG.error(e.getMessage());
		}

		try {
			titre = json.getString("title");
		} catch (JSONException e) {
			LOG.error(e.getMessage());
		}

		try {

			formatted_schedule = json.getString("formatted_schedule");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			freetext = json.getString("description");

			freetext = freetext + "\n" + formatted_schedule;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			description = json.getString("short_description");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			datedebutstr = json.getString("next_date");
			dateDebut = getDate(datedebutstr);

			DateTime datefinDt = new DateTime(dateDebut.getTime());
			DateTime finale = datefinDt.withHourOfDay(23).withMinuteOfHour(59)
					.withSecondOfMinute(59);
			dateFin = finale.toDate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			image = json.getString("image");

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			JSONObject adressejson = json.getJSONObject("_embedded")
					.getJSONObject("address");
			LOG.info(adressejson);
			latitude = adressejson.getDouble("latitude");
			longitude = adressejson.getDouble("longitude");
			adress = adressejson.getString("formatted_address");

		} catch (Exception e) {
			e.printStackTrace();
		}

		// LOG.info("image:" + image);
		LOG.info("TITRE:" + titre);
		// LOG.info("Description:" + description);
		// LOG.info("freeText:" + freetext);
		// LOG.info("nomlieu:" + nomLieu);
		LOG.info("latitude:" + latitude);
		LOG.info("longitude:" + longitude);
		evenement = new EvenementOpenAGenda("0", uidEvenement, titre,
				description, freetext, adress, latitude, longitude, image,
				lienurl, ville, nomLieu);

		try {
			if (!valideActivite(dateDebut, dateFin))
				return;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		evenement.ajouteDAO(dateDebut, dateFin);

	}

	public static boolean valideActivite(Date debut, Date fin)
			throws ParseException {

		Date maitenant = new Date();

		if (fin.before(maitenant))
			return false;

		else

			return true;

	}

	public Date getDate(String date, String heure) throws ParseException {

		String datetmp = date + " " + heure;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(datetmp);

	}

	public String getFile() {

		BufferedReader br;
		StringBuilder retour = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader("donnees"));
			String line = null;

			while ((line = br.readLine()) != null) {

				retour.append(line);

			}
			br.close();

		} catch (Exception e) {

			LOG.error(e.getMessage());
		}
		return retour.toString();

	}

	@Override
	public void run() {
		start();
	}

	public Date getDate(String date) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return formatter.parse(date);

	}

}
