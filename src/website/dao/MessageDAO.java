package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;

public class MessageDAO {
	private static final Logger LOG = Logger.getLogger(MessageDAO.class);

	public static MessageServeur effaceMessage(int idMessage) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM message where  idmessage=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("effaceMessage", debut);
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));

			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return new MessageServeur(false,
				"Erreur dans la métode supprime activité");

		
		

	}

	public static MessageServeur effaceMessages(List<Integer> idMessage) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		StringBuilder param = new StringBuilder();
		for (Integer idmessage : idMessage) {

			param.append(idmessage + ",");

		}

		param.insert(0, "(");
		param.delete(param.length() - 1, param.length());
		param.insert(param.length(), ")");
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM message where idmessage in ?);";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, param.toString());
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

			LogDAO.LOG_DUREE("effaceMessages", debut);

			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}
		return new MessageServeur(false,
				"Erreur dans la métode supprime activité");

		

	}

	public static MessageServeur lireMessage(int idMessage) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "update message set lu=true   where  idmessage=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("lireMessage", debut);
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}
		return new MessageServeur(false,
				"Erreur dans la métode lit message pro");

		
		

	}

	public static String getNbrMessageNonLu(int idPersonne) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrmessagenonlu = 0;
		try {
			connexion = CxoPool.getConnection();

			String requete = "select  count(idmessage) as nbrmessagenonlu from message"
					+ " where (  iddestinataire=? and lu=false and emis=false);";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrmessagenonlu = rs.getInt("nbrmessagenonlu");
			}
			
			LogDAO.LOG_DUREE("getNbrMessageNonLu", debut);

			return new Integer(nbrmessagenonlu).toString();

		} catch (NamingException | SQLException e) {
				LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}

		return new Integer(nbrmessagenonlu).toString();

		
		
	}

	public static void ajouteMessageETclore(String message, int idPersonne,
			int idEmetteur) {
		

	}

	public static boolean ajouteMessage(String message, int idDestinataire,
			int idEmetteur) {
		
		long debut = System.currentTimeMillis();

		String iddiscussion;

		if (idEmetteur < idDestinataire)
			iddiscussion = "" + idEmetteur + "-" + idDestinataire;
		else
			iddiscussion = "" + idDestinataire + "-" + idEmetteur;

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			Date datecreation = Calendar.getInstance().getTime();

			String requete = "INSERT INTO message( corps, idpersonne, datecreation,idactivite,iddestinataire,iddiscussion,lu,emis)  "
					+ "VALUES (?, ?, ?, ?,?,?,true,true);";
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, message);
			preparedStatement.setInt(2, idEmetteur);
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(
					datecreation.getTime()));
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, idDestinataire);
			preparedStatement.setString(6, iddiscussion);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "INSERT INTO message( corps, idpersonne, datecreation,idactivite,iddestinataire,iddiscussion,lu,emis)  VALUES (?, ?, ?, ?,?,?,false,false);";

			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, message);
			preparedStatement.setInt(2, idEmetteur);
			preparedStatement.setTimestamp(3, new java.sql.Timestamp(
					datecreation.getTime()));
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, idDestinataire);
			preparedStatement.setString(6, iddiscussion);
			preparedStatement.execute();

			preparedStatement.close();
			connexion.commit();
			
			LogDAO.LOG_DUREE("ajouteMessage", debut);
			return true;

		} catch (NamingException | SQLException e) {
				LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

		return false;

	}

	public static boolean ajouteMessageProbleme(String message, String email,
			String pseudo) {
	
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			Date datecreation = Calendar.getInstance().getTime();

			String requete = "INSERT INTO problemeconnexion( probleme, email, pseudo,d_creation)  "
					+ "VALUES (?, ? ,?, ?);";
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, message);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, pseudo);
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(
					datecreation.getTime()));

			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("ajouteMessageProbleme", debut);
			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

		return false;

	}

}
