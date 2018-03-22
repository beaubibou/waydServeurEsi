package wayde.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import wayde.bean.CxoPool;
import wayde.beandatabase.TypeActiviteDb;

public class TypeActiviteDAO {
	private static final Logger LOG = Logger.getLogger(TypeActiviteDAO.class);

	Connection connexion;

	public TypeActiviteDAO(Connection connexion) {
		this.connexion = connexion;
	}

	public ArrayList<TypeActiviteDb> getListTypeActivite() throws SQLException {

		TypeActiviteDb typeactivitedb;
		ArrayList<TypeActiviteDb> retour = new ArrayList<>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connexion.createStatement();
			rs = stmt
					.executeQuery("SELECT * FROM type_activite order by ordre asc;");
			while (rs.next()) {
				long id = rs.getLong("idtypeactivite");
				long idcategorie = rs.getLong("idcategorieactivite");
				int typeusuer = rs.getInt("typeuser");
				String nom = rs.getString("nom");
				typeactivitedb = new TypeActiviteDb(id, idcategorie, nom,
						typeusuer);
				retour.add(typeactivitedb);

			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {

			LOG.error(ExceptionUtils.getStackTrace(e));
			throw e;
		} finally {
			CxoPool.close(stmt, rs);
		}

		return retour;

	}
}
