package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.Ami;
import wayde.bean.CxoPool;

public class AmiDAO {

	private static final Logger LOG = Logger.getLogger(AmiDAO.class);

	Connection connexion;

	public AmiDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ArrayList<Ami> getListAmi(int idpersonne) throws SQLException {
		Ami ami;
		ArrayList<Ami> retour = new ArrayList<>();

		String requete = " SELECT   ami.d_creation,personne.login,personne.idpersonne,personne.prenom, personne.nom,personne.photo,personne.note "
				+ "  FROM personne,ami  WHERE personne.idpersonne = ami.idami and ami.idpersonne=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			int id = rs.getInt("idpersonne");
			String nom = rs.getString("nom");
			String login = rs.getString("login");
			String prenom = rs.getString("prenom");
			if (prenom == null)
				prenom = "";
			String photostr = rs.getString("photo");
			Date depuisle = rs.getDate("d_creation");
			double note = rs.getDouble("note");
			ami = new Ami(photostr, prenom, nom, login, id, depuisle, note);
			retour.add(ami);
		}

		CxoPool.close(preparedStatement, rs);

		return retour;

	}

	public void effaceAmi(int idpersonne, int idami) {

		String requete = " delete   from ami where idpersonne=? and idami=?;"
				+ " delete   from ami where idpersonne=? and idami=?; ";
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idami);
			preparedStatement.setInt(3, idami);
			preparedStatement.setInt(4, idpersonne);
			preparedStatement.execute();
		} catch (SQLException e) {
			LOG.error(ExceptionUtils.getStackTrace(e));
		} finally {
			CxoPool.closePS(preparedStatement);
		}
	}

	public boolean isAmiFrom(int idpersonne, int idami) throws SQLException {
		// Renvoi si id ami appartient aux ami de idpersonne
		
		 boolean retour = false;
		String requete = " SELECT idami from ami  where idpersonne=? and idami=?  ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);

		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idami);
		ResultSet rs = preparedStatement.executeQuery();
		
		if (rs.next()) {

			retour = true;
		}
		
		CxoPool.close(preparedStatement, rs);
		return retour;

	}

}
