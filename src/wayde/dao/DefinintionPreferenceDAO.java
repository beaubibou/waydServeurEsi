package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import org.apache.log4j.Logger;

import wayde.beandatabase.DefinitionPreferenceDb;


public class DefinintionPreferenceDAO {
	private static final Logger LOG = Logger.getLogger(DefinintionPreferenceDAO.class);

	
	Connection connexion;
	public DefinintionPreferenceDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
	this.connexion=connexion;
	}

	public  ArrayList<DefinitionPreferenceDb> getListDefinitionPref(
			int idpersonne, int idtypeactivite) throws SQLException {
			DefinitionPreferenceDb defPrefdb = null;
		ArrayList<DefinitionPreferenceDb> retour = new ArrayList<DefinitionPreferenceDb>();

	
			String requete = " SELECT  prefere.always,prefere.active,prefere.idpersonne,"
					+ "prefere.idtypeactivite,jour,heuredebut,heurefin "
					+ "from prefere,plage "
					+ "where prefere.idpersonne=? "
					+ "and prefere.idtypeactivite=? and"
					+ " prefere.idpersonne=plage.idpersonne and"
					+ " prefere.idtypeactivite=plage.idtypeactivite;";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idtypeactivite);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next()) {
				int jour=rs.getInt("jour");
				int heuredebut=rs.getInt("heuredebut");
				int heurefin=rs.getInt("heurefin");
				int typeactivite=rs.getInt("idtypeactivite");
				boolean active=rs.getBoolean("active");
				boolean always=rs.getBoolean("always");
				defPrefdb = new DefinitionPreferenceDb(  jour,  heuredebut,
						 heurefin,  typeactivite,  idpersonne,active,always );

				retour.add(defPrefdb);

			}
			rs.close();
			preparedStatement.close();
		
			return retour;

	}

	public  DefinitionPreferenceDb getDefPref(int idpersonne,
			int idtypeactivite) throws SQLException {
		
		DefinitionPreferenceDb defPrefdb = null;
			String requete = " SELECT  prefere.always,prefere.active,prefere.idpersonne,"
					+ "prefere.idtypeactivite "
					+ "from prefere "
					+ "where prefere.idpersonne=? "
					+ "and prefere.idtypeactivite=?";

			PreparedStatement preparedStatement = connexion
					.prepareStatement(requete);

			preparedStatement.setInt(1, idpersonne);
			preparedStatement.setInt(2, idtypeactivite);
			ResultSet rs = preparedStatement.executeQuery();
			
			
			while (rs.next()) {
				
				boolean active=rs.getBoolean("active");
				boolean always=rs.getBoolean("always");
				defPrefdb = new DefinitionPreferenceDb( idpersonne,active,always );

	
			}
			rs.close();
			preparedStatement.close();
	
			return defPrefdb;
	}

}
