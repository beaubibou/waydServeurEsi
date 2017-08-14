package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;

public class AddParticipationGcm implements Runnable {

	
	private ArrayList<Personne> listParticipant;
	private int idActivite;

	public AddParticipationGcm(ArrayList<Personne> listParticipant, int idActivite) {
		super();
		this.listParticipant = listParticipant;
		this.idActivite = idActivite;
	}

	public void run() {
		// TODO Auto-generated method stub

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ServeurMethodes serveurMethodes=new ServeurMethodes(connexionGcm);
			
			serveurMethodes
					.envoiAndroidRefreshTdbAll(listParticipant);
			serveurMethodes
					.gcmUpdateNbrMessageByAct(listParticipant);

			serveurMethodes
					.envoiAndroidUpdateActivite(listParticipant,
							idActivite);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}
	
}
