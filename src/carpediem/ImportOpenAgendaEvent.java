package carpediem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import website.metier.Outils;

public class ImportOpenAgendaEvent implements Runnable {

	String token;
	private String idEvent;
	private String idagenda;
	private EvenementOpenAGenda evenement;
	private static final Logger LOG = Logger
			.getLogger(ImportOpenAgendaEvent.class);
	private static String REQUETE_EVENT = "https://api.openagenda.com/v1/events/idevent?key=token";

	public ImportOpenAgendaEvent(String tokenFb, String idEvent, String idagenda) {

		super();
		this.token = tokenFb;
		this.idEvent = idEvent;
		this.idagenda = idagenda;

	}

	@Override
	public void run() {

		LOG.info("*********    Acquisition *Evenemet OPEN AGENDA:" + idEvent);

		String urlString = REQUETE_EVENT.replace("idevent", idEvent).replace(
				"token", token);
		JSONObject json;
		json = Outils.getJsonFromUrl(urlString);
		LOG.info(json);
		getEvenemnt(json);

	}

	public void getEvenemnt(JSONObject json) {

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
		JSONObject data = null;
		JSONObject titreJSON;
		JSONObject descriptionJSON;
		JSONObject freeTextJSON;
		try {
			data = json.getJSONObject("data");
		} catch (JSONException e2) {

			LOG.error(e2.getMessage());
			LOG.error("La data est non présente");
		}

		try {
			uidEvenement = data.getString("uid");
		} catch (JSONException e2) {
		
			LOG.error(e2.getMessage());
			LOG.error("La uid est non présente");
		}

		try {
			
			titreJSON = data.getJSONObject("title");
			titre = titreJSON.getString("fr");
			descriptionJSON = data.getJSONObject("description");
			description = descriptionJSON.getString("fr");
			freeTextJSON = data.getJSONObject("freeText");
			freetext = freeTextJSON.getString("fr");

		} catch (JSONException e2) {
			
			LOG.error("La titre est non présente");
			LOG.error(e2.getMessage());

		}

		try {
			JSONArray array = data.getJSONArray("locations");
			nomLieu = array.getJSONObject(0).getString("placename");
			ville = array.getJSONObject(0).getString("city");
			latitude = array.getJSONObject(0).getDouble("latitude");
			longitude = array.getJSONObject(0).getDouble("longitude");
			adress = array.getJSONObject(0).getString("address");
			codePostal = array.getJSONObject(0).getString("postalCode");

		} catch (JSONException e2) {

			LOG.error(e2.getMessage());

		}

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

		evenement = new EvenementOpenAGenda(idagenda, uidEvenement, titre,
				description, freetext, adresseTotal, latitude, longitude,
				image, lienurl, ville, nomLieu);

		try {
		
			JSONArray array = json.getJSONArray("dates");
	
			for (int i = 0; i < array.length(); i++) {
		
				String date = array.getJSONObject(i).getString("date");
				String timeStart = array.getJSONObject(i)
						.getString("timeStart");
				String timeEnd = array.getJSONObject(i).getString("timeEnd");
				Date dateDebut = getDate(date, timeStart);
				Date dateFin = getDate(date, timeEnd);
				evenement.ajouteEvenement(dateDebut, dateFin);
			}

		} catch (JSONException | ParseException e2) {

			LOG.error("La dates est non présente");
			LOG.error(e2.getMessage());


		}

	}

	public Date getDate(String date, String heure) throws ParseException {

		String datetmp = date + " " + heure;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(datetmp);

	}

}
