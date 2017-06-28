package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.NamingException;

import wayde.bean.Ami;
import wayde.bean.CxoPool;
import website.metier.ActiviteBean;
import website.metier.AmiBean;
import website.metier.SignalementBean;
import website.metier.SignalementCount;

public class SignalementDAO {

	public static ArrayList<SignalementBean> getListSignalement(int idpersonne) {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SignalementBean> retour = new ArrayList<SignalementBean>();

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
				int idpersonnesignalee  = rs.getInt("idpersonnesignalee");
				int idinformateur  = rs.getInt("idinformateur");
				String pseudoSignale = rs.getString("pseudoSignale");
				String pseudoInfo = rs.getString("pseudoInfo");
				Date d_creation=rs.getTimestamp("d_creation");
				int idmotif  = rs.getInt("idmotif");
				String motif = rs.getString("motif");
				String libelle = rs.getString("libelle");
						
				retour.add(new SignalementBean(idpersonnesignalee, idinformateur, pseudoSignale, pseudoInfo, d_creation, idmotif, motif,libelle));
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

	public static ArrayList<SignalementCount> getCountSignalementBy() {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		ArrayList<SignalementCount> retour = new ArrayList<SignalementCount>();

		try {
			connexion = CxoPool.getConnection();

			String requete = 
						"SELECT count(signaler_profil.idsignalement) as nbr,signaler_profil.idsignalement, personne.prenom as pseudo"
					+ " FROM public.signaler_profil,personne  where  personne.idpersonne=signaler_profil.idsignalement"
					+ " group by idsignalement,personne.prenom";
			preparedStatement = connexion.prepareStatement(requete);
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				int idpersonnesignalee  = rs.getInt("idsignalement");
				int nbr  = rs.getInt("nbr");
				String pseudo=rs.getString("pseudo");
				retour.add(new SignalementCount(idpersonnesignalee, nbr,pseudo));
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
