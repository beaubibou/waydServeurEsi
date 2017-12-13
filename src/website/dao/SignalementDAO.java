package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;
import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;
import wayde.dao.ActiviteDAO;
import website.metier.SignalementActiviteBean;
import website.metier.SignalementProfilBean;
import website.metier.SignalementCount;

public class SignalementDAO {
	private static final Logger LOG = Logger.getLogger(SignalementDAO.class);

	public static ArrayList<SignalementProfilBean> getListSignalement(
			int idpersonne) {


		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SignalementProfilBean> retour = new ArrayList<SignalementProfilBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT signaler_profil.d_creation,signaler_profil.idsignalement as idpersonnesignalee,signaler_profil.idpersonne as idinformateur,signaler_profil.idmotif,signaler_profil.motif,"
					+ "		signaler_profil.idpersonne, personne.prenom as pseudoInfo, personne1.prenom as pseudoSignale, ref_signalementprofil.libelle "
					+ " from personne,personne as personne1 ,signaler_profil,ref_signalementprofil "
					+ "where personne.idpersonne=signaler_profil.idpersonne and   personne1.idpersonne=signaler_profil.idsignalement and "
					+ "ref_signalementprofil.id=signaler_profil.idmotif and signaler_profil.idsignalement=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idpersonnesignalee = rs.getInt("idpersonnesignalee");
				int idinformateur = rs.getInt("idinformateur");
				String pseudoSignale = rs.getString("pseudoSignale");
				String pseudoInfo = rs.getString("pseudoInfo");
				Date d_creation = rs.getTimestamp("d_creation");
				int idmotif = rs.getInt("idmotif");
				String motif = rs.getString("motif");
				String libelle = rs.getString("libelle");

				retour.add(new SignalementProfilBean(idpersonnesignalee,
						idinformateur, pseudoSignale, pseudoInfo, d_creation,
						idmotif, motif, libelle));
			}
			LogDAO.LOG_DUREE("getListSignalement", debut);
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static ArrayList<SignalementActiviteBean> getListSignalementActivite(
			int idActivite) {


		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SignalementActiviteBean> retour = new ArrayList<SignalementActiviteBean>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT idactivite,"
					+ " idmotif,signaler_activite.d_creation,"
					+ "motif,"
					+ "titre,"
					+ "signaler_activite.libelle,"
					+ "personne.prenom as pseudoInfo,"
					+ "signaler_activite.idpersonne as idinformateur, "
					+ "ref_signalementactivite.libelle as libelleMotif"
					+ "  FROM signaler_activite,personne,ref_signalementactivite"
					+ " where"
					+ " signaler_activite.idmotif=ref_signalementactivite.id"
					+ " and signaler_activite.idpersonne=personne.idpersonne"
					+ " and signaler_activite.idactivite=?";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idActivite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idpersonnesignalee = rs.getInt("idactivite");
				int idinformateur = rs.getInt("idinformateur");
			
				String pseudoInfo = rs.getString("pseudoInfo");
				Date d_creation = rs.getTimestamp("d_creation");
				int idmotif = rs.getInt("idmotif");
				String motif = rs.getString("motif");
				String libelle = rs.getString("libelleMotif");

				retour.add(new SignalementActiviteBean(idpersonnesignalee,
						idinformateur,  pseudoInfo, d_creation,
						idmotif, motif, libelle));
			}
			LogDAO.LOG_DUREE("getListSignalementActivite", debut);
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static ArrayList<SignalementCount> getCountSignalementBy() {

		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SignalementCount> retour = new ArrayList<SignalementCount>();

		try {
			connexion = CxoPool.getConnection();

			String requete = "SELECT count(signaler_profil.idsignalement) as nbr,signaler_profil.idsignalement, personne.prenom as pseudo"
					+ " FROM public.signaler_profil,personne  where  personne.idpersonne=signaler_profil.idsignalement"
					+ " group by idsignalement,personne.prenom";
			preparedStatement = connexion.prepareStatement(requete);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idpersonnesignalee = rs.getInt("idsignalement");
				int nbr = rs.getInt("nbr");
				String pseudo = rs.getString("pseudo");
				retour.add(new SignalementCount(idpersonnesignalee, nbr, pseudo));
			}
			LogDAO.LOG_DUREE("getCountSignalementBy", debut);
			return retour;

		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			return retour;
		} finally {

			CxoPool.close(connexion, preparedStatement, rs);
		}
	}

	public static MessageServeur addSignalement(int idpersonne, int idsignalee,
			int idmotif, String motif) {


		long debut = System.currentTimeMillis();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();

			if (new wayde.dao.SignalementDAO(connexion).isSignalerProfil(
					idpersonne, idsignalee))
				return new MessageServeur(false,
						Erreur_HTML.PROFIL_DEJA_SIGNALE);

			String requete = "INSERT INTO signaler_profil(idpersonne,idsignalement,idmotif,motif,d_creation)  VALUES (?, ?, ?,?,?);";
			connexion.setAutoCommit(false);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idsignalee);
			// if (motif.equals(""))motif=null;
			preparedStatement.setInt(3, idmotif);
			preparedStatement.setString(4, motif);
			preparedStatement.setTimestamp(5,
					new java.sql.Timestamp(new Date().getTime()));
			preparedStatement.execute();
			connexion.commit();
			LogDAO.LOG_DUREE("addSignalement", debut);
			return new MessageServeur(true, Erreur_HTML.ACTIVITE_SIGNALEE);

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error( ExceptionUtils.getStackTrace(e));
			
			return new MessageServeur(false, e.getMessage());

		} finally {

			CxoPool.close(connexion, preparedStatement);
		}

	}

}
