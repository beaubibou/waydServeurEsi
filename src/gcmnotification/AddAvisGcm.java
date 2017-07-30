package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AddAvisGcm extends Thread {

	private int idPersonnenotee;
	private int idPersonne;

	public AddAvisGcm(int idPersonnenotee, int idPersonne) {
		super();
		this.idPersonnenotee = idPersonnenotee;
		this.idPersonne = idPersonne;
	}

	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurMethodes = new ServeurMethodes(connexionGcm);
		
			serveurMethodes.envoiAndroidRefreshTDB(idPersonnenotee);

			serveurMethodes.envoiAndroidRefreshTDB(idPersonne);

			serveurMethodes.envoiAndroidUpdateNotification(idPersonnenotee);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}
}
