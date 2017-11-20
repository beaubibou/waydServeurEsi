package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import wayde.bean.MessageServeur;

public class MessageDAO {

	public static MessageServeur effaceMessage(int idMessage) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM message where  idmessage=? ;";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new MessageServeur(false,
				"Erreur dans la métode supprime activité");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public static MessageServeur effaceMessages(List<Integer> idMessage) {

		long debut = System.currentTimeMillis();

		Connection connexion = null;
		StringBuilder param = new StringBuilder();
		for (Integer idmessage : idMessage) {

			param.append(idmessage + ",");

		}

		param.insert(0, "(");
		param.delete(param.length() - 1, param.length());
		param.insert(param.length(), ")");
		System.out.println("chaine=" + param);
		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "DELETE FROM message where idmessage in ?);";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setString(1, param.toString());
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new MessageServeur(false,
				"Erreur dans la métode supprime activité");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

	}

	public static MessageServeur lireMessage(int idMessage) {
		long debut = System.currentTimeMillis();

		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		try {
			connexion = CxoPool.getConnection();
			connexion.setAutoCommit(false);
			String requete = "update message set lu=true   where  idmessage=? ";
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idMessage);
			preparedStatement.execute();
			preparedStatement.close();
			connexion.commit();
			return new MessageServeur(true, "ok");

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return new MessageServeur(false,
				"Erreur dans la métode lit message pro");

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub// TODO Auto-generated method stub

	}

	public static String getNbrMessageNonLu(int idPersonne){
		
		Connection connexion = null;

		PreparedStatement preparedStatement = null;
		ResultSet rs=null;
		int nbrmessagenonlu=0;
		try {
			connexion = CxoPool.getConnection();
		
			String requete = "select  count(idmessage) as nbrmessagenonlu from message"
					+ " where (  iddestinataire=? and lu=false and emis=false);";

			preparedStatement = connexion.prepareStatement(requete);
			preparedStatement.setInt(1, idPersonne);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				nbrmessagenonlu = rs.getInt("nbrmessagenonlu");
			}
			
			return new Integer(nbrmessagenonlu).toString();

		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block

			try {
				if (connexion != null)
					connexion.rollback();
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs!=null)rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		
			}
			e.printStackTrace();
		} finally {

			try {
				if (connexion != null)
					connexion.close();
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (SQLException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
return new Integer(nbrmessagenonlu).toString();

		// TODO Auto-generated method stub
		// TODO Auto-generated method stub// TODO Auto-generated method stub
	
		
		
	
		
		
		
	}

	public static void ajouteMessageETclore(String message, int idPersonne,
			int idEmetteur) {
		// TODO Auto-generated method stub
		
	}

	public static boolean ajouteMessage(String message, int idDestinataire, int idEmetteur) {
		// TODO Auto-generated method stub
		String iddiscussion;
	
		if (idEmetteur < idDestinataire)
			iddiscussion = "" + idEmetteur + "-" + idDestinataire;
		else
			iddiscussion = "" + idDestinataire + "-" + idEmetteur;

		
			Connection connexion = null;
			PreparedStatement preparedStatement = null;

			try {
				connexion = CxoPool.getConnection();
				connexion.setAutoCommit(false);
				Date datecreation = Calendar.getInstance().getTime();
				
				String requete = "INSERT INTO message( corps, idpersonne, datecreation,idactivite,iddestinataire,iddiscussion,lu,emis)  "
						+ "VALUES (?, ?, ?, ?,?,?,true,true);";
				 preparedStatement = connexion.prepareStatement(
						requete, Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, message);
				preparedStatement.setInt(2, idEmetteur);
				preparedStatement.setTimestamp(3,
						new java.sql.Timestamp(datecreation.getTime()));
				preparedStatement.setInt(4,0);
				preparedStatement.setInt(5, idDestinataire);
				preparedStatement.setString(6,iddiscussion);
				preparedStatement.execute();
			
				preparedStatement.close();
				
				requete = "INSERT INTO message( corps, idpersonne, datecreation,idactivite,iddestinataire,iddiscussion,lu,emis)  VALUES (?, ?, ?, ?,?,?,false,false);";
				
				preparedStatement = connexion.prepareStatement(requete,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, message);
				preparedStatement.setInt(2, idEmetteur);
				preparedStatement.setTimestamp(3,
						new java.sql.Timestamp(datecreation.getTime()));
				preparedStatement.setInt(4, 0);
				preparedStatement.setInt(5, idDestinataire);
				preparedStatement.setString(6, iddiscussion);
				preparedStatement.execute();
				
				preparedStatement.close();
				
				
				
				
				
				
				
				
				
				
				
				connexion.commit();

				return true;

			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {

					if (connexion != null)
						connexion.close();
					if (preparedStatement != null)
						preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return false;

	}
	
		
	
}
