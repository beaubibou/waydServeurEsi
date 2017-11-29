package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import wayde.bean.Preference;

public class PreferenceDAO {
	private static final Logger LOG = Logger.getLogger(PreferenceDAO.class);

	Connection connexion;

	public PreferenceDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
		this.connexion = connexion;
	}

	public ArrayList<Preference> getLisPreference(int idpersonne)
			throws SQLException {
		Preference preferencedb = null;
		ArrayList<Preference> retour = new ArrayList<Preference>();
		Statement stmt = connexion.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM prefere where active=true and idpersonne="
						+ idpersonne + ";");
		while (rs.next()) {
			int idtypeactivite = rs.getInt("idtypeactivite");
			preferencedb = new Preference(idpersonne, idtypeactivite);
			retour.add(preferencedb);
		}
		rs.close();
		stmt.close();
		return retour;

	}

	public ArrayList<Preference> getLisPreferences(int idpersonne)
			throws SQLException {
		Preference preference = null;
		ArrayList<Preference> retour = new ArrayList<Preference>();

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

		rs.close();
		preparedStatement.close();
		return retour;

	}

	public void addPreference(int idpersonne, int idtypeactivite[],
			Boolean[] active) throws SQLException {
		// TODO Auto-generated method stub
		//System.out.println("Ajoute met a jour la pr�f�rence DAO");

		String requete = "DELETE FROM prefere WHERE prefere.idpersonne=? ";

		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

		for (int f = 0; f < idtypeactivite.length; f++)

		{
			if (active[f] == true) {
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
		// TODO Auto-generated method stub
		//System.out.println("Ajoute preference DAO" + listpreferences.length);

		String requete = "DELETE FROM prefere WHERE idpersonne=? ";
		PreparedStatement preparedStatement = connexion
				.prepareStatement(requete);
		preparedStatement.setInt(1, idpersonne);
		preparedStatement.execute();
		preparedStatement.close();

		// GERE LES CAS OU IL N Y A AUCUNCE PREFERENCE
		//System.out.println("Ajoute preference DAO1");
		if (listpreferences[0] == 0)
			return;
		//System.out.println("Ajoute preference DAO2");
		for (int f = 0; f < listpreferences.length; f++)

		{
		//	System.out.println("ajoute preference" + idpersonne);

			requete = "INSERT INTO prefere  (idpersonne,idtypeactivite)	VALUES (?,?)";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, listpreferences[f]);
			preparedStatement.execute();
			preparedStatement.close();

		}

	}

}
