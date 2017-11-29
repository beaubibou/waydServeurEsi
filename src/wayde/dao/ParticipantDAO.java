package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.Participant;

public class ParticipantDAO {

	private static final Logger LOG = Logger.getLogger(ParticipantDAO.class);

	Connection connexion;

	public ParticipantDAO(Connection connexion) {
		this.connexion = connexion;
	}
	

	public ArrayList<Participant> getListPaticipant(int idactivite) throws SQLException {
		Participant participant = null;
		ArrayList<Participant> retour = new ArrayList<Participant>();

		String requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,personne.nbravis,personne.sexe,personne.note,"
				+ "personne.datenaissance,personne.afficheage,personne.affichesexe from personne,participer where personne.idpersonne=participer.idpersonne"
				+ " and participer.idactivite=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idactivite);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("idpersonne");
			String pseudo = rs.getString("prenom");
			if (pseudo==null)pseudo=" ";
			String photostr = rs.getString("photo");
			Date datenaissance = rs.getDate("datenaissance");
			double note=rs.getDouble("note");
			int nbravis=rs.getInt("nbravis");
			int sexe=rs.getInt("sexe");
			boolean afficheage=rs.getBoolean("afficheage");
			boolean affichesexe=rs.getBoolean("affichesexe");
			participant = new Participant(id, pseudo, nbravis, sexe, note, photostr,  datenaissance, afficheage, affichesexe);
			retour.add(participant);
		}
		
		 requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,personne.nbravis,personne.sexe,personne.note,"
				+ "personne.datenaissance,personne.afficheage,personne.affichesexe from personne,activite where personne.idpersonne=activite.idpersonne"
				+ " and activite.idactivite=?  ";
		 preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idactivite);
		 rs = preparedStatement.executeQuery();
		 
		while (rs.next()) {
			int id = rs.getInt("idpersonne");
			String pseudo = rs.getString("prenom");
			if (pseudo==null)pseudo=" ";
			String photostr = rs.getString("photo");
			Date datenaissance = rs.getDate("datenaissance");
			double note=rs.getDouble("note");
			int nbravis=rs.getInt("nbravis");
			int sexe=rs.getInt("sexe");
			boolean afficheage=rs.getBoolean("afficheage");
			boolean affichesexe=rs.getBoolean("affichesexe");
			participant = new Participant(id, pseudo, nbravis, sexe, note, photostr,  datenaissance, afficheage, affichesexe);
			retour.add(0,participant);
		}
		
		

		rs.close();
		preparedStatement.close();

		return retour;

	}

}
