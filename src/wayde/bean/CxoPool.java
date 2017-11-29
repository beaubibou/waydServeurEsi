package wayde.bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class CxoPool {

	private static final Logger LOG = Logger.getLogger(CxoPool.class);

	public static Connection getConnection() throws NamingException,
			SQLException {
		// Récupération connection référencées dans le JNDI - cf
		// context.xml
		
		Context ctx = new InitialContext();
		try{
			
			DataSource source = (DataSource) ctx.lookup("java:comp/env/PostgresDS");
			return source.getConnection();
		}
		catch( NamingException e){
			
		}
	
		
//		try {
//			Class.forName("org.postgresql.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		String url = "jdbc:postgresql://localhost:5432/wayd";
//		String user = "postgres";
//		String passwd = "azerty";
//		Connection conn = DriverManager.getConnection(url, user, passwd);
//		
		//return conn;
		return null;
		
		
	}

	public static Connection getConnection(boolean test) throws NamingException,
			SQLException {
		
		
				if (!test){
				Context ctx = new InitialContext();
				DataSource source = (DataSource) ctx.lookup("java:comp/env/PostgresDS");
				return source.getConnection();
				
			}
				else{
				
				try {
					Class.forName("org.postgresql.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				String url = "jdbc:postgresql://localhost:5432/wayd";
				String user = "postgres";
				String passwd = "azerty";
				Connection conn = DriverManager.getConnection(url, user, passwd);
			
				return conn;
				
			}
	
	}



	public static void close(Connection connection, Statement statement,
			ResultSet resultSet) {
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