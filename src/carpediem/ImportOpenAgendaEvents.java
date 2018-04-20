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

public class ImportOpenAgendaEvents {

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

	public void start() {

		boolean stop=false;
	int limit=100;
	int offset=1;
		while(!stop){
	
		try {
			
			LOG.info("OFFSET EN COURS");
			JSONObject json;
			
			String urlString = REQUETE_EVENT.replace("token", token)
					.replace("monoffset", Integer.toString(offset))
					.replace("malimite", Integer.toString(limit))
					.replace("monwhen", when);
			
//			json = Outils.getJsonFromUrl(urlString);
			json = new JSONObject(getFile());
			JSONArray arrayData = json.getJSONArray("data");
			if (arrayData.length()==0)
				{stop=true;}
			else
			{
				getEvenements(json);
				offset=offset+100;
			}
	
		} catch (JSONException e) {
			LOG.error(e.getMessage());
		}
		
	
		}
	}

	public void getEvenements(JSONObject json) {

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
		JSONArray arrayData = null;
		try {
			arrayData = json.getJSONArray("data");
		
		} catch (JSONException e2) {
			e2.printStackTrace();
		}

		for (int j = 0; j < arrayData.length(); j++) {

			JSONObject data = null;
			try {
				data = arrayData.getJSONObject(j);
				uidEvenement = data.getString("uid");
				titreJSON = data.getJSONObject("title");
				titre = titreJSON.getString("fr");
				descriptionJSON = data.getJSONObject("description");
				description = descriptionJSON.getString("fr");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}

			try {

				freeTextJSON = data.getJSONObject("freeText");
				freetext = freeTextJSON.getString("fr");

			} catch (Exception e) {

			}

			JSONArray arrayLocations = null;
			try {
				arrayLocations = data.getJSONArray("locations");
				
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}

			try {
				nomLieu = arrayLocations.getJSONObject(0)
						.getString("placename");
			
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}

			try {

				ville = arrayLocations.getJSONObject(0).getString("city");
			
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}

			try {

				latitude = arrayLocations.getJSONObject(0)
						.getDouble("latitude");
				longitude = arrayLocations.getJSONObject(0).getDouble(
						"longitude");

			} catch (JSONException e) {
				LOG.info("Pas de position on passe");
				continue;
			}

			try {

				adress = arrayLocations.getJSONObject(0).getString("address");
			
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}

			try {

				codePostal = arrayLocations.getJSONObject(0).getString(
						"postalCode");
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}

			// System.out.println("uid:" + uidEvenement);
			// System.out.println("TITRE:" + titre);
			// System.out.println("Description:" + description);
			// System.out.println("freeText:" + freetext);
			// System.out.println("nomlieu:" + nomLieu);
			// System.out.println("ville:" + ville);
			// System.out.println("code postal:" + codePostal);
			// System.out.println("adresseTotal:" + adress);
			// System.out.println("latitude:" + latitude);
			// System.out.println("longitude:" + longitude);
			String adresseTotal = adress + " " + codePostal + " " + ville;

			evenement = new EvenementOpenAGenda("0", uidEvenement, titre,
					description, freetext, adresseTotal, latitude, longitude,
					image, lienurl, ville, nomLieu);

			JSONArray array = null;

			try {
				array = arrayLocations.getJSONObject(0).getJSONArray("dates");
		
			} catch (JSONException e) {
				LOG.error(e.getMessage());
			}
			boolean ajout=false;
			for (int i = 0; i < array.length(); i++) {

				String date;
				try {
					LOG.info("taille ajoute event" + array.length());
					date = array.getJSONObject(i).getString("date");
					String timeStart = array.getJSONObject(i).getString(
							"timeStart");
					String timeEnd = array.getJSONObject(i)
							.getString("timeEnd");
					Date dateDebut = getDate(date, timeStart);
					Date dateFin = getDate(date, timeEnd);
					if (valideActivite(dateDebut, dateFin)){
					evenement.ajouteEvenement(dateDebut, dateFin);
					ajout=true;
					}

				} catch (JSONException | ParseException e) {
					
					LOG.info("Pas de date on passe");
					continue;

				}
			}

			LOG.info("ajoute dao");
		if (ajout)	evenement.ajouteDAO();
		}

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

}
