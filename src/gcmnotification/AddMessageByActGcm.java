package gcmnotification;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.bean.Personne;
import wayde.dao.ParticipationDAO;
import fcm.ServeurMethodes;

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

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e1));
		} finally {
			CxoPool.closeConnection(connexionGcm);
		}

	}

}
