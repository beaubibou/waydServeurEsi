package servlet;

import website.dao.LogDAO;

public class TacheFond implements Runnable {

	public boolean stop = false;

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
