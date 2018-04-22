package carpediem;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import website.dao.ActiviteDAO;
import website.metier.ActiviteCarpeDiem;
import website.metier.Outils;

public class ImportOpenAgendaAgenda {

	private static final Logger LOG = Logger
			.getLogger(ImportOpenAgendaAgenda.class);

	String token;
	private String idagenda;
	private int total;
	ArrayList<String> listIdEvent = new ArrayList<>();
	int totalActiviteAgenda = 500;
	private static String REQUTE_AGENDA_OFFSETT = "https://openagenda.com/agendas/idagenda/events.json?limit=malimite&offset=monoffset";
	public ImportOpenAgendaAgenda(String tokenFb, String idagenda) {

		super();
		this.token = tokenFb;
		this.idagenda = idagenda;

	}

	public void start() {

		LOG.info("*********    Acquisition *Evenemet OPEN AGENDA:" + idagenda);
		int offset = 1;
		int limit = 100;
		
		String urlString = REQUTE_AGENDA_OFFSETT.replace("idagenda", idagenda)
				.replace("monoffset",Integer.toString(offset))
				.replace("malimite",Integer.toString(limit));
	
	
		while (offset < totalActiviteAgenda) {
			
			JSONObject json;
			json = Outils.getJsonFromUrl(urlString);
		
			System.out.println(json);

			getidEvenemnt(json);

			offset = offset + limit;
			
			urlString = REQUTE_AGENDA_OFFSETT.replace("idagenda", idagenda)
					.replace("monoffset",Integer.toString(offset))
					.replace("malimite",Integer.toString(limit));
		
		}

		ExecutorService executor = Executors.newFixedThreadPool(20);
			LOG.info("------------nbr rid trouve"+listIdEvent.size());
	
//			for (String idEvent : listIdEvent) {
//			executor.execute(new ImportOpenAgendaEvent(
//					"261c569041f74c8180088cda3e47b375", idEvent,idagenda));
//
//		}
		
		executor.shutdown();
		
		while(!executor.isTerminated()){
			
		}
		LOG.info("**********Import terminée***********"+idagenda);

	}

	
	public void getidEvenemnt(JSONObject json) {

		try {
			totalActiviteAgenda = json.getInt("total");
			LOG.info("***********total"+total);
		} catch (JSONException e2) {

			LOG.error("total est non présente");

		}

		try {
			JSONArray array = json.getJSONArray("events");

			for (int i = 0; i < array.length(); i++) {

				String uid = array.getJSONObject(i).getString("uid");
				listIdEvent.add(uid);

			}

		} catch (JSONException e2) {

			LOG.error("Events est non présente");

		}

	}
}
