package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AddMessageGcm implements Runnable{
	
	int idDestinataire;
	public AddMessageGcm(int idDestinataire) {
		
		this.idDestinataire=idDestinataire;
	}
	
	@Override
	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			new ServeurMethodes(connexionGcm)
					.gcmUpdateNbrMessage(idDestinataire);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

		// TODO Auto-generated method stub

	}
	

}
