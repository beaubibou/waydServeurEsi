package website.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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
			String requete = "DELETE FROM message where  idmessage=? );";
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
		param.delete(param.length()-1, param.length());
		param.insert(param.length(),")");
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
}
