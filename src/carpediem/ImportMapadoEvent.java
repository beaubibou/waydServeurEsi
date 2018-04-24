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
		// LOG.info(access_token);
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
				// LOG.error("erreur offest:" + offset);

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

		Date dateDebut = null, dateFin=null;

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

		}

		try {

			freetext = json.getString("description");

			freetext = freetext + "\n" + formatted_schedule;
		} catch (Exception e) {

		}

		try {

			description = json.getString("short_description");

		} catch (Exception e) {

		}

		try {

			datedebutstr = json.getString("next_date");
			dateDebut = getDate(datedebutstr);

			DateTime datefinDt = new DateTime(dateDebut.getTime());
			DateTime finale = datefinDt.withHourOfDay(23).withMinuteOfHour(59)
					.withSecondOfMinute(59);
			dateFin = finale.toDate();
			System.out.println(finale);
			// dateFin=
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			image = json.getString("image");

		} catch (Exception e) {

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

		System.out.println("image:" + image);
		System.out.println("TITRE:" + titre);
		System.out.println("Description:" + description);
		System.out.println("freeText:" + freetext);
		System.out.println("nomlieu:" + nomLieu);
		// System.out.println("ville:" + ville);
		// System.out.println("code postal:" + codePostal);
		// System.out.println("adresseTotal:" + adress);
		System.out.println("latitude:" + latitude);
		System.out.println("longitude:" + longitude);
		// String adresseTotal = adress + " " + codePostal + " " + ville;

		 evenement = new EvenementOpenAGenda("0", uidEvenement, titre,
		 description, freetext, adress, latitude, longitude, image, lienurl, ville, nomLieu);
		//
		// JSONArray array = null;
		//
		// try {
		// array = arrayLocations.getJSONObject(0).getJSONArray("dates");
		//
		// } catch (JSONException e) {
		// LOG.error(e.getMessage());
		// }
		// boolean ajout = false;
		// LOG.info("taille ajoute event" + array.length());
		//
		// for (int i = 0; i < array.length(); i++) {
		//
		// String date;
		// try {
		// date = array.getJSONObject(i).getString("date");
		// String timeStart = array.getJSONObject(i).getString(
		// "timeStart");
		// String timeEnd = array.getJSONObject(i)
		// .getString("timeEnd");
		// Date dateDebut = getDate(date, timeStart);
		// Date dateFin = getDate(date, timeEnd);
		 try {
			if (!valideActivite(dateDebut, dateFin)) return;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// } catch (JSONException | ParseException e) {
		//
		// LOG.info("Pas de date on passe");
		// continue;
		//
		// }
		// }
		//
		// LOG.info("ajoute dao");
		// if (ajout)
		 evenement.ajouteDAO(dateDebut,dateFin);
		// }

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

	@Override
	public void run() {
		start();
	}

	public Date getDate(String date) throws ParseException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		return formatter.parse(date);

	}

}
