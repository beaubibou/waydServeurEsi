package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import fcm.ServeurMethodes;

public class AcquitMessageByActGcm implements Runnable {

	private static final Logger LOG = Logger
			.getLogger(AcquitMessageByActGcm.class);

	private int idPersonne;

	public AcquitMessageByActGcm(int idPersonne) {
		super();
		this.idPersonne = idPersonne;
	}

	public void run() {
		Connection connexiongcm = null;
		try {
			connexiongcm = CxoPool.getConnection();
			new ServeurMethodes(connexiongcm).gcmUpdateNbrMessage(idPersonne);
		} catch (SQLException | NamingException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.closeConnection(connexiongcm);
		}

	}
}
