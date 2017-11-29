package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AddAvisGcm implements Runnable {

	private int idPersonnenotee;
	private int idPersonne;
	private static final Logger LOG = Logger.getLogger(AddAvisGcm.class);
	
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
