package servlet;

import gcmnotification.AcquitAllNotificationGcm;
import gcmnotification.UpdatePreferenceGcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import threadpool.PoolThreadGCM;

/**
 * Servlet implementation class Cron
 */
public class Cron extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Cron.class);
	private Thread cronTache;
	private TacheFond tacheFond=new TacheFond();
   /**
     * @see HttpServlet#HttpServlet()
     */
    public Cron() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public void destroy() {
	tacheFond.setStop(true);
	LOG.info(PoolThreadGCM.poolThread.getActiveCount());
	PoolThreadGCM.poolThread.purge();
	PoolThreadGCM.poolThread.shutdown();
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		
		super.init();
		// Initialistion de l'application 
	//	MDC.put("duree", 3);
		LOG.info("Redemerrage application - Lancement du cron ");
		cronTache=new Thread(tacheFond);
		cronTache.start();
		
	}

}
