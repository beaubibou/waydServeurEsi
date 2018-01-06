package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ActiviteDAO;
import wayde.dao.DiscussionDAO;
import wayde.dao.NotificationDAO;
import wayde.dao.ParticipationDAO;

public class EffaceActiviteGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(EffaceActiviteGcm.class);
	
	ArrayList<Personne> personneInterresse;
	ArrayList<Personne> participants;
	int idActivite;
	
	public EffaceActiviteGcm(ArrayList<Personne> personneInterresse,ArrayList<Personne> participants,int idActivite) {
		this.personneInterresse=personneInterresse;
		this.participants=participants;
		this.idActivite=idActivite;
	}
	
	
	public EffaceActiviteGcm(int idActivite) {
			Connection connexion=null;
			this.idActivite=idActivite;
		try {
		
		
			connexion = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexion);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			this.personneInterresse = activitedao
					.getListPersonneInterresse(activitedao
							.getActivite(idActivite));
			this.participants = participationdao
					.getListPartipantActivite(idActivite);
			this.idActivite=idActivite;
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		}finally{
			
			CxoPool.closeConnection(connexion);
		}

	
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
			serveurmethode
					.envoiAndroidUpdateNotification(participants);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e1));
		
		} finally {
			
			CxoPool.closeConnection(connexionGcm);
		}

	}
}
