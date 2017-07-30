package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;

public class EffaceParticipationGcm extends Thread {
	
	ArrayList<Personne> listPersonne;
	int idActivite;
	int idAeffacer;
	
	public EffaceParticipationGcm(ArrayList<Personne> listPersonne,int idActivite,int idAeffacer) {
		
		this.listPersonne=listPersonne;
		this.idActivite=idActivite;
		this.idAeffacer=idAeffacer;
		
	}
	
	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurMethodes=new ServeurMethodes(connexionGcm);
		
			serveurMethodes
					.envoiAndroidRefreshTdbAll(listPersonne); // TODO
																// Auto-generated
			serveurMethodes
					.gcmAnnuleParticipation(idAeffacer, idActivite); // method

			serveurMethodes
					.gcmUpdateNbrMessageByAct(listPersonne);

			serveurMethodes
					.envoiAndroidUpdateActivite(listPersonne,
							idActivite);
			serveurMethodes
					.envoiAndroidUpdateNotification(listPersonne);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}
	}
}
