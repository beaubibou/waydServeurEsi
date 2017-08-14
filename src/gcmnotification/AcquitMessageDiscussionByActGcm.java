package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AcquitMessageDiscussionByActGcm implements Runnable {
	
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

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexiongcm);
		}

	}

}
