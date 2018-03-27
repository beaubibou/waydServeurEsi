package fcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import wayde.bean.Activite;
import wayde.bean.Personne;
import website.metier.ProfilBean;

public class PushNotifictionHelper {
	public final static String AUTH_KEY_FCM = ServeurMethodes.key_gcm;
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	private static final Logger LOG = Logger.getLogger(PushNotifictionHelper.class);

	
	
	public static Map<Integer, String> mTypeActivite;
	static {
		mTypeActivite = new HashMap<Integer, String>();
		mTypeActivite.put(1, "ic_barrestorond");
		mTypeActivite.put(2, "ic_sportrnd");
		mTypeActivite.put(3, "ic_expositionrnd");
		mTypeActivite.put(4, "ic_jeurnd");
		mTypeActivite.put(5, "ic_friendsrnd");
		mTypeActivite.put(6, "ic_suggestionwayd");
		mTypeActivite.put(7, "ic_entraide");
		mTypeActivite.put(8, "ic_entraide");

	}

	public synchronized static String sendPushNotificationSuggestionList(
			ArrayList<Personne> listpersonne, Activite activite)
			throws IOException, JSONException {

		ArrayList<String> listpersonneGcm =getListGCMNotification(listpersonne);
	
		String loginfo = "sendPushNotificationSuggestionList - Taille list personne GCMinterresee " +listpersonneGcm.size() ;
		
		if (listpersonneGcm.isEmpty())return "";
		String result = "";
		URL url = new URL(API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		json.put("registration_ids", listpersonneGcm);
		JSONObject info = new JSONObject();
		info.put("title", activite.getTitre()); // Notification title
		info.put("body", StringEscapeUtils.unescapeJava(activite.getLibelle())); // Notification
		if (activite.getTypeUser()==ProfilBean.WAYDEUR)
				info.put("click_action", "notificationSuggestion");
		
		if (activite.getTypeUser()==ProfilBean.PRO)
				info.put("click_action", "notificationSuggestionPro");
		
		//info.put("icon", mTypeActivite.get(activite.getTypeactivite()));
		json.put("notification", info);

		JSONObject data = new JSONObject();

		data.put("idactiviteFromNotification", activite.getId());
		data.put("idtypeactivite",
				mTypeActivite.get(activite.getTypeactivite()));
		int dureeNotification = (int) (activite.datefinactivite.getTime() - new Date()
				.getTime()) / 1000;

		data.put("time_to_live", dureeNotification);
		json.put("data", data); // Notification
		
		try {
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			while ((output = br.readLine()) != null) {
			}
			result = "ok";
			
		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			result = "Nok";
			
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
		

		return result;

	}

	public static String sendPushNotificationTo(String deviceToken)
			throws IOException, JSONException {
		String result = "";
		URL url = new URL(API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject json = new JSONObject();
		List<String> test = new ArrayList<>();
		test.add(deviceToken);

		json.put("to", deviceToken.trim());
		JSONObject info = new JSONObject();
		info.put("title", "notification title"); // Notification title
		info.put("body", "message body"); // Notification
		info.put("click_action", "notificationSuggestion");
		json.put("notification", info);
		//

		JSONObject data = new JSONObject();
		data.put("idactivite", 397694);
		data.put("idtypeactivite", 2);
		json.put("data", data); // Notification
								// body

		try {
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));


			result = "ok";
		} catch (Exception e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			result = "Nok";
		}
	
		return result;

	}
	
	public static ArrayList<String> getListGCMNotification(ArrayList<Personne> listpersonne) {
		
		ArrayList<String> listgcm = new ArrayList<>();
		for (Personne pers : listpersonne) {
			if (pers.getGcm()!=null ){
				
				if (pers.isNotification())
				listgcm.add(pers.getGcm());
			
			}
			
		}
		return listgcm;
	}
	
}