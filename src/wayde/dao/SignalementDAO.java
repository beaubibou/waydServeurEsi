package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;

public class SignalementDAO {
	private static final Logger LOG = Logger.getLogger(SignalementDAO.class);
	Connection connexion;
	public static int SUSPECTE = 0;
	public static int DANGEREUSE = 1;
	public static int ILLICITE = 2;
	public static int GRATUITE_PAYANTE = 3;
	public static int AUTRES = 4;

	public SignalementDAO(Connection connexion) {
		this.connexion = connexion;

	}

	public boolean isSignalerProfil(int idpersonne, int idsignalement)
			throws SQLException {

		boolean retour = false;
		String requete = "SELECT idpersonne  FROM signaler_profil where idpersonne=? and idsignalement=?;";
		PreparedStatement preparedStatement;
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idsignalement);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			retour = true;
		}
		CxoPool.close(preparedStatement, rs);
		return retour;
		
	}

	public boolean isSignalerActvite(int idpersonne, int idactivite)
			throws SQLException {
		boolean retour=false;
		String requete = "SELECT idpersonne  FROM signaler_activite where idpersonne=? and idactivite=?;";
		PreparedStatement preparedStatement;
		preparedStatement = connexion.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idactivite);
		ResultSet rs = preparedStatement.executeQuery();

		if (rs.next()) {
			retour = true;
		} 
		CxoPool.close(preparedStatement, rs);
		return retour;

		
	}

	public void signalerProfil(int idpersonne, int idsignalement, int idmotif,
			String motif) throws SQLException {

		String requete = "INSERT INTO signaler_profil(idpersonne,idsignalement,idmotif,motif,d_creation)  VALUES (?, ?, ?,?,?);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idsignalement);
		if (motif.equals(""))
			motif = null;
		preparedStatement.setInt(3, idmotif);
		preparedStatement.setString(4, motif);
		preparedStatement.setTimestamp(5,
				new java.sql.Timestamp(new Date().getTime()));
		preparedStatement.execute();
		preparedStatement.close();

	}

	public void signalerActivite(int idpersonne, int idactivite, int idmotif,
			String motif, String titre, String libelle) throws SQLException {

		String requete = "INSERT INTO signaler_activite(idpersonne,idactivite,idmotif,motif,titre,libelle)  VALUES (?, ?, ?,?,?,?);";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idactivite);
		preparedStatement.setInt(3, idmotif);
		preparedStatement.setString(4, motif);
		preparedStatement.setString(5, titre);
		preparedStatement.setString(6, libelle);
		preparedStatement.execute();
		preparedStatement.close();

	}

}
