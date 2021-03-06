package website.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.naming.NamingException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.makers.ThumbnailMaker;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import sun.misc.BASE64Encoder;
import texthtml.pro.Erreur_HTML;
import threadpool.PoolThreadGCM;
import wayd.ws.TextWebService;
import wayde.bean.Activite;
import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import wayde.bean.Personne;
import wayde.bean.TypeActivite;
import wayde.dao.ParticipationDAO;
import wayde.dao.SignalementDAO;
import website.metier.ActiviteAjax;
import website.metier.ActiviteBean;
import website.metier.ActiviteCarpeDiem;
import website.metier.EvenementFaceBook;
import website.metier.IndicateurWayd;
import website.metier.Outils;
import website.metier.ParticipantBean;
import website.metier.PhotoActiviteBean;
import website.metier.ProfilBean;
import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeEtatMessage;
import website.metier.TypeGratuitActivite;
import website.metier.TypeSignalement;
import website.metier.TypeUser;
import website.metier.admin.FitreAdminActivites;
import carpediem.DateEvenementOpenAgenda;
import carpediem.EvenementOpenAGenda;
import carpediem.ImportCarpe;
import fcm.ServeurMethodes;
import gcmnotification.EffaceActiviteGcm;

public class ActiviteDAO {

	private static final Logger LOG = Logger.getLogger(ActiviteDAO.class);
	Connection connexion;

	public ActiviteDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ActiviteDAO() {

	}

