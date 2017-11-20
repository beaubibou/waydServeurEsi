package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.joda.time.DateTime;

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
	
	public static ArrayList<ProblemeBean> getListProbleme(int etatProbleme,DateTime debut,DateTime fin) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ProblemeBean> retour = new ArrayList<ProblemeBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT email, probleme, id, d_creation  FROM problemeconnexion where d_creation between ? and ?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(debut.toDate().getTime()));
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(fin.toDate().getTime()));
		
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
	
	public static boolean supprime(int idProbleme) {
		// TODO Auto-generated method stub
	
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

}
