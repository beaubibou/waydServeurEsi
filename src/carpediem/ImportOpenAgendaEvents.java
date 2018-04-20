package carpediem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import website.metier.Outils;

public class ImportOpenAgendaEvents implements Runnable {

	String token;
	private String when;

	private EvenementOpenAGenda evenement;
	private static final Logger LOG = Logger
			.getLogger(ImportOpenAgendaEvents.class);
	private static String REQUETE_EVENT = "https://api.openagenda.com/v1/events?key=token&offset=monoffset&limit=malimite&when=monwhen";

	public ImportOpenAgendaEvents(String tokenFb, String when) {

		super();
		this.token = tokenFb;
		this.when = when;

	}

	@Override
	public void run() {

		String urlString = REQUETE_EVENT.replace("token", token)
				.replace("monoffset", "1").replace("malimite", "10")
				.replace("monwhen", when);
		JSONObject json;
		json = Outils.getJsonFromUrl(urlString);

		LOG.info(json);

		getEvenements(json);

	}

	public void getEvenements(JSONObject json) {

		try {

			String uidEvenement = null;
			String description = null;
			String titre = null;
			String ville = null;
			String lienurl = null;
			double latitude = 0;
			double longitude = 0;
			String codePostal = null;
			String adress = null;
			String freetext = null;
			String image = null;
			String nomLieu = null;
			JSONObject titreJSON;
			JSONObject descriptionJSON;
			JSONObject freeTextJSON;

			JSONArray arrayData = json.getJSONArray("data");

			for (int index = 0; index < arrayData.length(); index++) {

				JSONObject data = arrayData.getJSONObject(index);

				uidEvenement = data.getString("uid");

				titreJSON = data.getJSONObject("title");
				titre = titreJSON.getString("fr");
				descriptionJSON = data.getJSONObject("description");
				description = descriptionJSON.getString("fr");
				freeTextJSON = data.getJSONObject("freeText");
				freetext = freeTextJSON.getString("fr");

				JSONArray arrayLocations = data.getJSONArray("locations");
				nomLieu = arrayLocations.getJSONObject(0)
						.getString("placename");
				ville = arrayLocations.getJSONObject(0).getString("city");
				latitude = arrayLocations.getJSONObject(0)
						.getDouble("latitude");
				longitude = arrayLocations.getJSONObject(0).getDouble(
						"longitude");
				adress = arrayLocations.getJSONObject(0).getString("address");
				codePostal = arrayLocations.getJSONObject(0).getString(
						"postalCode");

				LOG.info("uid:" + uidEvenement);
				LOG.info("TITRE:" + titre);
				LOG.info("Description:" + description);
				LOG.info("freeText:" + freetext);
				LOG.info("nomlieu:" + nomLieu);
				LOG.info("ville:" + ville);
				LOG.info("code postal:" + codePostal);
				LOG.info("adresseTotal:" + adress);
				LOG.info("latitude:" + latitude);
				LOG.info("longitude:" + longitude);
				String adresseTotal = adress + " " + codePostal + " " + ville;

				evenement = new EvenementOpenAGenda("0", uidEvenement, titre,
						description, freetext, adresseTotal, latitude,
						longitude, image, lienurl, ville, nomLieu);

				JSONArray array = json.getJSONArray("dates");

				for (int i = 0; i < array.length(); i++) {

					String date = array.getJSONObject(i).getString("date");
					String timeStart = array.getJSONObject(i).getString(
							"timeStart");
					String timeEnd = array.getJSONObject(i)
							.getString("timeEnd");
					Date dateDebut = getDate(date, timeStart);
					Date dateFin = getDate(date, timeEnd);
					evenement.ajouteEvenement(dateDebut, dateFin);
				}

			}

		//	evenement.ajouteDAO();

		}

		catch (Exception e) {

			LOG.error(e.getMessage());
		}

	}

	public Date getDate(String date, String heure) throws ParseException {

		String datetmp = date + " " + heure;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(datetmp);

	}

}
