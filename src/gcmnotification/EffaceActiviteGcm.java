package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;

public class EffaceActiviteGcm extends Thread {

	ArrayList<Personne> personneInterresse;
	ArrayList<Personne> participants;
	int idActivite;
	
	public EffaceActiviteGcm(ArrayList<Personne> personneInterresse,ArrayList<Personne> participants,int idActivite) {
		this.personneInterresse=personneInterresse;
		this.participants=participants;
		this.idActivite=idActivite;
	}
	
	
	@Override
	public void run() {
		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurmethode = new ServeurMethodes(
					connexionGcm);

			ArrayList<Personne> listpersonneTotal = new ArrayList<Personne>();
			listpersonneTotal.addAll(participants);
			listpersonneTotal.addAll(personneInterresse);

			serveurmethode
					.envoiAndroidRefreshTdbAll(listpersonneTotal);
			serveurmethode.gcmAnnuleActivite(participants,
					idActivite);
			serveurmethode.gcmEffaceSuggestion(personneInterresse,
					idActivite);
			new ServeurMethodes(connexionGcm)
					.envoiAndroidUpdateNotification(participants);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}
}
