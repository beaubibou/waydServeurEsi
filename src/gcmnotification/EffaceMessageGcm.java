package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class EffaceMessageGcm implements Runnable {

	int idPersonne;
	
	private static final Logger LOG = Logger.getLogger(EffaceMessageGcm.class);
	
	
	public EffaceMessageGcm(int idPersonne) {
		super();
		this.idPersonne = idPersonne;
	}



	@Override
	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			new ServeurMethodes(connexionGcm)
					.gcmUpdateNbrMessage(idPersonne);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

		// TODO Auto-generated method stub

	}
}
