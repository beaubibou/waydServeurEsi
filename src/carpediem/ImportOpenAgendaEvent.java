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

public class ImportOpenAgendaEvent implements Runnable {

	private static final Logger LOG = Logger
			.getLogger(ImportOpenAgendaEvent.class);

	String token;
	private  String idEvent;
	private int total;

	public ImportOpenAgendaEvent(String tokenFb, String idEvent ) {

		super();
		this.token = tokenFb;
		this.idEvent = idEvent;

	}

	@Override
	public void run() {

		LOG.info("*********    Acquisition *Evenemet OPEN AGENDA:" + idEvent);

		double latitude =0;
		double longitude =0;

		
		String requete = "https://api.openagenda.com/v1/events/idevent?key=token";
		String requteFinale = requete.replace("idevent", idEvent);
		requteFinale = requteFinale.replace("token",token);
		String urlString =requteFinale;
		JSONObject json;
		
		json = getJsonFromUrl(urlString);
		System.out.println(json);
		getEvenemnt(json);
		
		
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

	public void getEvenemnt(JSONObject json){
		
		URL url;
		String uid = null;
		String description=null;
		String titre = null;
		String ville = null;
		String pays = null;
		double latitude = 0;
		double longitude=0;
		String rue = null;
		String codePostal = null;
		String adresseTotal = null;
		String freetext=null;
		String image = null;
		String nomLieu = null;
		JSONObject data=null;
		JSONObject titreJSON=null;
		JSONObject descriptionJSON=null;
		JSONObject locationJSON=null;
		JSONObject freeTextJSON=null;
		try {
			 data = json.getJSONObject("data");
		} catch (JSONException e2) {

			LOG.error("La data est non présente");

		}

		try {
			uid = data.getString("uid");
		} catch (JSONException e2) {

			LOG.error("La uid est non présente");

		}
		
		try {
			titreJSON = data.getJSONObject("title");
			titre=titreJSON.getString("fr");
			
			descriptionJSON=data.getJSONObject("description");
			description=descriptionJSON.getString("fr");
			
			freeTextJSON=data.getJSONObject("freeText");
			freetext = freeTextJSON.getString("fr");
		
			
			
		} catch (JSONException e2) {

			
			LOG.error("La titre est non présente");

		}
		
		try {
			JSONArray array = data.getJSONArray("locations");
			nomLieu=array.getJSONObject(0).getString("placename");
			ville=array.getJSONObject(0).getString("city");
			latitude=array.getJSONObject(0).getDouble("latitude");
			longitude=array.getJSONObject(0).getDouble("longitude");
			adresseTotal=array.getJSONObject(0).getString("address");
			codePostal=array.getJSONObject(0).getString("postalCode");;
			
			//	adresseTotal=locationJSON.getString("description");
		//	ville=locationJSON.getString("city");
		//	codePostal=locationJSON.getString("postalCode");
		//	System.out.println("+++++++++++++++++++++++++"+locationJSON);
			
		} catch (JSONException e2) {

			
			LOG.error("La locations est non présente");

		}
		
		
		LOG.info("uid:"+uid);
		LOG.info("TITRE:"+titre);
		LOG.info("Description:"+description);
		LOG.info("freeText:"+freetext);
		
		LOG.info("nomlieu:"+nomLieu);
		LOG.info("ville:"+ville);
		LOG.info("code postal:"+codePostal);
		LOG.info("adresseTotal:"+adresseTotal);
		LOG.info("latitude:"+latitude);
		LOG.info("longitude:"+longitude);
		
//		try {
//			titre = json.getString("name");
//		} catch (JSONException e1) {
//
//			LOG.error("La titre est non présent");
//
//		}
//
//		JSONObject place = null;
//		try {
//			place = json.getJSONObject("place");
//		} catch (JSONException e1) {
//
//			LOG.error("L'objet place n'est pas présent dans le JSON: activité rejetée");
//			return;
//		}
//
//		try {
//			nomLieu = place.getString("name");
//		} catch (JSONException e1) {
//
//			LOG.error("Le nom du site n'est pas présent dans le JSON");
//		}
//
//		JSONObject location = null;
//		try {
//			location = place.getJSONObject("location");
//		} catch (JSONException e1) {
//
//			LOG.error("L'objet location n'est pas présent dans le JSON: activité rejetée");
//			return;
//		}
//
//		try {
//			ville = location.getString("city");
//		} catch (JSONException e1) {
//
//			LOG.error("La ville n'est pas présente dans le JSON");
//
//		}
//		try {
//			pays = location.getString("country");
//		} catch (JSONException e1) {
//
//			LOG.error("Le pays  n'est pas présente dans le JSON");
//
//		}
//
//		try {
//			latitude = location.getDouble("latitude");
//		} catch (JSONException e1) {
//
//			LOG.error("La latitude  n'est pas présente dans le JSON: activité rejetée");
//			return;
//		}
//		try {
//			longitude = location.getDouble("longitude");
//		} catch (JSONException e1) {
//
//			LOG.error("La longitude  n'est pas présente dans le JSON: activité rejetée");
//			return;
//		}
//
//		try {
//			rue = location.getString("street");
//		} catch (JSONException e1) {
//
//			LOG.error("La rue  n'est pas présente dans le JSON");
//		}
//
//		try {
//			codePostal = location.getString("zip");
//		} catch (JSONException e1) {
//
//			LOG.error("Le code postal  n'est pas présente dans le JSON");
//
//		}

		
		
		
//		LOG.info("Ville:"+ville);
//		LOG.info("PAys:"+pays);
//		LOG.info("latitude:"+latitude);
//		LOG.info("longitude:"+longitude);
//		LOG.info("rue:"+rue);
//		LOG.info("code postal:"+codePostal);
//		LOG.info("nomlieu:"+nomLieu);
//		LOG.info("image:"+image);

		
	
		
		
		

	}
}
