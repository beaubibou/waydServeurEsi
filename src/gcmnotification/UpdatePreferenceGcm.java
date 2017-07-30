package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class UpdatePreferenceGcm extends Thread {
	int idPersonne;

	public UpdatePreferenceGcm(int idPersonne) {
		// TODO Auto-generated constructor stub
		this.idPersonne = idPersonne;
	}


	public void run() {
		// TODO Auto-generated method stub
		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			new ServeurMethodes(connexionGcm)
					.envoiAndroidRefreshTDB(idPersonne);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

}

}