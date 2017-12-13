package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AcquitMessageDiscussionByActGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(AcquitMessageDiscussionByActGcm.class);
	
	private int idDestinataire;

	public AcquitMessageDiscussionByActGcm(int idDestinataire) {
		super();
		this.idDestinataire = idDestinataire;
	}

	public void run() {
		Connection connexiongcm = null;
		try {
			connexiongcm = CxoPool.getConnection();
			new ServeurMethodes(connexiongcm)
					.gcmUpdateNbrMessage(idDestinataire);

		} catch (SQLException | NamingException e) {
	
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

	}

}
