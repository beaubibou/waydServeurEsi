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

public class AddParticipationGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(AddParticipationGcm.class);
	
	
	private ArrayList<Personne> listParticipant;
	private int idActivite;

	public AddParticipationGcm(ArrayList<Personne> listParticipant, int idActivite) {
		super();
		this.listParticipant = listParticipant;
		this.idActivite = idActivite;
	}

	public void run() {
		

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurMethodes=new ServeurMethodes(connexionGcm);
			
			serveurMethodes
					.envoiAndroidRefreshTdbAll(listParticipant);
			serveurMethodes
					.gcmUpdateNbrMessageByAct(listParticipant);

			serveurMethodes
					.envoiAndroidUpdateActivite(listParticipant,
							idActivite);

		} catch (SQLException | NamingException e1) {
	
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}
	
}
