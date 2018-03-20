package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import fcm.ServeurMethodes;

public class AcquitMessageDiscussionGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(AcquitMessageDiscussionGcm.class);
	
	private int idPersonne;

	public AcquitMessageDiscussionGcm(int idPersonne) {
		super();
		this.idPersonne = idPersonne;
	}

	public void run() {
		Connection connexiongcm = null;

		try {
			connexiongcm = CxoPool.getConnection();
			new ServeurMethodes(connexiongcm)
					.gcmUpdateNbrMessage(idPersonne);

		} catch (SQLException | NamingException e1) {
		
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

	}
}
