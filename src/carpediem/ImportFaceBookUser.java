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

import com.google.gson.JsonObject;

import website.metier.ActiviteCarpeDiem;

public class ImportFaceBookUser implements Runnable {

	private static final Logger LOG = Logger
			.getLogger(ImportFaceBookUser.class);

	String tokenFb;
	private String ville;
	private int total;

	public ImportFaceBookUser(String tokenFb, String ville) {

		super();
		this.tokenFb = tokenFb;
		this.ville = ville;

	}

	@Override
	public void run() {

		LOG.info("*********    Acquisition *USERS:" + ville);

		double latitude = 48.86;
		double longitude = 2.33;

		String requete = "search?type=place&q=ville&center=latitude,longitude&distance=50000&fields=name,checkins,picture,location";
		String requteFinale = requete.replace("ville", ville);
		requteFinale = requteFinale.replace("latitude",
				Double.toString(latitude));
		requteFinale = requteFinale.replace("longitude",
				Double.toString(longitude));

		String urlString = "https://graph.facebook.com/v2.11/" + requteFinale
				+ "&access_token=" + tokenFb;

		
		JSONObject json;
		
		json = getJsonFromUrl(urlString);
		getIdUser(json);
		
		
	}

	public JSONObject getJsonFromUrl(String urlString) {

		StringBuilder parsedContentFromUrl = new StringBuilder();

		LOG.info(urlString);

		URL url;
		JSONObject json;

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
			e1.printStackTrace();
			return null;
		}

	
		try {
			json = new JSONObject(parsedContentFromUrl.toString());
		} catch (JSONException e1) {
			LOG.error("Le parcours du json de l'évenemt FB a échoué: activité rejetée");
			return null;

		}

		return json;
	}

	public void getIdUser(JSONObject json){
		String next = null;
		double latitude,longitude;
		
		try {
			JSONArray array = json.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {

				String id = array.getJSONObject(i).getString("id");

				JSONObject location = null;
				try {
					location = array.getJSONObject(i).getJSONObject("location");
					longitude = location.getDouble("longitude");
					latitude = location.getDouble("latitude");

				} catch (JSONException e1) {

					LOG.error("L'objet lo n'est pas présent dans le JSON: activité rejetée");
					return;
				}
				total++;
				LOG.info("total"+total+"id:"+id + ":" + latitude + ":" + longitude);
				
			}

		} catch (JSONException e1) {

			LOG.error("L'objet date n'est pas présent dans le JSON: activité rejetée");
			return;
		}

	
		try {
			JSONObject paging = json.getJSONObject("paging");
			next = paging.getString("next");
		} catch (JSONException e) {
			
			LOG.info("Fin du parsing");
			return;
		}
		
		if (next!=null){
			JSONObject json1=null;
			json1 = getJsonFromUrl(next);
			getIdUser(json1);
			
		}

	}
}
