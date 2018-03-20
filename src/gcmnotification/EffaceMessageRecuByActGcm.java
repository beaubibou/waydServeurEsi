package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import fcm.ServeurMethodes;

public class EffaceMessageRecuByActGcm implements Runnable{
int idPersonne;

private static final Logger LOG = Logger.getLogger(EffaceMessageEmisByActGcm.class);

	public EffaceMessageRecuByActGcm(int idPersonne) {
	super();
	this.idPersonne = idPersonne;
}


	public void run() {
		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			new ServeurMethodes(connexionGcm)
					.gcmUpdateNbrMessage(idPersonne);

		} catch (SQLException | NamingException e1) {
	
			LOG.error( ExceptionUtils.getStackTrace(e1));
	
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}
}
