package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import website.enumeration.TypePhoto;
import website.metier.DureeBean;
import website.metier.ProfilBean;
import website.metier.QuandBean;
import website.metier.QuantiteWaydeurBean;
import website.metier.RayonBean;
import website.metier.SexeBean;
import website.metier.TypeAccess;
import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeEtatMessage;
import website.metier.TypeUser;

public class CacheValueDAO {
	private static final Logger LOG = Logger.getLogger(CacheValueDAO.class);
	
	static Map<Integer, TypeActiviteBean> mapTypeActivite = new HashMap<Integer, TypeActiviteBean>();
	static final Map<TypePhoto, String> mapPhotoCache = new HashMap<TypePhoto, String>();
	static ArrayList<TypeEtatActivite> ListTypeEtatActivite = new ArrayList<TypeEtatActivite>();
	static ArrayList<TypeEtatMessage> listTypeEtatMessage = new ArrayList<TypeEtatMessage>();
	static ArrayList<TypeActiviteBean> listTypeActivitePro = new ArrayList<TypeActiviteBean>();
	static ArrayList<RayonBean> listRayon = new ArrayList<RayonBean>();


	static {

		initMapPhotoActivite();
	

	}

	private static void initMapPhotoActivite() {
		// TODO Auto-generated method stub
		for (TypeActiviteBean typeActivite : getListTypeActiviteBeanFull())
			mapTypeActivite.put(typeActivite.id, typeActivite);

	}

	public static ArrayList<TypeActiviteBean> getListTypeActiviteBeanFull() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeActiviteBean> retour = new ArrayList<TypeActiviteBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle,photo FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();
			retour.add(new TypeActiviteBean(0, "Tous"));
			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				String photo = rs.getString("photo");
				retour.add(new TypeActiviteBean(id, libelle, photo));
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

	public static boolean updatePhotoTypeActivite(int id, String photo) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
	
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  type_activite set photo=? "
					+ " WHERE idtypeactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, photo);
			preparedStatement.setInt(2, id);
			preparedStatement.execute();
			connexion.commit();

			return true;

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			try {
				connexion.rollback();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				preparedStatement.close();
				connexion.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return false;

	}

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
			retour.add(new TypeAccess(0, "Tous"));

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

		// SOllicite le cache
		
		if (listTypeActivitePro.size()>0)
			return listTypeActivitePro;
		
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite where typeuser=2 order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				listTypeActivitePro.add(new TypeActiviteBean(id, libelle));
			}

			return listTypeActivitePro;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return listTypeActivitePro;
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
			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite where"
					+ " typeuser=3 or typeuser=2 order by ordre asc";
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
			listDuree.add(new DureeBean(f * 60, f + " Heure"));

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

	public static ArrayList<TypeEtatActivite> getListEtatActivite() {
		// TODO Auto-generated method stub

		
		
		if (ListTypeEtatActivite.size()==0){
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.TOUTES, "Toutes"));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.ENCOURS, "En cours"));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.PLANIFIEE, "Planif�e"));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.TERMINEE, "Termin�es"));
	
		}
		
		
		
		
		return ListTypeEtatActivite;
	}
	
	public static ArrayList<TypeEtatMessage> getListEtatMessage() {
		// TODO Auto-generated method stub

		if (listTypeEtatMessage.size()==0){
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.LU, "Lu"));
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.NONLU, "Non lus"));
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.TOUS, "Tous"));
		}

		return listTypeEtatMessage;
	}

	public static ArrayList<QuandBean> getListQuand() {
		// TODO Auto-generated method stub

		ArrayList<QuandBean> listQuand = new ArrayList<QuandBean>();
		listQuand.add(new QuandBean(0, "Maintenant"));
		for (int f = 1; f < 9; f++) {
			listQuand.add(new QuandBean(f, "Dans " + f + " heures"));
		}
		listQuand.add(new QuandBean(-1, "Toutes"));
		return listQuand;

	}

	public static String getPhotoTypeActivite(Integer idType) {

		TypeActiviteBean typeActiviteBean = mapTypeActivite.get(idType);

		if (typeActiviteBean == null)
			return "";
		return typeActiviteBean.getPhoto();

	}

	public static String geLibelleTypeActivite(Integer idType) {

		TypeActiviteBean typeActiviteBean = mapTypeActivite.get(idType);

		if (typeActiviteBean == null)
			return "Libelle inconnu";
		return typeActiviteBean.getLibelle();

	}

	public static ArrayList<RayonBean> getListRayon() {
		// TODO Auto-generated method stub
		
		if (listRayon.size()==0){

		for (int f = 1; f < 9; f++) {
			listRayon.add(new RayonBean(f, "" + f + " Km"));
		}
		}
		
		return listRayon;
	}

	public static void updateCachePhoto(int id, String stringPhoto,
			String libelle) {
		mapTypeActivite.put(id, new TypeActiviteBean(id, libelle, stringPhoto));

	}

	public static void addPhotoCache(TypePhoto inconnu, String photoStr) {
		// TODO Auto-generated method stub
		mapPhotoCache.put(inconnu, photoStr);

	}

	public static String getPhoto(TypePhoto typePhoto) {
		// TODO Auto-generated method stub
		return mapPhotoCache.get(typePhoto);
	}
}
