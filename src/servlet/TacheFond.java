package servlet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.LogDAO;

public class TacheFond implements Runnable {

	public boolean stop = false;
	private static final Logger LOG = Logger.getLogger(TacheFond.class);


	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			try {

				Thread.sleep(20000);
				if (stop)
					return;
			
				doTacheFond();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error( ExceptionUtils.getStackTrace(e));
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
		
	System.out.println("Nbr log"+LogDAO.getNbrLogs());
	LogDAO.supprimeNderniersLogd();
	
	}

}
