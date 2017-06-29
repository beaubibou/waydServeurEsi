package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import website.metier.ProblemeBean;

public class ProblemeDAO {

	public static ArrayList<ProblemeBean> getListProbleme() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ProblemeBean> retour = new ArrayList<ProblemeBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT email, probleme, id, d_creation  FROM problemeconnexion";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String probleme = rs.getString("probleme");
				String email = rs.getString("email");
				Date d_creation = rs.getTimestamp("d_creation");
				retour.add(new ProblemeBean(id,probleme,email,d_creation));
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
