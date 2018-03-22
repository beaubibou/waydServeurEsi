package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.bean.Preference;

public class PreferenceDAO {
	private static final Logger LOG = Logger.getLogger(PreferenceDAO.class);

	Connection connexion;

	public PreferenceDAO(Connection connexion) {

		this.connexion = connexion;
	}

	public ArrayList<Preference> getLisPreference(int idpersonne)
			throws SQLException {
		Preference preferencedb;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Preference> retour = new ArrayList<>();
		try {
			stmt = connexion.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM prefere where active=true and idpersonne="
							+ idpersonne + ";");
			while (rs.next()) {
				int idtypeactivite = rs.getInt("idtypeactivite");
				preferencedb = new Preference(idpersonne, idtypeactivite);
				retour.add(preferencedb);
			}

			stmt.close();
			rs.close();
		} catch (SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {

			CxoPool.close(stmt, rs);
		}
		return retour;

	}

	public ArrayList<Preference> getLisPreferences(int idpersonne)
			throws SQLException {
		Preference preference ;
		ArrayList<Preference> retour = new ArrayList<>();

		String requete = "SELECT p.idtypeactivite,p.active,t.nom,t.ordre FROM prefere p ,type_activite t where p.idpersonne=?"
				+ " and p.idtypeactivite=t.idtypeactivite union"
				+ "	(select idtypeactivite,false,nom,ordre from type_activite where idtypeactivite"
				+ "	not in( SELECT idtypeactivite FROM prefere where idpersonne=?)) order by ordre";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.setInt(2, idpersonne);
		ResultSet rs = preparedStatement.executeQuery();

		while (rs.next()) {
			int idtypeactivite = rs.getInt("idtypeactivite");
			boolean active = rs.getBoolean("active");
			String nom = rs.getString("nom");
			preference = new Preference(idpersonne, idtypeactivite, active, nom);
			retour.add(preference);
		}

		CxoPool.close(preparedStatement, rs);
		return retour;

	}

	public void addPreference(int idpersonne, int idtypeactivite[],
			Boolean[] active) throws SQLException {

		String requete = "DELETE FROM prefere WHERE prefere.idpersonne=? ";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

		for (int f = 0; f < idtypeactivite.length; f++)

		{
			if (active[f]) {
				requete = "INSERT INTO prefere  (idpersonne,idtypeactivite,always,active)	VALUES (?,?,?,?)";
				preparedStatement = connexion.prepareStatement(requete);
				preparedStatement.setInt(1, idpersonne);
				preparedStatement.setInt(2, idtypeactivite[f]);
				preparedStatement.setBoolean(3, true);
				preparedStatement.setBoolean(4, active[f]);
				preparedStatement.execute();
				preparedStatement.close();

			}
		}

	}

	public void addPreferences(int idpersonne, int listpreferences[])
			throws SQLException {

		String requete = "DELETE FROM prefere WHERE idpersonne=? ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

		if (listpreferences[0] == 0)
			return;
		for (int f = 0; f < listpreferences.length; f++)

		{
			requete = "INSERT INTO prefere  (idpersonne,idtypeactivite)	VALUES (?,?)";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, listpreferences[f]);
			preparedStatement.execute();
			preparedStatement.close();

		}

	}

}
