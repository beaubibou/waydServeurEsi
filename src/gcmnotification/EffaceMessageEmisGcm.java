package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import fcm.ServeurMethodes;
import wayde.bean.CxoPool;




public class EffaceMessageEmisGcm extends Thread{
	
	private int idPersonne;

	public EffaceMessageEmisGcm(int idPersonne) {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}

}
