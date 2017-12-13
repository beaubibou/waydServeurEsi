package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import wayd.ws.WBservices;
import wayde.bean.CxoPool;
import wayde.dao.ActiviteDAO;
import website.metier.ParticipantBean;
import website.metier.ProfilBean;

public class ParticipantDAO {
	private static final Logger LOG = Logger.getLogger(ParticipantDAO.class);

	Connection connexion;

	public ParticipantDAO(Connection connexion) {
		this.connexion = connexion;

	}

	public ArrayList<ParticipantBean> getListPaticipant(int idactivite)
			throws SQLException {
	
		long debut = System.currentTimeMillis();
		
		ParticipantBean participant = null;
		ArrayList<ParticipantBean> retour = new ArrayList<ParticipantBean>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {

			String requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,personne.nbravis,"
					+ "personne.sexe,personne.note,personne.datenaissance,personne.afficheage,"
					+ "personne.affichesexe,personne.typeuser,personne.admin "
					+ " from personne,participer"
					+ " where personne.idpersonne=participer.idpersonne"
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
				int typeuser = rs.getInt("typeuser");
				boolean admin = rs.getBoolean("admin");
				
				participant = new ParticipantBean(id, pseudo, nbravis, sexe,
						note, photostr, datenaissance, afficheage, affichesexe,typeuser,admin);
				retour.add(participant);
			}

			preparedStatement.close();
			rs.close();

			requete = " SELECT   personne.prenom,personne.photo,personne.idpersonne,"
					+ " personne.nbravis,personne.sexe,personne.note,"
					+ " personne.datenaissance,personne.afficheage,personne.affichesexe,personne.typeuser,personne.admin "
					+ " from personne,activite"
					+ " where personne.idpersonne=activite.idpersonne"
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
				int typeuser = rs.getInt("typeuser");
				boolean admin = rs.getBoolean("admin");
				participant = new ParticipantBean(id, pseudo, nbravis, sexe,
						note, photostr, datenaissance, afficheage, affichesexe,typeuser,admin);
				retour.add(0, participant);
			}
			LogDAO.LOG_DUREE("getListParticipant", debut);
			return retour;

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement);
		}

	}



}
