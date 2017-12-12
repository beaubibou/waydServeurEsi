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

import org.apache.commons.lang3.exception.ExceptionUtils;
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
import website.metier.admin.FitreAdminSuggestions;

public class SuggestionDAO {
	private static final Logger LOG = Logger.getLogger(SuggestionDAO.class);

	public static ArrayList<SuggestionBean> getListSuggestion() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SuggestionBean> retour = new ArrayList<SuggestionBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT id,mail,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo,amelioration.lu  FROM personne,amelioration where "
					+ "amelioration.idpersonne=personne.idpersonne";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idPersonne = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String pseudo = rs.getString("pseudo");
				String email = rs.getString("mail");
				Date d_creation = rs.getTimestamp("d_creation");
				boolean lu = rs.getBoolean("lu");
				retour.add(new SuggestionBean(id, idPersonne,email, suggestion,
						d_creation, pseudo,lu));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
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
				String email = rs.getString("mail");
				Date d_creation = rs.getTimestamp("d_creation");
				boolean lu = rs.getBoolean("lu");
				retour.add(new SuggestionBean(id, idPersonne,email, suggestion,
						d_creation, pseudo,lu));
			}

			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static boolean addSuggestion(int idPersonne, String mail, String message) {
		Connection connexion = null;
		PreparedStatement preparedStatement=null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT into amelioration(idpersonne, suggestion,mail,d_creation)"
					+ " VALUES (?, ?,?,?)";

			preparedStatement = connexion.prepareStatement(
					requete, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idPersonne);
			preparedStatement.setString(2, message);
			preparedStatement.setString(3, mail);
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.execute();
			connexion.commit();
			preparedStatement.close();
			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.close(connexion, preparedStatement);
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

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			
		} finally {

			CxoPool.close(connexion, preparedStatement);

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

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			
		} finally {

				CxoPool.close(connexion, preparedStatement);
			

		}
		return new MessageServeur(false,
				"Erreur dans la métode lit probleme admin");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub// TODO Auto-generated method stub

	}

	public static ArrayList<SuggestionBean> getListSugestion(FitreAdminSuggestions filtre, int page,int maxResult) {
	
		int offset=(maxResult)*page;
	
		int etatSuggestion=filtre.getEtatSuggestion();
		DateTime debut=filtre.getDateDebutCreation();
		DateTime fin=filtre.getDateFinCreation();
		
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SuggestionBean> retour = new ArrayList<SuggestionBean>();
	
		try {
	
			String requete = "";
			connexion = CxoPool.getConnection();
	
			switch (etatSuggestion) {
	
			case EtatProbleme.TOUS:
				requete = "SELECT personne.idpersonne,prenom as pseudo,personne.mail, suggestion, id, d_creation,lu  FROM amelioration,personne "
						+ "  where personne.idpersonne=amelioration.idpersonne and d_creation between ? and ? order by id desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
			
			case EtatProbleme.CLOTURE:
				requete = "SELECT personne.idpersonne,prenom as pseudo,personne.mail, suggestion, id, d_creation,lu  FROM amelioration,personne "
						+ "  where personne.idpersonne=amelioration.idpersonne and lu=true and d_creation between ? and ? order by id desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
		
			case EtatProbleme.NONCLOTOURE:
				requete = "SELECT personne.idpersonne,prenom as pseudo,personne.mail, suggestion, id, d_creation,lu  FROM amelioration,personne "
						+ "  where personne.idpersonne=amelioration.idpersonne and lu=false and d_creation between ? and ? order by id desc  limit ?  offset ?";
			
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
	
			}
	
			rs = preparedStatement.executeQuery();
	
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idPersonne = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String email = rs.getString("mail");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				
				boolean lu = rs.getBoolean("lu");
				retour.add(new SuggestionBean( id,  idPersonne, email,  suggestion,
						d_creation, pseudo, lu));
			}
			LOG.info(retour);
			return retour;
	
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
	
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {
	
			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
}
