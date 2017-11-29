package wayde.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import wayde.beandatabase.TypeActiviteDb;

public class TypeActiviteDAO {
	private static final Logger LOG = Logger.getLogger(TypeActiviteDAO.class);

	Connection connexion ;
	public TypeActiviteDAO(Connection connexion) {
		// TODO Auto-generated constructor stub
	this.connexion=connexion;
	}

	public  ArrayList<TypeActiviteDb> getListTypeActivite() throws SQLException {
		TypeActiviteDb typeactivitedb = null;
		ArrayList<TypeActiviteDb> retour=new ArrayList<TypeActiviteDb>();
		  
			Statement stmt = connexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM type_activite order by ordre asc;"
					 );
			while (rs.next()) {
				long id = rs.getLong("idtypeactivite");
				long idcategorie = rs.getLong("idcategorieactivite");
				int typeusuer=rs.getInt("typeuser");
				String nom = rs.getString("nom");
				typeactivitedb = new TypeActiviteDb( id,  idcategorie,  nom,typeusuer);
				retour.add(typeactivitedb);
				
			}
			rs.close();
			stmt.close();
		
			return retour;

		
		
		
	}
}
