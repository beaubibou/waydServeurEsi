package fcm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import wayde.bean.Personne;
import wayde.bean.TableauBord;
import wayde.dao.ActiviteDAO;
import wayde.dao.PersonneDAO;

import com.google.android.gcm.server.Sender;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.tasks.OnSuccessListener;

public class ServeurMethodes {
	static String key_gcm = "AIzaSyCpwBa7gclZ9H1oRi0jRZ-tVj2nTWfnrRI";
	private static final Logger LOG = Logger.getLogger(ServeurMethodes.class);

	Connection connexion;

	protected static String uid;

	public ServeurMethodes(Connection connexion) {
		// TODO Auto-generated constructor stub
		this.connexion = connexion;
	}

	public ServeurMethodes() {
		// TODO Auto-generated constructor stub

	}

	public void envoiAndroidRefreshTDB(int idpersonne) {
		String GCMid;
		if (idpersonne == 0)
			return;

		try {
			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.refreshtdb));
			String Er = key_gcm;
			Sender n = new Sender(Er);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.build();
			// System.out.println(GCMid);
			n.send(androidMessage, GCMid, 3);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gcmUpdateNbrSuggestion(ArrayList<Personne> listparticipant) {

		String GCMid;
		// A OPTIMISER

		ActiviteDAO activiteDAO = new ActiviteDAO(connexion);
		PersonneDAO personneDAO = new PersonneDAO(connexion);
		for (Personne participant : listparticipant) {
			try {
				int nbrsuggestion;
				nbrsuggestion = activiteDAO.getNbrSuggestion(participant
						.getId());
				GCMid = personneDAO.getGCMId(participant.getId());

				if (GCMid == null)
					continue;

				PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
						Integer.toString(PushAndroidMessage.NBR_SUGGESTION));
				Sender n = new Sender(key_gcm);
				com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
						.timeToLive(5)
						.addData("id", messageaenvoyer.getId())
						.addData("nbrsuggestion",
								Integer.toString(nbrsuggestion))
						.addData("idpersonne",
								Integer.toString(participant.getId())).build();
				n.send(androidMessage, GCMid, 3);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void gcmUpdateNbrMessageByAct(ArrayList<Personne> listparticipant) {

		String GCMid;
		// A OPTIMISER

		for (Personne participant : listparticipant) {

			try {
				int nbrmessagenonlu;

				nbrmessagenonlu = new ActiviteDAO(connexion)
						.getNbrMessageNonLu(participant.getId());
				GCMid = new PersonneDAO(connexion)
						.getGCMId(participant.getId());

				if (GCMid == null)
					continue;

				PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
						Integer.toString(PushAndroidMessage.NBR_MESSAGE_NONLU));
				String Er = key_gcm;
				Sender n = new Sender(Er);
				com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
						.timeToLive(5)
						.addData("id", messageaenvoyer.getId())
						.addData("nbrmessagenonlu",
								Integer.toString(nbrmessagenonlu))
						.addData("idpersonne",
								Integer.toString(participant.getId())).build();
				n.send(androidMessage, GCMid, 3);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void gcmUpdateTdb(ArrayList<Personne> listparticipant) {// Envoi les
																	// tableau

		String GCMid;
		// A OPTIMISER
		ActiviteDAO activitedao = new ActiviteDAO(connexion);

		for (Personne participant : listparticipant) {

			try {
				// int nbrmessagenonlu;
				TableauBord tdb = activitedao.getTableauBord(participant
						.getId());

				GCMid = new PersonneDAO(connexion)
						.getGCMId(participant.getId());

				if (GCMid == null)
					continue;

				PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
						Integer.toString(PushAndroidMessage.TDB_REFRESH));
				String Er = key_gcm;
				Sender n = new Sender(Er);
				com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
						.timeToLive(5)
						.addData("id", messageaenvoyer.getId())
						.addData("nbrmessagenonlu",
								Integer.toString(tdb.getNbrmessagenonlu()))
						.addData("nbractivite",
								Integer.toString(tdb.getNbractiviteencours()))
						.addData("nbrami", Integer.toString(tdb.getNbrami()))
						.addData("nbrsuggestion",
								Integer.toString(tdb.getNbrsuggestion()))
						.addData("nbrnotification",
								Integer.toString(tdb.getNbrnotification()))
						.addData("idpersonne",
								Integer.toString(participant.getId())).build();
				n.send(androidMessage, GCMid, 3);

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void gcmUpdateNbrMessage(int idpersonne) {
		String GCMid;

		try {

			int nbrmessagenonlu = new ActiviteDAO(connexion)
					.getNbrMessageNonLu(idpersonne);
			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.NBR_MESSAGE_NONLU));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5)
					.addData("id", messageaenvoyer.getId())
					.addData("nbrmessagenonlu",
							Integer.toString(nbrmessagenonlu))
					.addData("idpersonne", Integer.toString(idpersonne))
					.build();

			n.send(androidMessage, GCMid, 3);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gcmUpdateNbrAmi(int idpersonne) {
		String GCMid;

		try {

			int nbrami = new ActiviteDAO(connexion).getNbrAmi(idpersonne);
			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.NBR_AMI));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("nbrami", Integer.toString(nbrami))
					.addData("idpersonne", Integer.toString(idpersonne))
					.build();

			n.send(androidMessage, GCMid, 3);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gcmUpdateNbrActivite(int idpersonne) {
		String GCMid;

		try {

			int nbractivite = new ActiviteDAO(connexion)
					.getNbrActiviteTotal(idpersonne);

			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.NBR_ACTIVITE));
			String Er = key_gcm;
			Sender n = new Sender(Er);

			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idpersonne", Integer.toString(idpersonne))
					.addData("nbractivite", Integer.toString(nbractivite))
					.build();

			n.send(androidMessage, GCMid, 3);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void gcmUpdateNotification(int idpersonne) {
		String GCMid;

		try {
			// Calcule le nobmre de notification
			int nbrnotification = new ActiviteDAO(connexion)
					.getNbrNotification(idpersonne);
			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.NBR_NOTIFICATION));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5)
					.addData("id", messageaenvoyer.getId())
					.addData("nbrnotification",
							Integer.toString(nbrnotification))
					.addData("idpersonne", Integer.toString(idpersonne))
					.build();

			n.send(androidMessage, GCMid, 3);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void envoiAndroidRefreshTdbAll(ArrayList<Personne> listpersonne) {

		try {

			if (listpersonne.size() == 0)
				return;

			ArrayList<String> listpersonneGcm = getListGCM(listpersonne);
			if (listpersonneGcm.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.refreshtdb));
			String Er = key_gcm;
			Sender n = new Sender(Er);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(10).addData("id", messageaenvoyer.getId())
					.build();
			n.send(androidMessage, listpersonneGcm, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void envoiAndroidUpdateActivite(ArrayList<Personne> listpersonne,
			int idActivite) {

		try {

			if (listpersonne.size() == 0)
				return;

			ArrayList<String> listpersonneGcm = getListGCM(listpersonne);
			if (listpersonneGcm.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.UPDATE_ACTIVITE));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(10).addData("id", messageaenvoyer.getId())
					.addData("idactivite", Integer.toString(idActivite))
					.build();
			n.send(androidMessage, listpersonneGcm, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void envoiAndroidUpdateNotification(ArrayList<Personne> listpersonne) {

		try {

			if (listpersonne.size() == 0)
				return;

			ArrayList<String> listpersonneGcm = getListGCM(listpersonne);
			if (listpersonneGcm.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.UPDATE_NOTIFICATION));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(10).addData("id", messageaenvoyer.getId())
					.build();
			n.send(androidMessage, listpersonneGcm, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void envoiAndroidUpdateNotification(int idpersonne) {

		try {

			String GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);

			LOG.info("GCMID" + GCMid);

			if (GCMid == null)
				return;
			if (GCMid.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.UPDATE_NOTIFICATION));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(10).addData("id", messageaenvoyer.getId())
					.build();
			n.send(androidMessage, GCMid, 3);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gcmAnnuleActivite(ArrayList<Personne> listpersonne,
			int idactivite) {

		try {

			if (listpersonne.size() == 0)
				return;
			ArrayList<String> listpersonneGcm = getListGCM(listpersonne);
			if (listpersonneGcm.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.Annule_Activite));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idactivite", Integer.toString(idactivite))
					.build();
			n.send(androidMessage, listpersonneGcm, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void gcmEffaceSuggestion(ArrayList<Personne> listpersonne,
			int idactivite) {// Informe les terminaux concern�es par la
								// suggestion que l'ativit� est annul�e.

		try {

			if (listpersonne.size() == 0)
				return;

			ArrayList<String> listpersonneGcm = getListGCM(listpersonne);
			if (listpersonneGcm.isEmpty())
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.EFFACE_SUGGESTION));

			Sender n = new Sender(key_gcm);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idactivite", Integer.toString(idactivite))
					.build();
			n.send(androidMessage, listpersonneGcm, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void gcmAnnuleParticipation(int idpersonne, int idactivite) {

		try {

			String GCMid;

			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);
			if (GCMid == null)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.Annule_PARTICIPATION));
			String Er = key_gcm;
			Sender n = new Sender(Er);
			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idpersonne", Integer.toString(idpersonne))
					.addData("idactivite", Integer.toString(idactivite))

					.build();
			n.send(androidMessage, GCMid, 3);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void gcmPushInterressByactivite(ArrayList<Personne> listpersonne,
			int idactivite) {

		try {

			if (listpersonne.size() == 0)
				return;

			ArrayList<String> listgcm = getListGCM(listpersonne);
			if (listgcm.size() == 0)
				return;

			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.notification));
			Sender n = new Sender(key_gcm);

			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idactivite", Integer.toString(idactivite))
					.addData("message", "Nouvelle Activite").build();
			n.send(androidMessage, listgcm, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void envoiMessageAndroid(int idpersonne) throws IOException {
		String GCMid;
		try {
			GCMid = new PersonneDAO(connexion).getGCMId(idpersonne);
			PushAndroidMessage messageaenvoyer = new PushAndroidMessage(
					Integer.toString(PushAndroidMessage.notification));
			String Er = key_gcm;
			Sender n = new Sender(Er);

			com.google.android.gcm.server.Message androidMessage = new com.google.android.gcm.server.Message.Builder()
					.timeToLive(5).addData("id", messageaenvoyer.getId())
					.addData("idactivite", Integer.toString(12)).build();
			n.send(androidMessage, GCMid, 3);

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<String> getListGCM(ArrayList<Personne> listpersonne) {

		ArrayList<String> listgcm = new ArrayList<String>();
		for (Personne pers : listpersonne) {
			if (pers.getGcm() != null) {
				listgcm.add(pers.getGcm());
			}
		}
		return listgcm;
	}

	public static double getDistance(double lat1, double lat2, double lon1,
			double lon2) {
		final int R = 6371; // Radius of the earth

		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2)
				* Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters
		double height = 0 - 0;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);

	}

	public String getUserIdByToken(String idToken) {

		uid = null;

		if (FirebaseApp.getApps().isEmpty()) 
				FirebaseApp.initializeApp(WBservices.optionFireBase);
		
		// Verfie le jeton si correspodant cree le compte ou renvoi la personne
		
		FirebaseAuth.getInstance().verifyIdToken(idToken)
				.addOnSuccessListener(new OnSuccessListener<FirebaseToken>() {
					@Override
					public void onSuccess(FirebaseToken decodedToken) {
						uid = decodedToken.getUid();
						// ...

					}
				});

		return uid;

	}

}
