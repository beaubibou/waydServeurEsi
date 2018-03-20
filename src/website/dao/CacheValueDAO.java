package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import texthtml.pro.ListeValeurText;
import wayde.bean.CxoPool;
import website.enumeration.TypePhoto;
import website.metier.DureeBean;
import website.metier.QuandBean;
import website.metier.QuantiteWaydeurBean;
import website.metier.RayonBean;
import website.metier.SexeBean;
import website.metier.TypeAccess;
import website.metier.TypeActiveActivite;
import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeEtatLogPerf;
import website.metier.TypeEtatLogs;
import website.metier.TypeEtatMessage;
import website.metier.TypeEtatProfil;
import website.metier.TypeEtatValide;
import website.metier.TypeGratuitActivite;
import website.metier.TypeSignalement;
import website.metier.TypeUser;
import website.metier.admin.EtatProbleme;
import website.metier.admin.EtatSuggestion;

public class CacheValueDAO {
	private static final Logger LOG = Logger.getLogger(CacheValueDAO.class);
	
	static Map<Integer, TypeActiviteBean> mapTypeActivite = new HashMap<Integer, TypeActiviteBean>();
	static final Map<TypePhoto, String> mapPhotoCache = new HashMap<TypePhoto, String>();
	static ArrayList<TypeEtatActivite> ListTypeEtatActivite = new ArrayList<TypeEtatActivite>();
	static ArrayList<TypeActiveActivite> ListTypeActiveActivite = new ArrayList<TypeActiveActivite>();
	static ArrayList<TypeEtatMessage> listTypeEtatMessage = new ArrayList<TypeEtatMessage>();
	static ArrayList<TypeActiviteBean> listTypeActivitePro = new ArrayList<TypeActiviteBean>();
	static ArrayList<RayonBean> listRayon = new ArrayList<RayonBean>();
	static ArrayList<EtatProbleme> listTypeEtatProbleme = new ArrayList<EtatProbleme>();
	static ArrayList<EtatSuggestion> listTypeEtatSuggestion = new ArrayList<EtatSuggestion>();
	static ArrayList<TypeActiviteBean> tousTypeActivite = new ArrayList<TypeActiviteBean>();
	static ArrayList<TypeGratuitActivite> ListTypeGRATUITActivite = new ArrayList<TypeGratuitActivite>();
	static ArrayList<TypeGratuitActivite> ListTypeGRATUITActiviteREQUETE = new ArrayList<TypeGratuitActivite>();

	
	
	

	static {

		initMapPhotoActivite();
	

	}

	private static void initMapPhotoActivite() {
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
			retour.add(new TypeActiviteBean(TypeActiviteBean.TOUS, ListeValeurText.TOUS));
			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				String photo = rs.getString("photo");
				retour.add(new TypeActiviteBean(id, libelle, photo));
			}

