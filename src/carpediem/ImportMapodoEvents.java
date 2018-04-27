package carpediem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import website.metier.Outils;

public class ImportMapodoEvents implements Runnable {

	String ville;
	private String when;
	private String access_token;

	private EvenementOpenAGenda evenement;
	private static final Logger LOG = Logger
			.getLogger(ImportMapodoEvents.class);
	private static String REQUETE_ACCESS_TOKEN = "https://oauth2.mapado.com/oauth/v2/token?client_id=454_2bn7u2t8g3dwkss4ogwkco4ow8wws4ocossw8wc40o0gw4kgwk&client_secret=3qxhc5jhr04kw8cswkc04oos4kgcw4gok0csc8s88go8kwcc4c&grant_type=password&username=pmestivier@club.fr&password=azerty";
	private static String REQUETE_EVENT = "https://api.mapado.com/v1/activities?access_token=montoken&offset=monoffset&limit=malimite&q=maville&when=monwhen";
	private static String REQUETE_EVENT_POSITION = "https://api.mapado.com/v1/activities?access_token"
			+ "=montoken&offset=monoffset&limit=malimite&latlng=malatlng&distance=auto&when=monwhen";
	
	private ArrayList<String> listUUID = new ArrayList<String>();

	public ImportMapodoEvents(String ville, String when) {

		super();

		this.when = when;
		this.ville = ville;
		this.access_token = getAccessToken();
		LOG.info(access_token);
	}

	public static String getAccessToken() {
		JSONObject json;

		json = Outils.getJsonFromUrl(REQUETE_ACCESS_TOKEN);

		try {
			return json.getString("access_token");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void start() {

		ArrayList<String> villes = new ArrayList<String>();
//		villes.add("paris");
//		villes.add("toulouse");
//		villes.add("nantes");
//		villes.add("montpellier");
//		villes.add("nice");
//		villes.add("rennes");
//		villes.add("bordeaux");
//		villes.add("limoges");
//		villes.add("nimes");
//		villes.add("marseille");
//		villes.add("lyon");

		for (String ville : villes) {

			int limit = 100;
			int offset = 1;

			boolean stop = false;
			while (!stop) {

				try {

					JSONObject json;

					String urlString = REQUETE_EVENT
							.replace("montoken", access_token)
							.replace("monoffset", Integer.toString(offset))
							.replace("malimite", Integer.toString(limit))
							.replace("monwhen", when).replace("maville", ville);
					LOG.info(urlString);
					json = Outils.getJsonFromUrl(urlString);
					JSONArray arrayData = json.getJSONArray("data");

					if (arrayData.length() == 0) {
						LOG.info("********** stop");
						stop = true;
					} else {
						getEvenements(json);
						offset = offset + 100;
					}

				} catch (Exception e) {
					LOG.error(e.getMessage());
					stop = true;
				}

			}

		}

		ArrayList<VillePosition> villePositions = new ArrayList<VillePosition>();
		villePositions.add(new VillePosition("paris", 48.8667, 2.3333));
		villePositions.add(new VillePosition("lyon", 45.76, 4.83));
		villePositions.add(new VillePosition("marseille", 43.29, 5.36));
		villePositions.add(new VillePosition("rennes", 48.11, -1.67));
		villePositions.add(new VillePosition("nantes", 47.21, -1.55));
		villePositions.add(new VillePosition("montpellier", 43.61, 3.87));
		villePositions.add(new VillePosition("bordeaux", 44.83, -0.57));
		villePositions.add(new VillePosition("nimes", 43.83, 4.360));
	
		for (VillePosition villeposition : villePositions) {

			int limit = 100;
			int offset = 1;

			boolean stop = false;
			while (!stop) {

				try {

					JSONObject json;

					String urlString = REQUETE_EVENT_POSITION
							.replace("montoken", access_token)
							.replace("monoffset", Integer.toString(offset))
							.replace("malimite", Integer.toString(limit))
							.replace("monwhen", when).replace("malatlng", villeposition.getPosition());
					LOG.info(urlString);
					json = Outils.getJsonFromUrl(urlString);
					JSONArray arrayData = json.getJSONArray("data");

					if (arrayData.length() == 0) {
						LOG.info("********** stop");
						stop = true;
					} else {
						getEvenements(json);
						offset = offset + 100;
					}

				} catch (Exception e) {
					LOG.error(e.getMessage());
					stop = true;
				}

			}

		}
	
		int ajout = 0;
	
		for (String uuid : listUUID) {
			ajout++;
			new ImportMapadoEvent(access_token, uuid).start();
			LOG.info("************Ajout evenement du "+when +":"+ ajout + "/" + listUUID.size());

		}

	}

	public void getEvenements(JSONObject json) {

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
				String uidEvenement = data.getString("uuid");
				//listUUID.add(uidEvenement);
				ajouteUUID(listUUID,uidEvenement);
			} catch (JSONException e1) {
			
				LOG.error(e1.getMessage());
				continue;
			}

		}

	}

	private void ajouteUUID(ArrayList<String> listUUID2, String uidEvenement) {
	
		boolean contient=false;
		for (String uuid:listUUID2){
			if (uuid.equals(uidEvenement))
				contient=true;
		}
			
		if (!contient)
			listUUID2.add(uidEvenement);
		
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

}
