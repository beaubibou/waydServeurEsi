package servlet;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.LogDAO;

public class TacheFond implements Runnable {

	public boolean stop = false;
	private static final Logger LOG = Logger.getLogger(TacheFond.class);
	private Date debut=new Date();
	private Date debutCarpediem=new Date();

	@Override
	public void run() {
		
		while (true) {
			try {
		
				Thread.sleep(LogDAO.TPS_ECHATILLONNAGE*1000);
		
				if (stop)
					return;
			
				if ((System.currentTimeMillis()-debut.getTime())/1000>LogDAO.TPS_CALCUL_PERFOMENCE){
				debut=new Date();
				LogDAO.prepareStatPerf();
				}
				
				
				doTacheFond();

			} catch (InterruptedException e) {
			
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
		
	LogDAO.supprimeNderniersLogd();
	
	}

}
