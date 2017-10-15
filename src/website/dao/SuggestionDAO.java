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

import wayde.bean.CxoPool;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;
import website.metier.SuggestionBean;

public class SuggestionDAO {

	public static ArrayList<SuggestionBean> getListSuggestion() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SuggestionBean> retour = new ArrayList<SuggestionBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT id,personne.idpersonne, suggestion, d_creation,personne.prenom as pseudo  FROM personne,amelioration where "
					+ "amelioration.idpersonne=personne.idpersonne";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int idPersonne = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				retour.add(new SuggestionBean(id, idPersonne, suggestion,
						d_creation, pseudo));
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
}
