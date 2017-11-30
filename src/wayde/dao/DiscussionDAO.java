package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.Activite;
import wayde.bean.Discussion;
import wayde.bean.Message;



public class DiscussionDAO {
	private static final Logger LOG = Logger.getLogger(DiscussionDAO.class);

	Connection connexion;

	public DiscussionDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ArrayList<Discussion> getArrayDiscussionByAct(int idpersonne) throws SQLException {

		ArrayList<Discussion> retour = new ArrayList<Discussion>();
		ActiviteDAO activitedao = new ActiviteDAO(connexion);
		MessageDAO messagedao=new MessageDAO(connexion);
		ArrayList<Activite> listActivite = activitedao
				.getMesActiviteEncours(idpersonne);

		for (Activite activite : listActivite)
			 {
				
				Message message=messagedao.getLastMessageByAct(activite.getId());
				String corps=message.getCorps();
				Date dateMessage=message.datecreation;
				Discussion discussion = new Discussion(activite,dateMessage,corps);
				discussion.setNbnonlu(activitedao.getNbrMessageNonLuByAct(
						idpersonne, activite.getId()));		
			
				retour.add(discussion);

			 }

		return retour;
	}

	public void effaceDiscussionActivite(int idactivite,int idpersonne) throws SQLException {
	
		String requete = "Delete from messagebyact where idactivite=? and idemetteur=? and emis=true;"
				+ "Delete from messagebyact where idactivite=? and iddestinataire=? and emis=false;";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.setInt(3, idactivite);
		preparedStatement.setInt(4, idpersonne);
	
		preparedStatement.execute();

	}
	
	public void effaceDiscussionTouteActivite(int idactivite) throws SQLException {
	
		String requete = "Delete from messagebyact where idactivite=? ;";
		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.execute();

	}
	

	public void effaceDiscussionActivitePersonne(int idactivite, int idpersonne)
			throws SQLException {
		
		String requete = "Delete from messagebyact where idactivite=? and iddestinataire=? and emis=false;"
						+ "Delete from messagebyact where idactivite=? and idpersonne=? and emis=true;";

		PreparedStatement preparedStatement = connexion.prepareStatement(
				requete, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.setInt(1, idactivite);
		preparedStatement.setInt(2, idpersonne);
		preparedStatement.execute();

	}

}
