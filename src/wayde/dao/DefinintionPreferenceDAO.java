package wayde.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.beandatabase.DefinitionPreferenceDb;


public class DefinintionPreferenceDAO {
	private static final Logger LOG = Logger.getLogger(DefinintionPreferenceDAO.class);

	
	Connection connexion;
	public DefinintionPreferenceDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
	this.connexion=connexion;
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
			
			CxoPool.close(preparedStatement, rs);
	
			return defPrefdb;
	}

}
