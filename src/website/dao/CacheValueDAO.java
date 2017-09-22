package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import website.metier.SexeBean;
import website.metier.TypeAccess;
import website.metier.TypeUser;



public class CacheValueDAO {

	
	public static ArrayList<TypeUser> getListTypeUser() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeUser> retour = new ArrayList<TypeUser>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT  id,libelle from typeuser ";
			preparedStatement = connexion.prepareStatement(requete);
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeUser(id,libelle));
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
	
	public static ArrayList<TypeAccess> getListTypeAccess() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeAccess> retour = new ArrayList<TypeAccess>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT  id,libelle from typeacces ";
			preparedStatement = connexion.prepareStatement(requete);
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeAccess(id,libelle));
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