			return retour;

		} catch (SQLException | NamingException e) {
				LOG.error( ExceptionUtils.getStackTrace(e));
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
			LOG.error( ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
		} finally {

			CxoPool.close(connexion, preparedStatement);

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

			retour.add(new TypeUser(0, ListeValeurText.TOUS));
			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeUser(id, libelle));
			}
		
			return retour;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}
	
	public static ArrayList<TypeUser> getListTypeUserAdmin() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<TypeUser> retour = new ArrayList<TypeUser>();

		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT  id,libelle from typeuser ";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			retour.add(new TypeUser(TypeUser.TOUS, ListeValeurText.TOUS));
		
			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeUser(id, libelle));
			}
			
			retour.add(new TypeUser(TypeUser.ADMIN, ListeValeurText.ADMIN));
			return retour;

		} catch (SQLException | NamingException e) {
			
			LOG.error( ExceptionUtils.getStackTrace(e));
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
			retour.add(new TypeAccess(0, ListeValeurText.TOUS));

			while (rs.next()) {
				int id = rs.getInt("id");
				String libelle = rs.getString("libelle");
				retour.add(new TypeAccess(id, libelle));
			}

			return retour;

		} catch (SQLException | NamingException e) {
		
			LOG.error( ExceptionUtils.getStackTrace(e));
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
			
			LOG.error( ExceptionUtils.getStackTrace(e));
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
		
			LOG.error( ExceptionUtils.getStackTrace(e));
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
			
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static ArrayList<TypeActiviteBean> getListTypeActiviteToutes() {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		if (tousTypeActivite.size()>0)
			return tousTypeActivite;
				
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idtypeactivite,nom as libelle FROM type_activite order by ordre asc";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			tousTypeActivite.add(new TypeActiviteBean(0, ListeValeurText.TOUS));
			while (rs.next()) {
				int id = rs.getInt("idtypeactivite");
				String libelle = rs.getString("libelle");
				tousTypeActivite.add(new TypeActiviteBean(id, libelle));
			}

			return tousTypeActivite;

		} catch (SQLException | NamingException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
			return tousTypeActivite;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public ArrayList<DureeBean> getListDuree() {
		
		ArrayList<DureeBean> listDuree = new ArrayList<DureeBean>();
		
		listDuree.add(new DureeBean( 60, 1 + " Heure"));
		
		for (int f = 2; f <=8 ; f++) {
			listDuree.add(new DureeBean(f * 60, f + " Heures"));

		}
		return listDuree;
	}

	public ArrayList<QuantiteWaydeurBean> getListQuantiteWaydeur() {
	
		ArrayList<QuantiteWaydeurBean> listQuantite = new ArrayList<QuantiteWaydeurBean>();
		for (int f = 1; f < 9; f++) {
			listQuantite.add(new QuantiteWaydeurBean(f, f + " Waydeur"));
		}
		return listQuantite;
	}

	public static ArrayList<TypeSignalement> getListTypeSignalementActivite() {
	
		ArrayList<TypeSignalement> listEtat = new ArrayList<TypeSignalement>();
		listEtat.add(new TypeSignalement(TypeSignalement.TOUS, ListeValeurText.TOUS));
		listEtat.add(new TypeSignalement(TypeSignalement.AUMOINSUNE,  ListeValeurText.AUMOINSUN));
		listEtat.add(new TypeSignalement(TypeSignalement.MOINSDE10, ListeValeurText.MOINSDE10));
		listEtat.add(new TypeSignalement(TypeSignalement.PLUSDE10,  ListeValeurText.PLUSDE10));
	
		
		
		return listEtat;
	
	}

	public static ArrayList<TypeGratuitActivite> getListGratuitActiviteRequete() {
		
		if (ListTypeGRATUITActiviteREQUETE.isEmpty()){
			ListTypeGRATUITActiviteREQUETE.add(new TypeGratuitActivite(TypeGratuitActivite.GRATUITE_INCONNU,  ListeValeurText.GRATUITE_INCONNU));
			ListTypeGRATUITActiviteREQUETE.add(new TypeGratuitActivite(TypeGratuitActivite.GRATUIT,  ListeValeurText.GRATUIT));
			ListTypeGRATUITActiviteREQUETE.add(new TypeGratuitActivite(TypeGratuitActivite.PAYANT,  ListeValeurText.PAYANT));
			ListTypeGRATUITActiviteREQUETE.add(new TypeGratuitActivite(TypeGratuitActivite.TOUS,  ListeValeurText.TOUTES));
		}
		
		
		
		
		return ListTypeGRATUITActiviteREQUETE;
	}
	
	public static ArrayList<TypeGratuitActivite> getListGratuitActivite() {

		
		
		if (ListTypeGRATUITActivite.isEmpty()){
			ListTypeGRATUITActivite.add(new TypeGratuitActivite(TypeGratuitActivite.GRATUITE_INCONNU,  ListeValeurText.GRATUITE_INCONNU));
			ListTypeGRATUITActivite.add(new TypeGratuitActivite(TypeGratuitActivite.GRATUIT,  ListeValeurText.GRATUIT));
			ListTypeGRATUITActivite.add(new TypeGratuitActivite(TypeGratuitActivite.PAYANT,  ListeValeurText.PAYANT));
	
		}
		
		
		
		
		return ListTypeGRATUITActivite;
	}
	
	public static ArrayList<TypeActiveActivite> getListActivteActivite() {
		
		
		if (ListTypeActiveActivite.isEmpty()){
		
			ListTypeActiveActivite.add(new TypeActiveActivite(TypeActiveActivite.ACTIVE,  ListeValeurText.ACTIVE));
			ListTypeActiveActivite.add(new TypeActiveActivite(TypeActiveActivite.INACTIVE,  ListeValeurText.INACTIF));
		
		}
		
		
		return ListTypeActiveActivite;
	}
	
	public static ArrayList<TypeEtatActivite> getListEtatActivite() {

		
		
		if (ListTypeEtatActivite.size()==0){
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.TOUTES,  ListeValeurText.TOUTES));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.ENCOURS,  ListeValeurText.ENCOURS));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.PLANIFIEE,  ListeValeurText.PLANIFIEES));
			ListTypeEtatActivite.add(new TypeEtatActivite(TypeEtatActivite.TERMINEE, ListeValeurText.TERMINEES));
	
		}
		
		
		
		
		return ListTypeEtatActivite;
	}
	
	public static ArrayList<TypeEtatMessage> getListEtatMessage() {
		

		if (listTypeEtatMessage.size()==0){
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.LU, ListeValeurText.LU));
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.NONLU, ListeValeurText.NONLU));
		listTypeEtatMessage.add(new TypeEtatMessage(TypeEtatMessage.TOUS, ListeValeurText.TOUS));
		}

		return listTypeEtatMessage;
	}
	
	public static ArrayList<EtatProbleme> getListEtatProbleme() {
		

		if (listTypeEtatProbleme.size()==0){
			listTypeEtatProbleme.add(new EtatProbleme(EtatProbleme.CLOTURE, ListeValeurText.CLOTURE));
			listTypeEtatProbleme.add(new EtatProbleme(EtatProbleme.NONCLOTOURE, ListeValeurText.NONCLOTURE));
			listTypeEtatProbleme.add(new EtatProbleme(EtatProbleme.TOUS, ListeValeurText.TOUS));
		}

		return listTypeEtatProbleme;
	}

	
	public static ArrayList<EtatSuggestion> getListEtatSuggestions() {
		

		if (listTypeEtatSuggestion.size()==0){
			listTypeEtatSuggestion.add(new EtatSuggestion(EtatSuggestion.CLOTURE, ListeValeurText.CLOTURE));
			listTypeEtatSuggestion.add(new EtatSuggestion(EtatSuggestion.NONCLOTOURE, ListeValeurText.NONCLOTURE));
			listTypeEtatSuggestion.add(new EtatSuggestion(EtatSuggestion.TOUS, ListeValeurText.TOUS));
		}

		return listTypeEtatSuggestion;
	}
	public static ArrayList<QuandBean> getListQuand() {
		

		ArrayList<QuandBean> listQuand = new ArrayList<QuandBean>();
		listQuand.add(new QuandBean(0, "Maintenant"));
		for (int f = 1; f < 9; f++) {
			listQuand.add(new QuandBean(f, "Dans " + f + " heures"));
		}
		listQuand.add(new QuandBean(-1, "Toutes"));
		return listQuand;

	}
	
	public static ArrayList<TypeEtatProfil> getListEtatProfil() {
		

		ArrayList<TypeEtatProfil> listEtat = new ArrayList<TypeEtatProfil>();
		listEtat.add(new TypeEtatProfil(TypeEtatProfil.TOUTES, ListeValeurText.TOUS));
		listEtat.add(new TypeEtatProfil(TypeEtatProfil.ACTIF, ListeValeurText.ACTIF));
		listEtat.add(new TypeEtatProfil(TypeEtatProfil.INACTIF,ListeValeurText.INACTIF));
		
		return listEtat;

	}
	
	public static ArrayList<TypeEtatValide> getListEtatValide() {
		

		ArrayList<TypeEtatValide> listEtat = new ArrayList<TypeEtatValide>();
		listEtat.add(new TypeEtatValide(TypeEtatValide.VALIDE, ListeValeurText.VALIDE));
		listEtat.add(new TypeEtatValide(TypeEtatValide.EN_ATTENTE, ListeValeurText.EN_ATTENTE));
		listEtat.add(new TypeEtatValide(TypeEtatValide.TOUS, ListeValeurText.TOUS));
			
		
		return listEtat;

	}
	
	public static ArrayList<TypeSignalement> getListTypeSignalementProfil() {
		

		ArrayList<TypeSignalement> listEtat = new ArrayList<TypeSignalement>();
		listEtat.add(new TypeSignalement(TypeSignalement.TOUS,ListeValeurText.TOUS));
		listEtat.add(new TypeSignalement(TypeSignalement.AUMOINSUNE, ListeValeurText.AUMOINSUN));
		listEtat.add(new TypeSignalement(TypeSignalement.MOINSDE10, ListeValeurText.MOINSDE10));
		listEtat.add(new TypeSignalement(TypeSignalement.PLUSDE10, ListeValeurText.PLUSDE10));
	
		
		return listEtat;

	}
	
	
	
	public static ArrayList<TypeEtatLogs> getListTypeEtatLogs() {
		

		ArrayList<TypeEtatLogs> listEtat = new ArrayList<TypeEtatLogs>();
		listEtat.add(new TypeEtatLogs(TypeEtatLogs.TOUTES,ListeValeurText.TOUS));
		listEtat.add(new TypeEtatLogs(TypeEtatLogs.DEBUG, ListeValeurText.LOG4J_DEBUG));
		listEtat.add(new TypeEtatLogs(TypeEtatLogs.INFO, ListeValeurText.LOG4J_INFO));
		listEtat.add(new TypeEtatLogs(TypeEtatLogs.WARNING, ListeValeurText.LOG4J_WARNING));
		listEtat.add(new TypeEtatLogs(TypeEtatLogs.ERROR, ListeValeurText.LOG4J_ERREUR));
			
		return listEtat;

	}

	public static ArrayList<TypeEtatLogPerf> getListTypeEtatLogPerf() {
		

		ArrayList<TypeEtatLogPerf> listEtat = new ArrayList<TypeEtatLogPerf>();
		listEtat.add(new TypeEtatLogPerf(TypeEtatLogPerf.ACTIVE,ListeValeurText.ACTIVE));
		listEtat.add(new TypeEtatLogPerf(TypeEtatLogPerf.DESACTIVE, ListeValeurText.DESACTIVE));
				
		return listEtat;

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
		
		mapPhotoCache.put(inconnu, photoStr);

	}

	public static String getPhoto(TypePhoto typePhoto) {
		
		return mapPhotoCache.get(typePhoto);
	}
}
