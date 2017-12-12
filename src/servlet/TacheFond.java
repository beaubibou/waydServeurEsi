package servlet;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import wayde.bean.CxoPool;
import wayde.dao.AvisDAO;

public class TacheFond implements Runnable {

	public boolean stop = false;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {

				Thread.sleep(2000);
				if (stop)
					return;

				doTacheFond();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	private void doTacheFond() {
	
	try {
		Connection connexion=CxoPool.getConnection()	;
	AvisDAO avis=new AvisDAO(connexion);
	System.out.println(avis.isDoubleAvis(679273,  679274,449621));
	
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		// TODO Auto-generated method stub

	}

}
