package wayde.bean;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class CxoPool {

	public static Connection getConnection() throws NamingException, SQLException {
			// Récupération connection référencées dans le JNDI - cf context.xml 
			Context ctx = new InitialContext();
			// Connexion avec Oracle
			DataSource source = (DataSource) ctx.lookup("java:/comp/env/PostgresDS");
			
			// Connexion avec Postgre
			//DataSource source = (DataSource) ctx.lookup("java:/comp/env/PostgreDS");
			return source.getConnection();
	}

	public static void close(Connection connection, Statement statement, ResultSet resultSet) {
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	public static void closeConnection(Connection connexion) {
		
		if (connexion != null)
			try {
				connexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
}