package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.Ami;
import wayde.bean.CxoPool;
import website.metier.ActiviteBean;
import website.metier.AmiBean;
import website.metier.ProblemeBean;
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
				int idPersonne  = rs.getInt("idpersonne");
				String suggestion = rs.getString("suggestion");
				String pseudo = rs.getString("pseudo");
				Date d_creation = rs.getTimestamp("d_creation");
				retour.add(new SuggestionBean(id, idPersonne, suggestion, d_creation,pseudo));
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

}
