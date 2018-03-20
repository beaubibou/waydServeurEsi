package gcmnotification;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import fcm.PushNotifictionHelper;
import fcm.ServeurMethodes;

public class AddActiviteGcm implements Runnable {
	Activite activite;
	int idOrganisateur;
	private static final Logger LOG = Logger.getLogger(AddActiviteGcm.class);
	
	public AddActiviteGcm(Activite activite, int idOrganisateur) {

		this.idOrganisateur = idOrganisateur;
		this.activite = activite;

	}

	public AddActiviteGcm(ActiviteBean activiteBean, int idOrganisateur) {

		this.idOrganisateur = idOrganisateur;
		this.activite = new Activite(activiteBean);

	}

	public AddActiviteGcm(int idactivite) {
	
		Connection connexion = null;
		try {
			
			connexion = CxoPool.getConnection();
			Activite activiteTemp = new ActiviteDAO(connexion)
					.getActivite(idactivite);
			this.idOrganisateur = activiteTemp.getIdorganisateur();
			this.activite = activiteTemp;

		} catch (Exception e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closeConnection(connexion);
		}
	}

	@Override
	public void run() {

		Connection connexiongcm = null;
		try {
			// dans le cas ou l'activi� n'est pas en cours on ne fait rien
			
			if (!activite.isEnCours())return;
		
			connexiongcm = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexiongcm);
		
			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activite);
		
			ServeurMethodes serveurmethode = new ServeurMethodes(connexiongcm);
			serveurmethode.gcmUpdateNbrActivite(idOrganisateur);
			serveurmethode.gcmPushInterressByactivite(personneinteresse,
					activite.getId());
			serveurmethode.gcmUpdateNbrSuggestion(personneinteresse);
			PushNotifictionHelper.sendPushNotificationSuggestionList(
					personneinteresse, activite);

		} catch ( Exception e1) {
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

	}
}
