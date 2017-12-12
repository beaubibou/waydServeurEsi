package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.PushAndroidMessage;
import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AcquitAllNotificationGcm implements Runnable{
	private static final Logger LOG = Logger.getLogger(AcquitAllNotificationGcm.class);
	int idPersonne;
	public AcquitAllNotificationGcm(int idPersonne) {
	this.idPersonne=idPersonne;	
		
	}
	
	public void run() {
		Connection connexiongcm = null;
		try {
		
			connexiongcm = CxoPool.getConnection();
			new ServeurMethodes(connexiongcm)
					.gcmUpdateNotification(idPersonne);

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

		
	}

}
