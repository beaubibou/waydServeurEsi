package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import website.metier.ProblemeBean;
import website.metier.admin.EtatProbleme;

public class ProblemeDAO {
	
	private static final Logger LOG = Logger.getLogger(ProblemeDAO.class);


	public static ArrayList<ProblemeBean> getListProbleme(int etatProbleme,
			DateTime debut, DateTime fin,int page,int maxResult) {

		long debutlog = System.currentTimeMillis();
		
		int offset=(maxResult)*page;
	
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ProblemeBean> retour = new ArrayList<ProblemeBean>();

		try {

			String requete = "";
			connexion = CxoPool.getConnection();

			switch (etatProbleme) {

			case EtatProbleme.TOUS:
				requete = "SELECT pseudo,email, probleme, id, d_creation,lu  FROM problemeconnexion where"
						+ " d_creation between ? and ? order by id desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
			
			case EtatProbleme.CLOTURE:
				requete = "SELECT pseudo,email, probleme, id, d_creation,lu  FROM problemeconnexion where"
						+ " d_creation between ? and ? and lu=true order by id desc  limit ?  offset ?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setTimestamp(1, new java.sql.Timestamp(debut
						.toDate().getTime()));
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(fin
						.toDate().getTime()));
				preparedStatement.setInt(3, maxResult);
				preparedStatement.setInt(4, offset);
				break;
		
			case EtatProbleme.NONCLOTOURE:
				requete = "SELECT pseudo,email, probleme, id, d_creation,lu  FROM problemeconnexion where"
						+ " d_creation between ? and ? and lu=false order by id desc  limit ?  offset ?";
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
				String probleme = rs.getString("probleme");
				String email = rs.getString("email");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				boolean lu = rs.getBoolean("lu");
				retour.add(new ProblemeBean(id, probleme, email,pseudo, d_creation, lu));
			}
			LogDAO.LOG_DUREE("getListProbleme", debutlog);
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


	public static boolean supprime(int idProbleme) {
		// TODO Auto-generated method stub

		long debut = System.currentTimeMillis();
		
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM problemeconnexion where  id=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idProbleme);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("supprime", debut);
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
			String requete = "update problemeconnexion set lu=true   where  id=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("lireProbleme", debut);
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
}
