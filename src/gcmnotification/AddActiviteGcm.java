package gcmnotification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import fcm.PushNotifictionHelper;
import fcm.ServeurMethodes;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ActiviteDAO;
import website.metier.ActiviteBean;

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

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connexion != null)
				try {
					connexion.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

	}
}
