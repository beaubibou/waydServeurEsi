package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class EffaceAmiGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(EffaceAmiGcm.class);
	
	private int idAmi;
	private int idPersonne;

	public EffaceAmiGcm(int idAmi, int idPersonne) {
		super();
		this.idAmi = idAmi;
		this.idPersonne = idPersonne;
	}

	public void run() {
		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurmethode = new ServeurMethodes(connexionGcm);
			serveurmethode.gcmUpdateNotification(idAmi);
			serveurmethode.gcmUpdateNbrAmi(idPersonne);
			serveurmethode.gcmUpdateNbrAmi(idAmi);
			serveurmethode.envoiAndroidUpdateNotification(idPersonne);
			serveurmethode.envoiAndroidUpdateNotification(idAmi);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			LOG.error(ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}

}
