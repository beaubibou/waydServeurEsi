package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;

public class SuggestionDAO {
	private static final Logger LOG = Logger.getLogger(SuggestionDAO.class);

	Connection connexion;

	public SuggestionDAO(Connection connexion) {
		this.connexion = connexion;

	}

	public void addSuggestion(int idpersonne, String suggestion)
			throws SQLException {
		String requete = "INSERT into amelioration(idpersonne, suggestion,d_creation)"
				+ " VALUES (?, ?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, suggestion);
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(new Date().getTime()));

			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			CxoPool.closePS(preparedStatement);
		}

	}

	public void addPrbConnexion(String probleme, String email)
			throws SQLException {

		String requete = "INSERT into problemeconnexion(probleme, email,d_creation)"
				+ " VALUES (?, ?,?)";
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, probleme);
			preparedStatement.setString(2, email);
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(new Date().getTime()));

			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			CxoPool.closePS(preparedStatement);
		}

	}

}
