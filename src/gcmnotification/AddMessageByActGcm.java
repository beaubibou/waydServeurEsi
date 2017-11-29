package gcmnotification;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import fcm.ServeurMethodes;
import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ParticipationDAO;

public class AddMessageByActGcm implements Runnable{
	private static final Logger LOG = Logger.getLogger(AddMessageByActGcm.class);
	
	int idActivite;
	public AddMessageByActGcm(int idActivite) {
		
		this.idActivite=idActivite;
	
	}
	
	@Override
	public void run() {

		Connection connexionGcm = null;
		try {
			connexionGcm = CxoPool.getConnection();
			ParticipationDAO participationdao=new ParticipationDAO(connexionGcm);
		
			ArrayList<Personne> listparticipant = participationdao
					.getListPartipantActivite(idActivite);
		
			new ServeurMethodes(connexionGcm)
					.gcmUpdateNbrMessageByAct(listparticipant);

		} catch (SQLException | NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}

}
