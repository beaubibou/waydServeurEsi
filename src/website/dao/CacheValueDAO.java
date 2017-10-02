package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import website.metier.DureeBean;
import website.metier.QuandBean;
import website.metier.QuantiteWaydeurBean;
import website.metier.RayonBean;
import website.metier.SexeBean;
import website.metier.TypeAccess;
import website.metier.TypeActiviteBean;
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
			
			retour.add(new TypeUser(0, "Tous"));
			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeUser(id, libelle));
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
				retour.add(new TypeAccess(id, libelle));
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
				retour.add(new SexeBean(id, libelle));
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

	public static ArrayList<TypeActiviteBean> getListTypeActivitePro() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeActiviteBean> retour = new ArrayList<TypeActiviteBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				retour.add(new TypeActiviteBean(id, libelle));
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

	public static ArrayList<TypeActiviteBean> getListTypeActiviteWaydeur() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeActiviteBean> retour = new ArrayList<TypeActiviteBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				retour.add(new TypeActiviteBean(id, libelle));
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

	public static ArrayList<TypeActiviteBean> getListTypeActiviteToutes() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeActiviteBean> retour = new ArrayList<TypeActiviteBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();
			retour.add(new TypeActiviteBean(0, "Tous"));
			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				retour.add(new TypeActiviteBean(id, libelle));
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

	public ArrayList<DureeBean> getListDuree() {
		// TODO Auto-generated method stub
		ArrayList<DureeBean> listDuree = new ArrayList<DureeBean>();
		for (int f = 1; f < 3; f++) {
			listDuree.add(new DureeBean(f*60, f + " Heure"));

		}
		return listDuree;
	}

	public ArrayList<QuantiteWaydeurBean> getListQuantiteWaydeur() {
		// TODO Auto-generated method stub

		ArrayList<QuantiteWaydeurBean> listQuantite = new ArrayList<QuantiteWaydeurBean>();
		for (int f = 1; f < 9; f++) {
			listQuantite.add(new QuantiteWaydeurBean(f, f + " Waydeur"));
		}
		return listQuantite;
	}

		public static ArrayList<QuandBean> getListQuand() {
		// TODO Auto-generated method stub
	
		ArrayList<QuandBean> listQuand = new ArrayList<QuandBean>();
		listQuand.add(new QuandBean(0, "Tous"));
		for (int f = 1; f < 9; f++) {
			listQuand.add(new QuandBean(f,"Dans "+f +" heures"));
		}
		return listQuand;
		
		
	}

		public static ArrayList<RayonBean> getListRayon() {
			// TODO Auto-generated method stub
			ArrayList<RayonBean> listRayon = new ArrayList<RayonBean>();
			
			for (int f = 1; f < 9; f++) {
				listRayon.add(new RayonBean(f,""+f +" Km"));
			}
			return listRayon;
		}
}
