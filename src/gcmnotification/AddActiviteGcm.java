package gcmnotification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import fcm.PushNotifictionHelper;
import fcm.ServeurMethodes;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ActiviteDAO;

public class AddActiviteGcm extends Thread {
	Activite activite;
	int idOrganisateur;

	public AddActiviteGcm(Activite activite, int idOrganisateur) {

		this.idOrganisateur = idOrganisateur;
		this.activite = activite;

	}

	@Override
	public void run() {

		Connection connexiongcm = null;
		try {

			connexiongcm = CxoPool.getConnection();
			ActiviteDAO activitedao = new ActiviteDAO(connexiongcm);
			final ArrayList<Personne> personneinteresse = activitedao.getListPersonneInterresse(activite);
			ServeurMethodes serveurmethode = new ServeurMethodes(connexiongcm);
			serveurmethode.gcmUpdateNbrActivite(idOrganisateur);
			serveurmethode.gcmPushInterressByactivite(personneinteresse, activite.getId());
			serveurmethode.gcmUpdateNbrSuggestion(personneinteresse);
			PushNotifictionHelper.sendPushNotificationSuggestionList(personneinteresse, activite);

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
