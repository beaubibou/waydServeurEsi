package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class EffaceMessageRecuGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(EffaceMessageRecuGcm.class);
	
	int idPersonne;
	
	public EffaceMessageRecuGcm(int idPersonne) {
		super();
		this.idPersonne = idPersonne;
	}

	@Override
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
