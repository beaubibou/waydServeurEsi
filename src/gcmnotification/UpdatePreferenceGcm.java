package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import fcm.ServeurMethodes;

public class UpdatePreferenceGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(UpdatePreferenceGcm.class);
	
	
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
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

}

}