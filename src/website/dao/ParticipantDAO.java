package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import website.metier.ParticipantBean;

public class ParticipantDAO {

	Connection connexion;
	
	public ParticipantDAO(Connection connexion){
		this.connexion
		=connexion;
		
	}
	public  ArrayList<ParticipantBean> getListPaticipant(int idactivite)
			throws SQLException {
		ParticipantBean participant = null;
		ArrayList<ParticipantBean> retour = new ArrayList<ParticipantBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
		
			String requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,personne.nbravis,personne.sexe,personne.note,"
					+ "personne.datenaissance,personne.afficheage,personne.affichesexe from personne,participer where personne.idpersonne=participer.idpersonne"
					+ " and participer.idactivite=?  ";
			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setInt(1, idactivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idpersonne");
				String pseudo = rs.getString("prenom");
				if (pseudo == null)
					pseudo = " ";
				String photostr = rs.getString("photo");
				Date datenaissance = rs.getDate("datenaissance");
				double note = rs.getDouble("note");
				int nbravis = rs.getInt("nbravis");
				int sexe = rs.getInt("sexe");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				participant = new ParticipantBean(id, pseudo, nbravis, sexe,
						note, photostr, datenaissance, afficheage, affichesexe);
				retour.add(participant);
			}

			preparedStatement.close();
			rs.close();

			requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,personne.nbravis,personne.sexe,personne.note,"
					+ "personne.datenaissance,personne.afficheage,personne.affichesexe from personne,activite where personne.idpersonne=activite.idpersonne"
					+ " and activite.idactivite=?  ";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setInt(1, idactivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idpersonne");
				String pseudo = rs.getString("prenom");
				if (pseudo == null)
					pseudo = " ";
				String photostr = rs.getString("photo");
				Date datenaissance = rs.getDate("datenaissance");
				double note = rs.getDouble("note");
				int nbravis = rs.getInt("nbravis");
				int sexe = rs.getInt("sexe");
				boolean afficheage = rs.getBoolean("afficheage");
				boolean affichesexe = rs.getBoolean("affichesexe");
				participant = new ParticipantBean(id, pseudo, nbravis, sexe,
						note, photostr, datenaissance, afficheage, affichesexe);
				retour.add(0, participant);
			}
			return retour;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return retour;
		} finally {

			 preparedStatement.close();
			 rs.close();}

	}

}
