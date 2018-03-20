package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ParticipationDAO;
import fcm.ServeurMethodes;

public class UpdateActiviteGcm implements Runnable {
	private static final Logger LOG = Logger.getLogger(UpdateActiviteGcm.class);
	
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
							idActivite);// envoi la mise � jour �
										// tous les participants
										// sauf l'organisateur
			// sa mise � jour est en local.

		} catch (SQLException | NamingException e1) {
		
			LOG.error( ExceptionUtils.getStackTrace(e1));
		}  finally {
			CxoPool.closeConnection(connexionGcm);
		}
	}
	
}
