package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ParticipationDAO;

public class UpdateActiviteGcm extends Thread {
	
	private int idActivite;
	private int idPersonne;
		
	public UpdateActiviteGcm(int idActivite, int idPersonne) {
		super();
		this.idActivite = idActivite;
		this.idPersonne = idPersonne;
	}


	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
		
			ParticipationDAO participationDAO=new ParticipationDAO(connexionGcm);
			
			ArrayList<Personne> listpersonne = participationDAO
					.getListPartipantActiviteExpect(idActivite, idPersonne);
		
			new ServeurMethodes(connexionGcm)
					.envoiAndroidUpdateActivite(listpersonne,
							idActivite);// envoi la mise à jour à
										// tous les participants
										// sauf l'organisateur
			// sa mise à jour est en local.

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  finally {
			CxoPool.closeConnection(connexionGcm);
		}
	}
	
}
