package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class AcquitMessageByActGcm implements Runnable {

	private static final Logger LOG = Logger.getLogger(AcquitMessageByActGcm.class);
	
	private int idPersonne;

	public AcquitMessageByActGcm(int idPersonne) {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {

			CxoPool.closeConnection(connexiongcm);
		}

	}
}