	public static boolean isDejaTraiteCarpediemExist(String url) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT lien from carpediem where lien=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, url);
			rs = preparedStatement.executeQuery();

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;

	}

	public static ArrayList<PhotoActiviteBean> getListPhotoActivite(
			int idactivite) {

		ArrayList<PhotoActiviteBean> listPhotos = new ArrayList<PhotoActiviteBean>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT * from photo_activite where idactivite=?";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setInt(1, idactivite);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String photo = rs.getString("photo");
				int id = rs.getInt("id");
				int idActivite = rs.getInt("idactivite");
				listPhotos.add(new PhotoActiviteBean(id, idActivite, photo));
			}
			rs.close();
			preparedStatement.close();
			return listPhotos;

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return listPhotos;

		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static boolean addPhotoActivite(String photo, int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO photo_activite(idactivite, photo)  VALUES ( ?, ?)";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.setString(2, photo);
			preparedStatement.execute();
			preparedStatement.close();

			LogDAO.LOG_DUREE("ajoutePhotoActivite", debut);

			connexion.commit();

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return true;

	}

	public static MessageServeur supprimePhotoActivite(int id, int idactivite) {
		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "delete from photo_activite where id=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			LogDAO.LOG_DUREE("supprimePhotoActivite", debut);

			connexion.commit();

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return new MessageServeur(true, "ok");

	}

	public static int getNbrPhoto(int idactivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int nbrPhoto = 0;
		try {
			connexion = CxoPool.getConnection();

			String requete = " SELECT count( *) as nbrPhoto  FROM photo_activite where idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				nbrPhoto = rs.getInt("nbrPhoto");

			}

			LogDAO.LOG_DUREE("getNbrPhoto", debut);
			return nbrPhoto;

		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return nbrPhoto;

		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static boolean addNbrVu(int idpersonne, int idactivite,
			int idorganisateur) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		if (idorganisateur == idpersonne)
			return false;

		if (isDejaVu(idpersonne, idactivite))
			return false;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO nbrvu("
					+ "   idpersonne, idactivite)" + "	VALUES (?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "UPDATE activite  SET  nbrvu=nbrvu+1 WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			CxoPool.closeConnection(connexion);
			LogDAO.LOG_DUREE("addNbrVu", debut);

			return true;

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

		return false;

	}

	private static boolean isDejaVu(int idpersonne, int idactivite) {
		long debut = System.currentTimeMillis();
		boolean retour = false;
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from nbrvu where idpersonne=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			rs = preparedStatement.executeQuery();

			LogDAO.LOG_DUREE("isDejaVu", debut);

			if (rs.next())
				retour = true;

			CxoPool.close(connexion, preparedStatement, rs);

			return retour;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isFavoriDejaSignale(int idpersonne, int idactivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from favori where idpersonne=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			rs = preparedStatement.executeQuery();

			LogDAO.LOG_DUREE("isFavoriDejaSignale", debut);

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isInteretDejaSignale(int idpersonne, int idactivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idpersonne from interet where idpersonne=? and idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			rs = preparedStatement.executeQuery();

			LogDAO.LOG_DUREE("isInteretDejaSignale", debut);

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public static boolean isDejaExits(double latitude, double longitude,
			Date datedebut, Date datefin) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT idactivite from activite where latitude=? and longitude=? and datedebut=? and datefin=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setDouble(1, latitude);
			preparedStatement.setDouble(2, longitude);
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(datefin.getTime()));
			rs = preparedStatement.executeQuery();

			LogDAO.LOG_DUREE("Activite existante rejetée", debut);

			if (rs.next())
				return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return false;
	}

	public ArrayList<ActiviteAjax> getListActiviteAjaxMap(double malatitude,
			double malongitude, double nelat, double nelon, double swlat,
			double swlon) {

		long debut = System.currentTimeMillis();

		ArrayList<ActiviteAjax> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			double latMin = swlat;
			double latMax = nelat;
			double longMin = swlon;
			double longMax = nelon;
			ActiviteAjax activite = null;

			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
					+ "personne.note,personne.nbravis as totalavis,personne.photo,activite.nbrwaydeur as nbrparticipant,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,"
					+ "activite.nbmaxwayd,activite.typeuser   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne   "
					+ "and  datefin>? "
					+ " and activite.latitude between ? and ?"
					+ " and activite.longitude between ? and ? FETCH FIRST 40 ROWS ONLY;";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.setDouble(2, latMin);
			preparedStatement.setDouble(3, latMax);
			preparedStatement.setDouble(4, longMin);
			preparedStatement.setDouble(5, longMax);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int typeUser = rs.getInt("typeuser");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");

				if (pseudo == null)
					pseudo = "";
				String photo = rs.getString("photo");

				activite = new ActiviteAjax(id, titre, libelle, idorganisateur,
						latitude, longitude, photo, nom, pseudo, typeUser,
						idtypeactivite);
				retour.add(activite);

			}

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

		LogDAO.LOG_DUREE("getListActiviteAjaxMap", debut);
		return retour;
	}

	public ArrayList<ActiviteAjax> getListActiviteEncoursAjax(
			double centreLatitude, double centreLongitude) throws SQLException {

		long debut = System.currentTimeMillis();

		ActiviteAjax activite = null;
		ArrayList<ActiviteAjax> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,  personne.datenaissance,  personne.sexe,    personne.nom,    personne.idpersonne,"
					+ "personne.photo,"
					+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.typeuser   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne "
					+ "and  activite.datefin>?  ";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(new Date().getTime()));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");
				if (pseudo == null)
					pseudo = "";
				String photo = rs.getString("photo");
				int typeUser = rs.getInt("typeuser");
				activite = new ActiviteAjax(id, titre, libelle, idorganisateur,
						latitude, longitude, photo, nom, pseudo, typeUser,
						idtypeactivite);
				retour.add(activite);
			}

		} catch (NamingException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);

		}
		LogDAO.LOG_DUREE("getListActiviteEncoursAjax", debut);
		return retour;

	}

	public boolean isInscrit(ActiviteBean activite, int idpersonne) {

		long debut = System.currentTimeMillis();

		String requete = "SELECT  idpersonne FROM public.participer "
				+ "where( idpersonne=? and idactivite=?);";

		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, activite.getId());
			ResultSet rs = preparedStatement.executeQuery();

			LogDAO.LOG_DUREE("isInscrit", debut);

			if (rs.next()) {
				rs.close();
				preparedStatement.close();
				return true;

			} else {
				rs.close();
				preparedStatement.close();
				return false;

			}

		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closePS(preparedStatement);
		}
		return false;

	}

	public static boolean updateActivitePro(String titre, String commentaire,
			Date datedebut, Date datefin, String adresse, double latitude,
			double longitude, int idtypeactivite, int idactivite) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			String requete = "UPDATE  activite set titre=?, libelle=?,  datedebut=?, datefin=?,  adresse=?,"
					+ " latitude=?,  longitude=?,  idtypeactivite=?"
					+ " WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2,
					Outils.getStringStatement(commentaire));
			preparedStatement.setTimestamp(3,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(datefin.getTime()));
			preparedStatement.setString(5, adresse);

			preparedStatement.setDouble(6, latitude);
			preparedStatement.setDouble(7, longitude);
			preparedStatement.setInt(8, idtypeactivite);
			preparedStatement.setInt(9, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("updateActivitePro", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean setGratuite(int idactivite, int gratuit) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			String requete = "UPDATE  activite set gratuit=? WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, gratuit);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("setGratuit", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean setTypeActivite(int idactivite, int typeActivite) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			String requete = "UPDATE  activite set idtypeactivite=? WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, typeActivite);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("setTypeActivite", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean setActif(int idactivite, boolean actif) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  activite set actif=? WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setBoolean(1, actif);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("setActif", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean setDateDebut(int idactivite, Date datedebut) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  activite set datedebut=? WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("setDatedebut", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public static boolean setMasque(int idactivite, boolean masque) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		Connection connexion = null;
		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "UPDATE  activite set masque=? WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setBoolean(1, masque);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			LogDAO.LOG_DUREE("setMasque", debut);

			return true;

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}
		return false;

	}

	public int addActivitePro(int idpersonne, String titre, String commentaire,
			Date datedebut, Date datefin, String adresse, double latitude,
			double longitude, int idtypeactivite, int typeuser, int typeaccess) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO activite("
					+ "idpersonne, titre, libelle,datedebut,"
					+ " datefin, adresse, latitude, longitude, actif,"
					+ " idtypeactivite,datecreation,typeuser,typeacces,nbrvu)"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,0)";

			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, titre);
			LOG.info("Mon titre" + titre);
			preparedStatement.setString(3,
					Outils.getStringStatement(commentaire));
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(datedebut.getTime()));
			preparedStatement.setTimestamp(5,
					new java.sql.Timestamp(datefin.getTime()));
			preparedStatement.setString(6, adresse);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setBoolean(9, true);
			preparedStatement.setInt(10, idtypeactivite);
			preparedStatement.setTimestamp(11, new java.sql.Timestamp(
					new Date().getTime()));
			preparedStatement.setInt(12, typeuser);
			preparedStatement.setInt(13, typeaccess);
			preparedStatement.execute();
			connexion.commit();
			rs = preparedStatement.getGeneratedKeys();
			int cle = 0;

			if (rs.next())
				cle = rs.getInt("idactivite");

			LogDAO.LOG_DUREE("addActivitePro", debut);

			return cle;
		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
		return 0;
	}

	public static IndicateurWayd getIndicateurs() {
		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connexion = CxoPool.getConnection();

			int nbrTotalactivite = 0, nbrTotalparticipation = 0, nbrTotalInscrit = 0, nbrTotalMessage = 0, nbrTotalMessageByAct = 0;

			// ************Calcul le nbr total d'activité

			String requete = "Select count(idactivite) as nbractivite  FROM activite";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbrTotalactivite = rs.getInt("nbractivite");
			}

			preparedStatement.close();
			rs.close();
			// ***********Calcul le nbr de participation

			requete = "Select count(idpersonne) as nbrinscrit  FROM personne;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalInscrit = rs.getInt("nbrinscrit");
			}

			preparedStatement.close();
			rs.close();
			// **************Calcul le nbr d'inscrit
			requete = "Select count(idpersonne) as nbrparticipation  FROM participer;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalparticipation = rs.getInt("nbrparticipation");
			}

			preparedStatement.close();
			rs.close();
			// **************Calcul de message non lu en stand alone;

			requete = "select count(idmessage) as nbrmessage from message;";

			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalMessage = rs.getInt("nbrmessage");
			}

			preparedStatement.close();
			rs.close();
			// ***********Calcul de message non lu en stand talkgroup

			requete = "select  count(idmessage) as nbrmessagebyact from messagebyact;";
			preparedStatement = connexion.prepareStatement(requete);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrTotalMessageByAct = rs.getInt("nbrmessagebyact");
			}
			preparedStatement.close();
			rs.close();

			LogDAO.LOG_DUREE("IndicateurWayd", debut);

			return new IndicateurWayd(nbrTotalactivite, nbrTotalparticipation,
					nbrTotalInscrit, nbrTotalMessage, nbrTotalMessageByAct);

		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return null;
		}

		finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}
	}

	// Renvoi une activité avec la liste des participants
	//

	public ActiviteBean getActivite(int idActivite) {

		long debut = System.currentTimeMillis();

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ActiviteBean activite = null;

		String requete = " SELECT activite.lienfacebook,activite.masque,activite.actif,activite.gratuit,activite.datedebut,       activite.adresse,    activite.latitude,"
				+ " activite.longitude,    personne.prenom as pseudo,    personne.sexe,    personne.nom,    personne.idpersonne,"
				+ "personne.affichesexe,personne.afficheage,personne.datenaissance,personne.note,descriptionall,"
				+ "personne.nbravis as totalavis,    personne.photo,activite.idactivite,activite.libelle,"
				+ "activite.titre,activite.nbrwaydeur as nbrparticipant ,activite.nbmaxwayd,activite.nbrvu,   activite.datefin, activite.idtypeactivite,"
				+ "activite.typeuser,activite.typeacces,type_activite.nom as libelleActivite,activite.adresse   FROM personne,"
				+ "activite,type_activite  WHERE activite.idtypeactivite=type_activite.idtypeactivite and personne.idpersonne = activite.idpersonne  and activite.idactivite=?";

		try {
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("pseudo");
				String photo = rs.getString("photo");
				Date datenaissance = rs.getTimestamp("datenaissance");
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
				String adresse = rs.getString("adresse");
				String descriptionall = rs.getString("descriptionall");
				int totalavis = rs.getInt("totalavis");
				int nbrVu = rs.getInt("nbrvu");
				int gratuit = rs.getInt("gratuit");
				String libelleActivite = rs.getString("libelleActivite");
				int nbrSignalement = 0;
				boolean actif = rs.getBoolean("actif");
				String lienFb = rs.getString("lienfacebook");

				boolean masque = rs.getBoolean("masque");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd,
						typeUser, typeAcces, libelleActivite, adresse,
						nbrSignalement, descriptionall, gratuit, actif, masque,
						lienFb);
				activite.setNbrVu(nbrVu);

				ArrayList<ParticipantBean> listParticipant = new ParticipantDAO(
						connexion).getListPaticipant(idActivite);

				activite.setListParticipant(listParticipant);

				LogDAO.LOG_DUREE("getActivite", debut);

				return activite;
			}

		} catch (SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
		return activite;

	}

	public static void effaceTouteCarpeDiem() {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			String requete = "delete from nbrvu where idactivite in (select idactivite from activite where typeuser=4)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "delete from interet where idactivite in (select idactivite from activite where typeuser=4)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "delete from activite where idpersonne in (select idpersonne from personne where typeuser=4)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.execute();
			preparedStatement.close();
			requete = "delete from personne where typeuser=4";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.execute();

			connexion.commit();

		} catch (NamingException | SQLException e) {

			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static ArrayList<ActiviteBean> getListActivite(int idpersonne) {
		long debut = System.currentTimeMillis();

		ActiviteBean activite = null;
		ArrayList<ActiviteBean> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
					+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
					+ "personne.note,0 as role,"
					+ "personne.nbravis as totalavis,"
					+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
					+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
					+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
					+ "activite.idactivite = participer.idactivite "
					+ " and participer.idpersonne=?  ) ORDER BY datedebut DESC";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int totalavis = rs.getInt("totalavis");

				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, archive,
						totalavis, datenaissance, sexe, nbrparticipant, true,
						true, nbmaxwayd);
				retour.add(activite);

			}

			preparedStatement.close();
			rs.close();
			// Cherche dans les activite

			requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
					+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
					+ "personne.note,personne.nbravis as totalavis,"
					+ "activite.nbrwaydeur as nbrparticipant"
					+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd   FROM personne,"
					+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? order by datedebut DESC";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int sexe = rs.getInt("sexe");
				int totalavis = rs.getInt("totalavis");
				int nbmaxwayd = rs.getInt("nbmaxwayd");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, archive,
						totalavis, datenaissance, sexe, nbrparticipant, true,
						true, nbmaxwayd);

				retour.add(activite);
			}

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		LogDAO.LOG_DUREE("getListActivite", debut);
		return retour;

	}

	public static ArrayList<ActiviteBean> getMesActivite(int idpersonne,
			int etatActivite) {
		long debut = System.currentTimeMillis();

		ActiviteBean activite = null;
		ArrayList<ActiviteBean> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			String requete = "";
			switch (etatActivite)

			{
			case TypeEtatActivite.ENCOURS:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and ?  between datedebut and datefin) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));

				rs = preparedStatement.executeQuery();

				break;
			case TypeEtatActivite.TERMINEE:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and datefin<? ) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.TOUTES:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,    personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=?  ) ORDER BY datedebut DESC";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatActivite.PLANIFIEE:
				requete = "SELECT activite.datedebut,activite.adresse,activite.latitude,"
						+ "activite.longitude,personne.prenom,personne.sexe,personne.nom,  personne.datenaissance,personne.idpersonne, "
						+ "personne.note,0 as role,"
						+ "personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,"
						+ "personne.photo,activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite, activite.nbmaxwayd  FROM personne,"
						+ "activite,participer  WHERE (personne.idpersonne=activite.idpersonne and "
						+ "activite.idactivite = participer.idactivite "
						+ " and participer.idpersonne=? and datedebut>? ) ORDER BY datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			}
			if (rs != null)
				while (rs.next()) {
					int id = rs.getInt("idactivite");
					String libelle = rs.getString("libelle");
					String titre = rs.getString("titre");
					int idorganisateur = rs.getInt("idpersonne");
					int idtypeactivite = rs.getInt("idtypeactivite");
					int nbrparticipant = rs.getInt("nbrparticipant");
					Date datedebut = rs.getTimestamp("datedebut");
					Date datefin = rs.getTimestamp("datefin");
					String adresse = rs.getString("adresse");
					double latitude = rs.getDouble("latitude");
					double longitude = rs.getDouble("longitude");
					double note = rs.getDouble("note");
					String nom = rs.getString("nom");
					String prenom = rs.getString("prenom");
					Date datenaissance = rs.getTimestamp("datenaissance");
					String photo = rs.getString("photo");
					boolean archive = true;
					int totalavis = rs.getInt("totalavis");

					int sexe = rs.getInt("sexe");
					int nbmaxwayd = rs.getInt("nbmaxwayd");

					activite = new ActiviteBean(id, titre, libelle,
							idorganisateur, datedebut, datefin, idtypeactivite,
							latitude, longitude, adresse, nom, prenom, photo,
							note, archive, totalavis, datenaissance, sexe,
							nbrparticipant, true, true, nbmaxwayd);

					retour.add(activite);

				}

			preparedStatement.close();
			rs.close();
			// Cherche dans les activite

			switch (etatActivite)

			{
			case TypeEtatActivite.ENCOURS:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis,"
						+ "activite.nbrwaydeur as nbrparticipant, nbrvu,nbr_interet"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ "   FROM personne,activite"
						+ "  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and ? between datedebut and datefin order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;
			case TypeEtatActivite.TERMINEE:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,activite "
						+ " WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and datefin<? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.TOUTES:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant,personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();

				break;

			case TypeEtatActivite.PLANIFIEE:
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,personne.datenaissance,    personne.sexe,    personne.nom,    personne.idpersonne,   "
						+ "personne.note,personne.nbravis as totalavis, nbrvu,nbr_interet,"
						+ "activite.nbrwaydeur as nbrparticipant"
						+ ",personne.photo,activite.idactivite,  activite.libelle,    activite.titre,   activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd"
						+ " FROM personne,activite "
						+ " WHERE personne.idpersonne = activite.idpersonne  and activite.idpersonne=? and datedebut>? order by datedebut DESC";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setTimestamp(2, new java.sql.Timestamp(
						new Date().getTime()));
				rs = preparedStatement.executeQuery();

			}

			while (rs.next()) {
				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				Date datenaissance = rs.getTimestamp("datenaissance");
				String photo = rs.getString("photo");
				boolean archive = true;
				int sexe = rs.getInt("sexe");
				int totalavis = rs.getInt("totalavis");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrvu = rs.getInt("nbrvu");
				int nbr_interet = rs.getInt("nbr_interet");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, 1,
						archive, totalavis, datenaissance, sexe,
						nbrparticipant, true, true, nbmaxwayd, nbrvu,
						nbr_interet);

				retour.add(activite);
			}

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		Collections.sort(retour, new Comparator<ActiviteBean>() {

			@Override
			public int compare(ActiviteBean o1, ActiviteBean o2) {

				return o2.datedebut.compareTo(o1.datedebut);
			}
		});

		LogDAO.LOG_DUREE("getMesActivite", debut);
		return retour;

	}

	public static ArrayList<website.metier.MessageBean> getMesMessages(
			int idpersonne, int etatFiltreMessage) {

		long debut = System.currentTimeMillis();

		website.metier.MessageBean activite = null;
		ArrayList<website.metier.MessageBean> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		Connection connexion = null;
		try {
			connexion = CxoPool.getConnection();
			String requete = "";
			switch (etatFiltreMessage)

			{
			case TypeEtatMessage.LU:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,"
						+ "iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and lu=true and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatMessage.TOUS:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,"
						+ "iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			case TypeEtatMessage.NONLU:

				requete = "SELECT personne.prenom as pseudo,sujet,corps,message.idpersonne,message.datecreation,idmessage,iddestinataire,lu,emis,iddiscussion"
						+ " from message,personne where personne.idpersonne=message.idpersonne and message.iddestinataire=? and lu=false and emis=false";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				rs = preparedStatement.executeQuery();
				break;

			}
			if (rs != null)
				while (rs.next()) {
					int id = rs.getInt("idmessage");
					String message = rs.getString("corps");
					String nomEmetteur = rs.getString("pseudo");
					Date dateCreation = rs.getTimestamp("datecreation");
					boolean emis = rs.getBoolean("emis");
					boolean lu = rs.getBoolean("lu");

					activite = new website.metier.MessageBean(id, nomEmetteur,
							dateCreation, message, lu, emis);

					retour.add(activite);

				}

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

		Collections.sort(retour, new Comparator<website.metier.MessageBean>() {

			@Override
			public int compare(website.metier.MessageBean o1,
					website.metier.MessageBean o2) {

				return o2.getDateCreation().compareTo(o1.getDateCreation());
			}
		});

		LogDAO.LOG_DUREE("getMesMessages", debut);

		return retour;

	}

	private static String getFormatDate(DateTime dt) {

		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		return fmt.print(dt);

	}

	public static ArrayList<ActiviteBean> getListActivite(
			FitreAdminActivites filtre, int page, int maxResult) {
		long debut = System.currentTimeMillis();

		int offset = (maxResult) * page;

		int typeactivite = filtre.getTypeactivite();
		int typeUser_ = filtre.getTypeUser();
		int rayonmetre = filtre.getRayon();
		int typeSignalement = filtre.getTypeSignalement();
		int etatActivite = filtre.getEtatActivite();
		double malatitude = filtre.getLatitude();
		double malongitude = filtre.getLongitude();
		int gratuit = filtre.getGratuit();
		double coef = rayonmetre * 0.007 / 700;
		double latMin = malatitude - coef;
		double latMax = malatitude + coef;
		double longMin = malongitude - coef;
		double longMax = malongitude + coef;
		boolean masqueFiltre = filtre.isMasque();
		boolean actifFiltre = filtre.isActif();

		ActiviteBean activite = null;

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<ActiviteBean> retour = new ArrayList<ActiviteBean>();
		Connection connexion = null;

		try {

			connexion = CxoPool.getConnection();

			String requete = "SELECT 	activite.lienfacebook,personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,"
					+ " personne.note,personne.nbravis as totalavis,personne.photo,activite.idactivite,    activite.titre,    activite.libelle,    activite.adresse,"
					+ "activite.latitude,    activite.longitude,    activite.actif,    activite.idtypeactivite,    activite.datefin,    activite.datedebut,"
					+ "activite.idpersonne,    activite.datecreation,    activite.nbrwaydeur as nbrparticipant,    activite.nbmaxwayd,    activite.d_finactivite,"
					+ "activite.typeacces, activite.masque,   activite.typeuser,activite.nbrvu,    activite.nbrvu, COALESCE(tablesignalement.nbrsignalement, 0::bigint) AS nbrsignalement, "
					+ "type_activite.nom as libelleActivite,descriptionall,gratuit "
					+ " FROM activite"
					+ " LEFT JOIN ( SELECT count(*) AS nbrsignalement, signaler_activite.idactivite "
					+ " FROM signaler_activite  GROUP BY signaler_activite.idactivite) tablesignalement ON activite.idactivite = tablesignalement.idactivite"
					+ " LEFT JOIN type_activite ON type_activite.idtypeactivite = activite.idtypeactivite "
					+ " left join personne on personne.idpersonne = activite.idpersonne "
					+ " WHERE activite.latitude between ? and ? and activite.longitude between ? and ?"
					+ " and activite.masque=? and activite.actif=? and to_char(activite.datedebut,'dd/MM/yyyy')=?";

			// tablesignalement.nbrsignalement = 1
			if (typeactivite != TypeActiviteBean.TOUS) {// on trie sur
														// l'activité

				requete = requete + " and activite.idtypeactivite=? ";

			}

			if (typeUser_ != TypeUser.TOUS) {// on trie sur l'activité

				requete = requete + " and activite.typeuser=? ";

			}

			if (gratuit != TypeGratuitActivite.TOUS) {// on trie sur l'activité

				requete = requete + " and activite.gratuit=? ";

			}

			switch (etatActivite) {

			case TypeEtatActivite.ENCOURS:

				requete = requete
						+ " and current_timestamp between datedebut and datefin ";

				break;

			case TypeEtatActivite.PLANIFIEE:

				requete = requete + " and datedebut>current_timestamp ";

				break;

			case TypeEtatActivite.TERMINEE:

				requete = requete + " and datefin<current_timestamp ";
				break;

			}

			switch (typeSignalement) {

			case TypeSignalement.AUMOINSUNE:
				requete = requete + " and nbrsignalement>0 ";

				break;

			case TypeSignalement.MOINSDE10:
				requete = requete
						+ " and nbrsignalement<10  or nbrsignalement is null ";

				break;
			case TypeSignalement.PLUSDE10:
				requete = requete + " and nbrsignalement>=10 ";
				break;

			case TypeSignalement.TOUS:

				break;
			}

			requete = requete
					+ " order by activite.datedebut desc limit ?  offset ?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setDouble(1, latMin);
			preparedStatement.setDouble(2, latMax);
			preparedStatement.setDouble(3, longMin);
			preparedStatement.setDouble(4, longMax);
			preparedStatement.setBoolean(5, masqueFiltre);
			preparedStatement.setBoolean(6, actifFiltre);
			preparedStatement.setString(7,
					getFormatDate(filtre.getDateRecherche()));

			int index = 8;

			if (typeactivite != TypeActiviteBean.TOUS) {// on trie sur
														// l'activité
				preparedStatement.setInt(index, typeactivite);
				index++;
			}

			if (typeUser_ != TypeUser.TOUS) {// on trie sur l'activité

				preparedStatement.setInt(index, typeUser_);
				index++;

			}

			if (gratuit != TypeGratuitActivite.TOUS) {// on trie sur l'activité

				preparedStatement.setInt(index, gratuit);
				index++;

			}

			preparedStatement.setInt(index, maxResult);
			index++;
			preparedStatement.setInt(index, offset);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double distance = ServeurMethodes.getDistance(malatitude,
						latitude, malongitude, longitude);
				if (distance >= rayonmetre)
					continue;

				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String pseudo = rs.getString("prenom");
				String photo = rs.getString("photo");
				Date datenaissance = rs.getTimestamp("datenaissance");
				int totalavis = rs.getInt("totalavis");
				int typeUser = rs.getInt("typeuser");
				int typeAcces = rs.getInt("typeacces");
				String libelleActivite = rs.getString("libelleActivite");
				String adresse = rs.getString("adresse");
				String descriptionall = rs.getString("descriptionall");
				int nbrSignalement = rs.getInt("nbrsignalement");
				int nbrVu = rs.getInt("nbrvu");
				int gratuite = rs.getInt("gratuit");
				boolean actif = rs.getBoolean("actif");
				boolean masque = rs.getBoolean("masque");
				String lienFb = rs.getString("lienfacebook");

				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, nom, pseudo, photo, note, totalavis,
						datenaissance, sexe, nbrparticipant, nbmaxwayd,
						typeUser, typeAcces, libelleActivite, adresse,
						nbrSignalement, descriptionall, gratuite, actif,
						masque, lienFb);

				activite.setPositionRecherche(filtre.getLatitude(),
						filtre.getLongitude());
				activite.setNbrVu(nbrVu);
				retour.add(activite);

			}

			LogDAO.LOG_DUREE("getListActivite", debut);

			return retour;
		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static boolean terminerActivite(int idActivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			String requete = "UPDATE public.activite  SET  datefin=current_timestamp WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			LogDAO.LOG_DUREE("terminerActivite", debut);
			return true;

		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}

	}

	public static boolean supprimeActivite(int idactivite) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();

			String requete = "DELETE FROM interet where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM nbrvu where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM photo_activite where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM demandeami where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.participer where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);

			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.noter where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "DELETE FROM public.activite where ( idactivite=? );";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			requete = "Delete from messagebyact where idactivite=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();

			preparedStatement.close();

			LogDAO.LOG_DUREE("supprimeActivite", debut);

			return true;
		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			return false;

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}

	}

	public MessageServeur effaceActivite(int idorganisateur, int idactivite) {

		long debut = System.currentTimeMillis();

		try {

			connexion = CxoPool.getConnection();

			wayde.dao.ActiviteDAO activitedao = new wayde.dao.ActiviteDAO(
					connexion);
			ParticipationDAO participationdao = new ParticipationDAO(connexion);
			Activite activite = activitedao.getActivite(idactivite);

			if (activite == null)
				return new MessageServeur(false,
						Erreur_HTML.ACTIVITE_EXISTE_PLUS);

			if (activite.isTerminee())
				return new MessageServeur(false, Erreur_HTML.ACTIVITE_SUPPRIMEE);

			// Recuepre les personnes interesse par cette activit�e
			connexion.setAutoCommit(false);
			final ArrayList<Personne> personneinteresse = activitedao
					.getListPersonneInterresse(activitedao
							.getActivite(idactivite));
			ArrayList<Personne> participants = participationdao
					.getListPartipantActivite(idactivite);

			activitedao.RemoveOnlyActivite(idactivite);
			connexion.commit();

			LogDAO.LOG_DUREE("effaceActivite", debut);
			// ************ Si l'activité est en cours je brodact via
			// GCM*******************
			if (activite.isEnCours())
				PoolThreadGCM.poolThread.execute(new EffaceActiviteGcm(
						personneinteresse, participants, idactivite));
			// ******************************************************************************

			return new MessageServeur(true, TextWebService.suppressionActivite);

		} catch (Exception e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);
			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public void addActiviteWaydeur(int idpersonne, String titre,
			String commentaire, String adresse, double latitude,
			double longitude, int idtypeactivite, int maxwaydeur, int duree,
			int compteWaydeur) {

		long debut = System.currentTimeMillis();
		Date dateDebut = new Date();
		Calendar calFinActivite = Calendar.getInstance();
		calFinActivite.setTime(dateDebut);
		calFinActivite.add(Calendar.MINUTE, duree);
		Date datefinActivite = calFinActivite.getTime();
		PreparedStatement preparedStatement = null;

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO activite("
					+ "idpersonne, titre, libelle,datedebut,"
					+ " datefin, adresse, latitude, longitude, actif,"
					+ " idtypeactivite,datecreation,typeuser,typeacces,nbmaxwayd,nbrvu )"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";

			preparedStatement = connexion.prepareStatement(requete,
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setString(2, titre);
			preparedStatement.setString(3, commentaire);
			preparedStatement.setTimestamp(4,
					new java.sql.Timestamp(dateDebut.getTime()));
			preparedStatement.setTimestamp(5, new java.sql.Timestamp(
					datefinActivite.getTime()));
			preparedStatement.setString(6, adresse);
			preparedStatement.setDouble(7, latitude);
			preparedStatement.setDouble(8, longitude);
			preparedStatement.setBoolean(9, true);
			preparedStatement.setInt(10, idtypeactivite);
			preparedStatement.setTimestamp(11, new java.sql.Timestamp(
					new Date().getTime()));
			preparedStatement.setInt(12, ProfilBean.WAYDEUR);
			preparedStatement.setInt(13, ActiviteBean.GRATUIT);
			preparedStatement.setInt(14, maxwaydeur);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();

			LogDAO.LOG_DUREE("addActiviteWaydeur", debut);

		} catch (NamingException | SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.close(connexion, preparedStatement);
		}

	}

	public ArrayList<ActiviteBean> getListActivites(Double malatitude,
			Double malongitude, int rayonmetre, int idtypeactivite_,
			String motcle, int typeUser, int commenceDans) {

		long debut = System.currentTimeMillis();

		int TOUTES = -1;

		ArrayList<ActiviteBean> retour = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = CxoPool.getConnection();
			double coef = rayonmetre * 0.007 / 700;
			double latMin = malatitude - coef;
			double latMax = malatitude + coef;
			double longMin = malongitude - coef;
			double longMax = malongitude + coef;
			ActiviteBean activite = null;

			Calendar calendrierDebut = Calendar.getInstance();

			if (commenceDans != TOUTES)
				calendrierDebut.add(Calendar.MINUTE, commenceDans * 60);

			Date dateRechercheDebut = calendrierDebut.getTime();

			Calendar calendrierFin = Calendar.getInstance();
			int finiDans = (commenceDans) * 60 + 60;
			calendrierFin.add(Calendar.MINUTE, finiDans);
			// on remonte les activités dont le debut est comprise entre
			// l'heure
			// actuelle + commenceDans et l'heure actuelle + commenceDans+1
			// heure

			String requete;

			if (commenceDans != TOUTES) {

				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and (? between datedebut and  datefin )"
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";
			} else {
				requete = " SELECT activite.datedebut,        activite.adresse,    activite.latitude,"
						+ " activite.longitude,    personne.prenom,    personne.sexe,    personne.nom,    personne.idpersonne,personne.datenaissance,    "
						+ "personne.note,personne.nbravis as totalavis,personne.photo,"
						+ "activite.nbrwaydeur as nbrparticipant,"
						+ "activite.idactivite,    activite.libelle,    activite.titre,    activite.datefin,    activite.idtypeactivite,activite.nbmaxwayd  FROM personne,"
						+ "activite  WHERE personne.idpersonne = activite.idpersonne  "
						+ "and (datefin>? )"
						+ " and activite.latitude between ? and ?"
						+ " and activite.longitude between ? and ?";

			}

			if (idtypeactivite_ != 0) {
				requete = requete + " and activite.idtypeactivite=?";
			}

			if (motcle != null && !motcle.equals("")) {

				requete = requete
						+ " and ( UPPER(libelle) like UPPER(?) or UPPER(titre) like UPPER(?)) ";

			}

			if (typeUser != 0) {

				requete = requete + " and activite.typeuser=?";

			}

			requete = requete + " ORDER BY datedebut asc;";

			preparedStatement = connexion.prepareStatement(requete);

			preparedStatement.setTimestamp(1, new java.sql.Timestamp(
					dateRechercheDebut.getTime()));

			preparedStatement.setDouble(2, latMin);
			preparedStatement.setDouble(3, latMax);
			preparedStatement.setDouble(4, longMin);
			preparedStatement.setDouble(5, longMax);

			int index = 5;

			if (idtypeactivite_ != 0) {
				index++;
				preparedStatement.setInt(index, idtypeactivite_);

			}

			if (motcle != null && !motcle.equals("")) {

				index++;
				String test = "%" + motcle + "%";
				preparedStatement.setString(index, test);
				index++;
				preparedStatement.setString(index, test);

			}

			if (typeUser != 0) {

				index++;
				preparedStatement.setInt(index, typeUser);

			}

			//

			rs = preparedStatement.executeQuery();

			while (rs.next()) {

				double latitude = rs.getDouble("latitude");
				double longitude = rs.getDouble("longitude");
				double distance = ServeurMethodes.getDistance(malatitude,
						latitude, malongitude, longitude);
				if (distance >= rayonmetre)
					continue;

				int id = rs.getInt("idactivite");
				String libelle = rs.getString("libelle");
				String titre = rs.getString("titre");
				int idorganisateur = rs.getInt("idpersonne");
				int idtypeactivite = rs.getInt("idtypeactivite");
				int sexe = rs.getInt("sexe");
				int nbmaxwayd = rs.getInt("nbmaxwayd");
				int nbrparticipant = rs.getInt("nbrparticipant");
				Date datedebut = rs.getTimestamp("datedebut");
				Date datefin = rs.getTimestamp("datefin");
				String adresse = rs.getString("adresse");
				double note = rs.getDouble("note");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");

				if (prenom == null)
					prenom = "";
				String photo = rs.getString("photo");

				Date datenaissance = rs.getTimestamp("datenaissance");
				boolean archive = false;
				int totalavis = rs.getInt("totalavis");
				activite = new ActiviteBean(id, titre, libelle, idorganisateur,
						datedebut, datefin, idtypeactivite, latitude,
						longitude, adresse, nom, prenom, photo, note, archive,
						totalavis, datenaissance, sexe, nbrparticipant, true,
						true, nbmaxwayd);
				retour.add(activite);

			}

			rs.close();
			preparedStatement.close();

			LogDAO.LOG_DUREE("getListActivites", debut);

			return retour;

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));

			return retour;
		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public MessageServeur signalerActivite(int idpersonne, int idactivite,
			int idmotif, String motif, String titre, String libelle) {

		long debut = System.currentTimeMillis();

		try {

			connexion = CxoPool.getConnection();

			SignalementDAO signalementdao = new SignalementDAO(connexion);

			// Verfiie que le signalement est unique
			if (signalementdao.isSignalerActvite(idpersonne, idactivite))
				return new MessageServeur(false,
						TextWebService.activiteDejaSignale);

			connexion.setAutoCommit(false);
			signalementdao.signalerActivite(idpersonne, idactivite, idmotif,
					motif, titre, libelle);
			connexion.commit();

			LogDAO.LOG_DUREE("signalerActivite", debut);

			return new MessageServeur(true, TextWebService.activiteSignale);

		} catch (SQLException | NamingException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			CxoPool.rollBack(connexion);

			return new MessageServeur(false, e.getMessage());

		} finally {
			CxoPool.closeConnection(connexion);
		}

	}

	public void updateChampCalcule(int idactivite) {
		// Met aj our le nbr participant dans activite

		long debut = System.currentTimeMillis();

		String requete = "UPDATE activite SET nbrwaydeur=(select  count(idpersonne)+1 "
				+ " from participer where  idactivite=?) WHERE idactivite=?";

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();

			LogDAO.LOG_DUREE("updateChampCalcule", debut);
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.closePS(preparedStatement);

		}

	}

	public static int getNbrActiviteProposeEnCours(int idpersonne) {

		long debut = System.currentTimeMillis();

		int nbractivite = 0;
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connexion = CxoPool.getConnection();

			String requete = "Select count(idactivite) as nbractivite"
					+ "  FROM activite where ( idpersonne=? and  activite.datefin>?) ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setTimestamp(2,
					new java.sql.Timestamp(new Date().getTime()));
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				nbractivite = rs.getInt("nbractivite");
			}

			LogDAO.LOG_DUREE("getNbrActiviteProposeEnCours", debut);
			return nbractivite;
		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));

			return 0;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);

		}

	}

	public static void ajouteEvenementFaceBook(EvenementFaceBook evenement)
			throws IOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			if (evenement.getListActivite().isEmpty())
				return;

			URLConnection uc;
			URL url = new URL(evenement.getUrlPhoto());
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			BufferedImage imageTailleNormale = ImageIO
					.read(uc.getInputStream());

			String photo = encodeToString(imageTailleNormale, "jpeg");

			BufferedImage photoReduite = resize(imageTailleNormale,
					imageTailleNormale.getWidth() / 4,
					imageTailleNormale.getHeight() / 4);

			int idOrganisteur = website.dao.PersonneDAO.isLoginExist(evenement
					.getIdEvent());
			if (idOrganisteur == 0) {
				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				String requete = "INSERT into personne ( prenom, login,ville,photo,latitude,longitude,latitudefixe,longitudefixe,typeuser,sexe)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?)";

				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, evenement.getNom());
				preparedStatement.setString(2, evenement.getIdEvent());
				preparedStatement.setString(3, evenement.getVille());
				preparedStatement.setString(4, photo);
				preparedStatement.setDouble(5, evenement.getLat());
				preparedStatement.setDouble(6, evenement.getLng());
				preparedStatement.setDouble(7, evenement.getLat());
				preparedStatement.setDouble(8, evenement.getLng());
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				preparedStatement.setInt(10, 1);
				preparedStatement.execute();
				connexion.commit();

			}

			for (ActiviteCarpeDiem activite : evenement.getListActivite()) {

				String idactivitefb = activite.getIdactiviteFB();
				String fulldescription = activite.getFulldescription();
				String lienFb = activite.getLienFaceBook();
				double latitude = activite.getLat();
				double longitude = activite.getLng();
				Date debut = activite.getDateDebut();
				Date fin = activite.getDateFin();
				String libelle = activite.getDescription();
				String titre = activite.getName();
				String adresse = activite.getAddress() + " "
						+ activite.getNomLieu();
				String urlCarpe = activite.getUrlCarpeDiem();

				int idActivite = website.dao.PersonneDAO
						.isIdActiviteFBExist(idactivitefb);

				if (!ImportCarpe.valideActivite(activite))
					if (idActivite != 0) {
						// Si l'activité existe on l'update.
						connexion = CxoPool.getConnection();
						connexion.setAutoCommit(false);
						String requete = "update activite set titre=?,"
								+ " adresse=?,latitude=?,longitude=?,datedebut=?,datefin=?,"
								+ "libelle=?,typeuser=?,actif=?,typeacces=?,idtypeactivite=?,descriptionall=?,lienfacebook=?,liencarpediem=?"
								+ " where idpersonne=?";

						preparedStatement = connexion.prepareStatement(requete);
						preparedStatement.setString(1, titre);
						preparedStatement.setString(2, adresse);
						preparedStatement.setDouble(3, latitude);
						preparedStatement.setDouble(4, longitude);
						preparedStatement.setTimestamp(5,
								new java.sql.Timestamp(debut.getTime()));
						preparedStatement.setTimestamp(6,
								new java.sql.Timestamp(fin.getTime()));

						preparedStatement.setString(7, libelle);
						preparedStatement.setInt(8, ProfilBean.CARPEDIEM);
						boolean active = true;
						int idTypeActivite = TypeActivite.FACEBOOK;
						preparedStatement.setBoolean(9, active);
						preparedStatement.setInt(10, 2);
						preparedStatement.setInt(11, idTypeActivite);
						preparedStatement.setString(12, fulldescription);
						preparedStatement.setString(13, lienFb);
						preparedStatement.setString(14, urlCarpe);
						preparedStatement.setInt(15, idOrganisteur);
						preparedStatement.execute();
						connexion.commit();
						LOG.info("Activite mis à jour:");
						return;
					} else {

						connexion = CxoPool.getConnection();
						connexion.setAutoCommit(false);
						String requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
								+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,lienfacebook,liencarpediem)"
								+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

						preparedStatement = connexion.prepareStatement(requete);
						preparedStatement.setString(1, titre);
						preparedStatement.setString(2, adresse);
						preparedStatement.setDouble(3, latitude);
						preparedStatement.setDouble(4, longitude);
						preparedStatement.setTimestamp(5,
								new java.sql.Timestamp(debut.getTime()));
						preparedStatement.setTimestamp(6,
								new java.sql.Timestamp(fin.getTime()));
						preparedStatement.setInt(7, idOrganisteur);
						preparedStatement.setString(8, libelle);
						preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
						boolean active = true;
						int idTypeActivite = TypeActivite.FACEBOOK;
						preparedStatement.setBoolean(10, active);
						preparedStatement.setInt(11, 2);
						preparedStatement.setInt(12, idTypeActivite);
						preparedStatement.setString(13, fulldescription);
						preparedStatement.setString(14, lienFb);
						preparedStatement.setString(15, urlCarpe);
						preparedStatement.setString(16, idactivitefb);
						preparedStatement.execute();
						LOG.info("Activite ajoutée");
						connexion.commit();

					}

			}
		} catch (NamingException | SQLException | ParseException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {

				LOG.error(ExceptionUtils.getStackTrace(e1));
			}

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

	public static void ajouteActiviteFaceBook(ActiviteCarpeDiem activite)
			throws IOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			// ****************** Recuperation valeur***********************

			String prenom = activite.getName();
			String login = activite.getIdEventFaceBook();
			String idactivitefb = activite.getIdactiviteFB();
			String photoUrlFaceBook = activite.getUrlPhotoFB();
			String ville = activite.getVille();
			String fulldescription = activite.getFulldescription();

			String lienFb = activite.getLienFaceBook();
			double latitude = activite.getLat();
			double longitude = activite.getLng();
			double latitudeFixe = activite.getLat();
			double longitudeFixe = activite.getLng();
			Date debut = activite.getDateDebut();
			Date fin = activite.getDateFin();
			String libelle = activite.getDescription();
			String titre = activite.getName();
			String adresse = activite.getAddress() + " "
					+ activite.getNomLieu();
			String urlCarpe = activite.getUrlCarpeDiem();

			if (!ImportCarpe.valideActivite(activite.getDateDebut(),
					activite.getDateFin())) {
				LOG.info("Activite ignorée");
				return;
			}

			URLConnection uc;
			URL url = new URL(photoUrlFaceBook);
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			BufferedImage imageTailleNormale = ImageIO
					.read(uc.getInputStream());

			String photo = encodeToString(imageTailleNormale, "jpeg");

			BufferedImage buff_photoReduite = resize(imageTailleNormale, 100,
					100);

			String photoReduite = encodeToString(buff_photoReduite, "jpeg");

			int idOrganisteur = website.dao.PersonneDAO.isLoginExist(activite
					.getIdEventFaceBook());

			if (idOrganisteur != 0) {

				int idActivite = website.dao.PersonneDAO
						.isIdActiviteFBExist(idactivitefb);

				if (idActivite != 0) {
					// Si l'activité existe on l'update.
					connexion = CxoPool.getConnection();
					connexion.setAutoCommit(false);
					String requete = "update activite set titre=?,"
							+ " adresse=?,latitude=?,longitude=?,datedebut=?,datefin=?,"
							+ "libelle=?,typeuser=?,actif=?,typeacces=?,idtypeactivite=?,descriptionall=?,lienfacebook=?,liencarpediem=?"
							+ " where idactivite=?";

					preparedStatement = connexion.prepareStatement(requete);
					preparedStatement.setString(1, titre);
					preparedStatement.setString(2, adresse);
					preparedStatement.setDouble(3, latitude);
					preparedStatement.setDouble(4, longitude);
					preparedStatement.setTimestamp(5, new java.sql.Timestamp(
							debut.getTime()));
					preparedStatement.setTimestamp(6, new java.sql.Timestamp(
							fin.getTime()));

					preparedStatement.setString(7, libelle);
					preparedStatement.setInt(8, ProfilBean.CARPEDIEM);
					boolean active = true;
					int idTypeActivite = TypeActivite.FACEBOOK;
					preparedStatement.setBoolean(9, active);
					preparedStatement.setInt(10, 2);
					preparedStatement.setInt(11, idTypeActivite);
					preparedStatement.setString(12, fulldescription);
					preparedStatement.setString(13, lienFb);
					preparedStatement.setString(14, urlCarpe);
					preparedStatement.setInt(15, idActivite);
					preparedStatement.execute();
					connexion.commit();
					LOG.info("Activite mis à jour:");
					return;
				} else {

					connexion = CxoPool.getConnection();
					connexion.setAutoCommit(false);
					String requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
							+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,lienfacebook,liencarpediem,idactivitefacebook)"
							+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

					preparedStatement = connexion.prepareStatement(requete);
					preparedStatement.setString(1, titre);
					preparedStatement.setString(2, adresse);
					preparedStatement.setDouble(3, latitude);
					preparedStatement.setDouble(4, longitude);
					preparedStatement.setTimestamp(5, new java.sql.Timestamp(
							debut.getTime()));
					preparedStatement.setTimestamp(6, new java.sql.Timestamp(
							fin.getTime()));
					preparedStatement.setInt(7, idOrganisteur);
					preparedStatement.setString(8, libelle);
					preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
					boolean active = true;
					int idTypeActivite = TypeActivite.FACEBOOK;
					preparedStatement.setBoolean(10, active);
					preparedStatement.setInt(11, 2);
					preparedStatement.setInt(12, idTypeActivite);
					preparedStatement.setString(13, fulldescription);
					preparedStatement.setString(14, lienFb);
					preparedStatement.setString(15, urlCarpe);
					preparedStatement.setString(16, idactivitefb);

					preparedStatement.execute();
					LOG.info("Activite ajoutée");
					connexion.commit();

				}

			} else {

				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				String requete = "INSERT into personne ( prenom, login,ville,photo,latitude,longitude,latitudefixe,longitudefixe,typeuser,sexe,photosmall)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, prenom);
				preparedStatement.setString(2, login);
				preparedStatement.setString(3, ville);
				preparedStatement.setString(4, photo);
				preparedStatement.setDouble(5, latitude);
				preparedStatement.setDouble(6, longitude);
				preparedStatement.setDouble(7, latitudeFixe);
				preparedStatement.setDouble(8, longitudeFixe);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				preparedStatement.setInt(10, 1);
				preparedStatement.setString(11, photoReduite);
				preparedStatement.execute();

				ResultSet rs = preparedStatement.getGeneratedKeys();
				int idpersonne = 0;

				if (rs.next())
					idpersonne = rs.getInt("idpersonne");

				preparedStatement.close();

				requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
						+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,lienfacebook,liencarpediem,idactivitefacebook)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setString(1, titre);
				preparedStatement.setString(2, adresse);
				preparedStatement.setDouble(3, latitude);
				preparedStatement.setDouble(4, longitude);
				preparedStatement.setTimestamp(5,
						new java.sql.Timestamp(debut.getTime()));
				preparedStatement.setTimestamp(6,
						new java.sql.Timestamp(fin.getTime()));
				preparedStatement.setInt(7, idpersonne);
				preparedStatement.setString(8, libelle);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				boolean active = true;
				int idTypeActivite = TypeActivite.FACEBOOK;
				preparedStatement.setBoolean(10, active);
				preparedStatement.setInt(11, 2);
				preparedStatement.setInt(12, idTypeActivite);
				preparedStatement.setString(13, fulldescription);
				preparedStatement.setString(14, lienFb);
				preparedStatement.setString(15, urlCarpe);
				preparedStatement.setString(16, idactivitefb);
				preparedStatement.execute();
				LOG.info("Activite ajoutée");
				connexion.commit();
			}

		} catch (NamingException | SQLException | ParseException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {

				LOG.error(ExceptionUtils.getStackTrace(e1));
			}

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

	public static void ajouteActiviteFaceBookOld(ActiviteCarpeDiem activite)
			throws IOException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			// ****************** Recuperation valeur***********************

			String prenom = activite.getName();
			String login = activite.getIdactiviteFB();
			String photoUrl = activite.getUrlPhotoFB();
			String ville = activite.getVille();
			String fulldescription = activite.getFulldescription();

			String lienFb = activite.getLienFaceBook();
			double latitude = activite.getLat();
			double longitude = activite.getLng();
			double latitudeFixe = activite.getLat();
			double longitudeFixe = activite.getLng();
			Date debut = activite.getDateDebut();
			Date fin = activite.getDateFin();
			String libelle = activite.getDescription();
			String titre = activite.getName();
			String adresse = activite.getAddress() + " "
					+ activite.getNomLieu();
			String urlCarpe = activite.getUrlCarpeDiem();

			if (!ImportCarpe.valideActivite(activite.getDateDebut(),
					activite.getDateFin())) {
				LOG.info("Activite ignorée");
				return;
			}

			URLConnection uc;
			URL url = new URL(photoUrl);
			uc = url.openConnection();
			uc.addRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			uc.connect();
			BufferedImage imBuff = ImageIO.read(uc.getInputStream());
			String photo = encodeToString(imBuff, "jpeg");

			// String photo=null;
			// L'activite est complete est peut donc être inserée
			// Ajoute le lien dans la table carpe pour eviter la redondance.

			// addLienCarpeDiem(activite.getUrl());

			// Si elle existe mise à jour

			int idOrganisteur = website.dao.PersonneDAO.isLoginExist(activite
					.getIdactiviteFB());

			if (idOrganisteur != 0) {

				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				String requete = "update activite set titre=?,"
						+ " adresse=?,latitude=?,longitude=?,datedebut=?,datefin=?,"
						+ "libelle=?,typeuser=?,actif=?,typeacces=?,idtypeactivite=?,descriptionall=?,lienfacebook=?,liencarpediem=?"
						+ " where idpersonne=?";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setString(1, titre);
				preparedStatement.setString(2, adresse);
				preparedStatement.setDouble(3, latitude);
				preparedStatement.setDouble(4, longitude);
				preparedStatement.setTimestamp(5,
						new java.sql.Timestamp(debut.getTime()));
				preparedStatement.setTimestamp(6,
						new java.sql.Timestamp(fin.getTime()));

				preparedStatement.setString(7, libelle);
				preparedStatement.setInt(8, ProfilBean.CARPEDIEM);
				boolean active = true;
				int idTypeActivite = TypeActivite.FACEBOOK;
				preparedStatement.setBoolean(9, active);
				preparedStatement.setInt(10, 2);
				preparedStatement.setInt(11, idTypeActivite);
				preparedStatement.setString(12, fulldescription);
				preparedStatement.setString(13, lienFb);
				preparedStatement.setString(14, urlCarpe);
				preparedStatement.setInt(15, idOrganisteur);
				preparedStatement.execute();
				connexion.commit();
				LOG.info("Activite mis à jour:");
				return;

			} else {

				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);

				String requete = "INSERT into personne ( prenom, login,ville,photo,latitude,longitude,latitudefixe,longitudefixe,typeuser,sexe)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);

				preparedStatement.setString(1, prenom);
				preparedStatement.setString(2, login);
				preparedStatement.setString(3, ville);
				preparedStatement.setString(4, photo);
				preparedStatement.setDouble(5, latitude);
				preparedStatement.setDouble(6, longitude);
				preparedStatement.setDouble(7, latitudeFixe);
				preparedStatement.setDouble(8, longitudeFixe);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				preparedStatement.setInt(10, 1);

				preparedStatement.execute();

				ResultSet rs = preparedStatement.getGeneratedKeys();
				int idpersonne = 0;

				if (rs.next())
					idpersonne = rs.getInt("idpersonne");

				preparedStatement.close();

				requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
						+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,lienfacebook,liencarpediem)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setString(1, titre);
				preparedStatement.setString(2, adresse);
				preparedStatement.setDouble(3, latitude);
				preparedStatement.setDouble(4, longitude);
				preparedStatement.setTimestamp(5,
						new java.sql.Timestamp(debut.getTime()));
				preparedStatement.setTimestamp(6,
						new java.sql.Timestamp(fin.getTime()));
				preparedStatement.setInt(7, idpersonne);
				preparedStatement.setString(8, libelle);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				boolean active = true;
				int idTypeActivite = TypeActivite.FACEBOOK;
				preparedStatement.setBoolean(10, active);
				preparedStatement.setInt(11, 2);
				preparedStatement.setInt(12, idTypeActivite);
				preparedStatement.setString(13, fulldescription);
				preparedStatement.setString(14, lienFb);
				preparedStatement.setString(15, urlCarpe);

				preparedStatement.execute();
				LOG.info("Activite ajoutée");
				connexion.commit();
			}

		} catch (NamingException | SQLException | ParseException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {

				LOG.error(ExceptionUtils.getStackTrace(e1));
			}

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

	public static String encodeToString(BufferedImage image, String type) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, type, bos);
			byte[] imageBytes = bos.toByteArray();
			BASE64Encoder encoder = new BASE64Encoder();
			imageString = encoder.encode(imageBytes);
			bos.close();
		} catch (IOException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		}
		return imageString;
	}

	public static MessageServeur addInteretActivite(int idpersonne,
			int idactivite, int niveauInteret) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		if (isInteretDejaSignale(idpersonne, idactivite))
			return new MessageServeur(false, Erreur_HTML.INTERET_DEJA_SIGNALEE);

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO interet("
					+ "   idpersonne, idactivite,niveau_interet)"
					+ "	VALUES (?,?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.setInt(3, niveauInteret);
			preparedStatement.execute();
			preparedStatement.close();
			requete = "UPDATE activite  SET  nbr_interet=nbr_interet+1 WHERE idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idactivite);
			preparedStatement.execute();
			connexion.commit();

			LogDAO.LOG_DUREE("addInteretActivite", debut);

			return new MessageServeur(true, Erreur_HTML.INTERET_SIGNALEE);

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

		return new MessageServeur(false, Erreur_HTML.ERREUR_INCONNUE);

	}

	public static MessageServeur addFavori(int idpersonne, int idactivite) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		if (isFavoriDejaSignale(idpersonne, idactivite))
			return new MessageServeur(false, Erreur_HTML.INTERET_DEJA_SIGNALEE);

		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO favori("
					+ "   idpersonne, idactivite)" + "	VALUES (?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idactivite);
			preparedStatement.execute();
			preparedStatement.close();

			connexion.commit();

			LogDAO.LOG_DUREE("addFavoriActivite", debut);

			return new MessageServeur(true, Erreur_HTML.FAVORI_AJOUTE);

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

		return new MessageServeur(false, Erreur_HTML.ERREUR_INCONNUE);

	}

	public static void addLienCarpeDiem(String url) {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		if (isDejaTraiteCarpediemExist(url))
			return;

		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "INSERT INTO carpediem(lien)" + "	VALUES (?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, url);
			preparedStatement.execute();
			connexion.commit();

			LogDAO.LOG_DUREE("addUrlCarpeDien", debut);

		} catch (NamingException | SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

	public static void effaceTouteCarpeDiem(Date date) {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connexion = null;
		ArrayList<Integer> listIdActivte = new ArrayList<>();
		ArrayList<Integer> listPersonne = new ArrayList<>();

		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);

			String requete = " SELECT idactivite,idpersonne from activite where datefin<? and typeuser=4";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(date.getTime()));
			rs = preparedStatement.executeQuery();
			LOG.info("Recherche  les activités CARPEDIEM à effacer:");
			while (rs.next()) {

				int idActivite = rs.getInt("idactivite");
				int idPersonne = rs.getInt("idpersonne");
				listIdActivte.add(idActivite);
				listPersonne.add(idPersonne);
				LOG.info("Activite à effacer: id= " + idActivite + "-Personne:"
						+ idPersonne);
			}

			preparedStatement.close();

			for (Integer idActivite : listIdActivte) {

				requete = "delete from nbrvu where idactivite=?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idActivite);
				preparedStatement.execute();
				preparedStatement.close();

				requete = "delete from interet where idactivite=?";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idActivite);
				preparedStatement.execute();
				preparedStatement.close();

				requete = "delete from activite where idactivite=? and typeuser=4";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idActivite);
				preparedStatement.execute();
				preparedStatement.close();
				rs.close();

			}

			for (Integer idpersonne : listPersonne) {
				requete = "delete from personne where idpersonne=? and typeuser=4";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.execute();
				preparedStatement.close();

			}
			connexion.commit();

		} catch (NamingException | SQLException e) {

			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {
				LOG.error(ExceptionUtils.getStackTrace(e));
			}
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static void updateDateCarpeDiem(Date date) {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection connexion = null;
		ArrayList<Integer> listIdActivte = new ArrayList<>();
		ArrayList<Integer> listPersonne = new ArrayList<>();

		try {

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = " SELECT idactivite,datedebut from activite where"
					+ " to_date(to_char(datedebut,'dd/mm/yyyy'),'dd/mm/yyyy')<? "
					+ " and typeuser=4";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setTimestamp(1,
					new java.sql.Timestamp(date.getTime()));
			rs = preparedStatement.executeQuery();
			LOG.info("Recherche  les activités CARPEDIEM à effacer:");

			while (rs.next()) {

				Date maintenant = new Date();
				Calendar calNow = Calendar.getInstance();
				calNow.setTime(maintenant);
				int idActivite = rs.getInt("idactivite");
				Date datedebut = rs.getTimestamp("datedebut");
				Calendar cal = Calendar.getInstance();

				cal.setTime(datedebut);
				cal.set(Calendar.DAY_OF_MONTH,
						calNow.get(Calendar.DAY_OF_MONTH));
				cal.set(Calendar.MONTH, calNow.get(Calendar.MONTH));
				cal.set(Calendar.YEAR, calNow.get(Calendar.YEAR));

				setDateDebut(idActivite, cal.getTime());

				LOG.info("Activite à changer la date: id= " + idActivite);
			}

			preparedStatement.close();

		} catch (NamingException | SQLException e) {

			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {
				LOG.error(ExceptionUtils.getStackTrace(e1));
			}
			LOG.error(ExceptionUtils.getStackTrace(e));

		} finally {
			CxoPool.close(connexion, preparedStatement, rs);
		}

	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH)
			throws IOException {
		return Thumbnails.of(img).forceSize(newW, newH).outputQuality(1)
				.asBufferedImage();

	}

	public static void ajouteEvenementOpenAgenda(
			EvenementOpenAGenda evenementOpenAGenda) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			// ****************** Recuperation valeur***********************

			String prenom = evenementOpenAGenda.getTitre();
			String login = evenementOpenAGenda.getUidEvent();
			String photoUrl = evenementOpenAGenda.getImage();
			String ville = evenementOpenAGenda.getVille();
			String fulldescription = evenementOpenAGenda.getFreetext();

			String lien = evenementOpenAGenda.getLienurl();
			double latitude = evenementOpenAGenda.getLatitude();
			double longitude = evenementOpenAGenda.getLongitude();
			double latitudeFixe = evenementOpenAGenda.getLatitude();
			double longitudeFixe = evenementOpenAGenda.getLongitude();
			String libelle = evenementOpenAGenda.getDescription();
			String titre = evenementOpenAGenda.getTitre();

			String adresse = evenementOpenAGenda.getAdresseTotal()
					+ evenementOpenAGenda.getNomLieu();

			BufferedImage imageNormal = Outils.getImageFromURL(photoUrl);
			String photo = "";
			BufferedImage buffphotoReduite;
			String photoReduite = "";
			System.out.println("mon image" + photoUrl);

			if (imageNormal == null) {

				LOG.error("image non disponible - Activite refusée");

			} else {

				photo = encodeToString(imageNormal, "jpeg");
				buffphotoReduite = resize(imageNormal, 100, 100);
				photoReduite = encodeToString(buffphotoReduite, "jpeg");

			}
			LOG.info("Verifie personne");

			int idpersonneCree = website.dao.PersonneDAO
					.isLoginExist(evenementOpenAGenda.getUidEvent());

			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			if (idpersonneCree == 0) {
				LOG.info("Cree personne");

				String requete = "INSERT into personne ( prenom, login,ville,photo,latitude,longitude,latitudefixe,longitudefixe,typeuser,sexe,photosmall,actif,verrouille)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, prenom);
				preparedStatement.setString(2, login);
				preparedStatement.setString(3, ville);
				preparedStatement.setString(4, photo);
				preparedStatement.setDouble(5, latitude);
				preparedStatement.setDouble(6, longitude);
				preparedStatement.setDouble(7, latitudeFixe);
				preparedStatement.setDouble(8, longitudeFixe);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				preparedStatement.setInt(10, 1);
				preparedStatement.setString(11, photoReduite);
				preparedStatement.setBoolean(12, true);
				preparedStatement.setBoolean(13, false);
				preparedStatement.execute();

				ResultSet rs = preparedStatement.getGeneratedKeys();

				if (rs.next())
					idpersonneCree = rs.getInt("idpersonne");

				preparedStatement.close();
				connexion.commit();

			}

			// Recupere toutes les ID de la base

			preparedStatement = connexion
					.prepareStatement("select datedebut,datefin,idactivite from activite where idactiviteopen=?");
			preparedStatement.setString(1, evenementOpenAGenda.getUidEvent());

			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Date dateDebut = rs.getTimestamp("datedebut");
				Date dateFin = rs.getTimestamp("datefin");
				int idactivite = rs.getInt("idactivite");
				if (!evenementOpenAGenda.isExistEvenement(dateDebut, dateFin)) {
					ActiviteDAO.supprimeActivite(idactivite);
				}

				evenementOpenAGenda.supprimeEvtExisant(dateDebut, dateFin);
			}

			preparedStatement.close();
			LOG.info("**NBE EVENEMTN :**"
					+ evenementOpenAGenda.getListActivite().size());

			for (DateEvenementOpenAgenda dateEvenementOpenAGenda : evenementOpenAGenda
					.getListActivite()) {

				if (ActiviteDAO.isDejaExits(latitude, longitude,
						dateEvenementOpenAGenda.getDateDebut(),
						dateEvenementOpenAGenda.getDateFin()))
					continue;
				String requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
						+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,idactiviteopen)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setString(1, titre);
				preparedStatement.setString(2, adresse);
				preparedStatement.setDouble(3, latitude);
				preparedStatement.setDouble(4, longitude);
				preparedStatement.setTimestamp(5, new java.sql.Timestamp(
						dateEvenementOpenAGenda.getDateDebut().getTime()));
				preparedStatement.setTimestamp(6, new java.sql.Timestamp(
						dateEvenementOpenAGenda.getDateFin().getTime()));
				preparedStatement.setInt(7, idpersonneCree);
				preparedStatement.setString(8, libelle);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				boolean active = true;
				// int idTypeActivite = TypeActivite.OPEN_AGENDA;
				int idTypeActivite = 1;
				preparedStatement.setBoolean(10, active);
				preparedStatement.setInt(11, 2);
				preparedStatement.setInt(12, idTypeActivite);
				preparedStatement.setString(13, fulldescription);
				preparedStatement.setString(14,
						evenementOpenAGenda.getUidEvent());
				preparedStatement.execute();
				LOG.info("Activite ajoutée");
				connexion.commit();
				preparedStatement.close();

			}
			connexion.close();
			preparedStatement.close();

		} catch (NamingException | SQLException | IOException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {

				LOG.error(ExceptionUtils.getStackTrace(e1));
			}

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

	public static void ajouteEvenementMapado(
			EvenementOpenAGenda evenementOpenAGenda, Date debut, Date fin) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {

			// ****************** Recuperation valeur***********************

			String prenom = evenementOpenAGenda.getTitre();
			String login = evenementOpenAGenda.getUidEvent();
			String photoUrl = evenementOpenAGenda.getImage();
			String ville = evenementOpenAGenda.getVille();
			String fulldescription = evenementOpenAGenda.getFreetext();

			String lien = evenementOpenAGenda.getLienurl();
			double latitude = evenementOpenAGenda.getLatitude();
			double longitude = evenementOpenAGenda.getLongitude();
			double latitudeFixe = evenementOpenAGenda.getLatitude();
			double longitudeFixe = evenementOpenAGenda.getLongitude();
			String libelle = evenementOpenAGenda.getDescription();
			String titre = evenementOpenAGenda.getTitre();

			String adresse = evenementOpenAGenda.getAdresseTotal()
					+ evenementOpenAGenda.getNomLieu();

			
			BufferedImage imageNormal = Outils.getImageMapodoFromURL(photoUrl);
		//	BufferedImage imageNormal =Thumbnails.of(photoUrl).asBufferedImage();
			String photo = "";
			BufferedImage buffphotoReduite;
			String photoReduite = "";

			if (imageNormal == null) {

				LOG.error("image non disponible - Activite refusée");
				return;
			} else {

				photo = encodeToString(imageNormal, "jpeg");
				buffphotoReduite = resize(imageNormal, 100, 100);
				photoReduite = encodeToString(buffphotoReduite, "jpeg");

			}
			LOG.info("Verifie personne");

			int idpersonneCree = website.dao.PersonneDAO
					.isLoginExist(evenementOpenAGenda.getUidEvent());

			connexion = CxoPool.getConnection();
			if (idpersonneCree == 0) {

				connexion.setAutoCommit(false);
				LOG.info("Cree personne");

				String requete = "INSERT into personne ( prenom, login,ville,photo,latitude,longitude,latitudefixe,longitudefixe,typeuser,sexe,photosmall,actif,verrouille)"
						+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, prenom);
				preparedStatement.setString(2, login);
				preparedStatement.setString(3, ville);
				preparedStatement.setString(4, photo);
				preparedStatement.setDouble(5, latitude);
				preparedStatement.setDouble(6, longitude);
				preparedStatement.setDouble(7, latitudeFixe);
				preparedStatement.setDouble(8, longitudeFixe);
				preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
				preparedStatement.setInt(10, 1);
				preparedStatement.setString(11, photoReduite);
				preparedStatement.setBoolean(12, true);
				preparedStatement.setBoolean(13, false);
				preparedStatement.execute();

				ResultSet rs = preparedStatement.getGeneratedKeys();

				if (rs.next())
					idpersonneCree = rs.getInt("idpersonne");

				preparedStatement.close();
			//	connexion.commit();

			}

			if (ActiviteDAO.isDejaExits(latitude, longitude, debut, fin))
				return;

			String requete = "INSERT into activite ( titre, adresse,latitude,longitude,datedebut,datefin,"
					+ "idpersonne,libelle,typeuser,actif,typeacces,idtypeactivite,descriptionall,idactiviteopen)"
					+ "	VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, titre);
			preparedStatement.setString(2, adresse);
			preparedStatement.setDouble(3, latitude);
			preparedStatement.setDouble(4, longitude);
			preparedStatement.setTimestamp(5,
					new java.sql.Timestamp(debut.getTime()));
			preparedStatement.setTimestamp(6,
					new java.sql.Timestamp(fin.getTime()));
			preparedStatement.setInt(7, idpersonneCree);
			preparedStatement.setString(8, libelle);
			preparedStatement.setInt(9, ProfilBean.CARPEDIEM);
			boolean active = true;
			int idTypeActivite = 1;
			preparedStatement.setBoolean(10, active);
			preparedStatement.setInt(11, 2);
			preparedStatement.setInt(12, idTypeActivite);
			preparedStatement.setString(13, fulldescription);
			preparedStatement.setString(14, evenementOpenAGenda.getUidEvent());
			preparedStatement.execute();
			LOG.info("Activite ajoutée");
			connexion.commit();
		
		} catch (NamingException | SQLException | IOException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			try {
				if (connexion != null)
					connexion.rollback();
			} catch (SQLException e1) {

				LOG.error(ExceptionUtils.getStackTrace(e1));
			}

		} finally {

			CxoPool.close(connexion, preparedStatement);

		}

	}

}
