package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.bean.Personne;
import fcm.ServeurMethodes;

public class EffaceParticipationGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(EffaceParticipationGcm.class);
	
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
		
			LOG.error( ExceptionUtils.getStackTrace(e1));
	
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}
	}
}
