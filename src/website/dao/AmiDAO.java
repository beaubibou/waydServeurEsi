package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import website.metier.AmiBean;

public class AmiDAO {

	public static ArrayList<AmiBean> getListAmi(int idpersonne) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<AmiBean> retour = new ArrayList<AmiBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT   ami.d_creation,personne.login,personne.idpersonne,personne.prenom, personne.nom,personne.photo,personne.note "
					+ "  FROM personne,ami  WHERE personne.idpersonne = ami.idami and ami.idpersonne=?  ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idpersonne");
				String nom = rs.getString("nom");
				String login = rs.getString("login");
				String pseudo = rs.getString("prenom");
				String photostr = rs.getString("photo");
				Date depuisle = rs.getDate("d_creation");
				double note = rs.getDouble("note");
				retour.add(new AmiBean(photostr, pseudo, nom, login, id,
						depuisle, note));
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
