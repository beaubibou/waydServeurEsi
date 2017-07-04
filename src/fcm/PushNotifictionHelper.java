package fcm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import wayde.bean.Activite;
import wayde.bean.Personne;

import com.google.gson.JsonArray;

public class PushNotifictionHelper {
	public final static String AUTH_KEY_FCM = ServeurMethodes.key_gcm;
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	public static String sendPushNotificationSuggestionList(ArrayList<Personne> listpersonne,Activite activite)
			throws IOException {
		
		ArrayList<String> listpersonneGcm=ServeurMethodes.getListGCM(listpersonne);
		
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
		//List< String> test = new ArrayList<String>();
	
	//	test.add(deviceToken);
		json.put("registration_ids",listpersonneGcm);
			
		JSONObject info = new JSONObject();
		info.put("title",activite.getTitre()); // Notification title
		info.put("body", StringEscapeUtils.unescapeJava(activite.getLibelle())); // Notification
	//	info.put("click_action", "notificationSuggestion");
		json.put("notification", info);
	

	

		try {
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			result = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Nok";
		}
		System.out.println("GCM Notification is sent successfully");

		return result;

	}
	
	private static ArrayList<String> getListGCM(ArrayList<Personne> listpersonne) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String sendPushNotificationTo(String deviceToken)
			throws IOException {
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
		List< String> test = new ArrayList<String>();
		test.add(deviceToken);
		
		json.put("to",deviceToken.trim());
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

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			result = "ok";
		} catch (Exception e) {
			e.printStackTrace();
			result = "Nok";
		}
		System.out.println("GCM Notification is sent successfully");

		return result;

	}
}