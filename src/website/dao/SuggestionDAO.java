package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import wayde.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;
import website.metier.SuggestionBean;
import website.metier.admin.EtatProbleme;
import website.metier.admin.EtatSuggestion;

public class SuggestionDAO {
	private static final Logger LOG = Logger.getLogger(SuggestionDAO.class);

	public static ArrayList<SuggestionBean> getListSuggestion() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SuggestionBean> retour = new ArrayList<SuggestionBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT id,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo,amelioration.lu  FROM personne,amelioration where "
					+ "amelioration.idpersonne=personne.idpersonne";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idPersonne = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				boolean lu = rs.getBoolean("lu");
				retour.add(new SuggestionBean(id, idPersonne, suggestion,
						d_creation, pseudo,lu));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	public static ArrayList<SuggestionBean> getListSuggestion(int etatProbleme,
			DateTime debut, DateTime fin) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SuggestionBean> retour = new ArrayList<SuggestionBean>();

		try {
			connexion = CxoPool.getConnection();
			String requete ="";
			
			switch (etatProbleme) {

			case EtatSuggestion.TOUS:
				requete  = "SELECT id,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo,amelioration.lu"
						+ "  FROM personne,amelioration"
						+ " where "
						+ "amelioration.idpersonne=personne.idpersonne and d_creation between ? and ?";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				break;
			
			case EtatSuggestion.CLOTURE:
				requete  = "SELECT id,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo,amelioration.lu"
						+ "  FROM personne,amelioration"
						+ " where "
						+ "amelioration.idpersonne=personne.idpersonne and lu=true and d_creation between ? and ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				break;
		
			case EtatSuggestion.NONCLOTOURE:
				requete  = "SELECT id,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo,amelioration.lu"
						+ "  FROM personne,amelioration"
						+ " where "
						+ "amelioration.idpersonne=personne.idpersonne and lu=false and d_creation between ? and ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				break;

			}
		
			requete=requete+" order by id desc";
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idPersonne = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				boolean lu = rs.getBoolean("lu");
				retour.add(new SuggestionBean(id, idPersonne, suggestion,
						d_creation, pseudo,lu));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static boolean addSuggestion(int idPersonne, String mail, String message) {
		Connection connexion = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT into amelioration(idpersonne, suggestion,mail,d_creation)"
					+ " VALUES (?, ?,?,?)";

			PreparedStatement preparedStatement = connexion.prepareStatement(
					requete, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.setString(2, message);
			preparedStatement.setString(3, mail);
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.execute();
			preparedStatement.execute();
			connexion.commit();
			preparedStatement.close();
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;

	}

	public static boolean supprime(int idSuggestion) {
		// TODO Auto-generated method stub
	
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM amelioration where  id=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idSuggestion);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub	
	}
	
	public static MessageServeur lireProbleme(int idMessage) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "update amelioration set lu=true   where  id=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new MessageServeur(false,
				"Erreur dans la métode lit probleme admin");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub// TODO Auto-generated method stub

	}
}
