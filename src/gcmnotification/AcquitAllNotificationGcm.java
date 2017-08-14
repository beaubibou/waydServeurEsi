package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AcquitAllNotificationGcm implements Runnable{
	
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

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

		
	}

}
