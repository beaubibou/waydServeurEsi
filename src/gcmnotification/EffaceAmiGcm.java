package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;

public class EffaceAmiGcm extends Thread {

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
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}

}
