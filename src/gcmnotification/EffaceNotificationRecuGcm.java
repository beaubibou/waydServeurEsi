package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class EffaceNotificationRecuGcm implements Runnable {
	int idDestinataire;

	public EffaceNotificationRecuGcm(int idDestinataire) {
		super();
		this.idDestinataire = idDestinataire;
	}

	public void run() {
		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			new ServeurMethodes(connexionGcm).gcmUpdateNotification(idDestinataire);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}
	}
}
