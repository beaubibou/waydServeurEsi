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
import website.metier.SexeBean;



public class SexeDAO {

	public static ArrayList<SexeBean> getListSexe() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SexeBean> retour = new ArrayList<SexeBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT  sexe.id,sexe.libelle from sexe ";
			preparedStatement = connexion.prepareStatement(requete);
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new SexeBean(id,libelle));
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
