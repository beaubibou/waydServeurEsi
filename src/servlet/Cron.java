package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import threadpool.PoolThreadGCM;
import wayd.ws.WBservices;
import website.dao.CacheValueDAO;
import website.dao.LogDAO;
import website.enumeration.TypePhoto;
import website.metier.Outils;

import com.google.firebase.FirebaseApp;

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
		
	}

	@Override
	public void init() throws ServletException {
		
		
		super.init();
		// Initialistion de l'application 
	//	MDC.put("duree", 3);
		
		LOG.info("Demarrage serveur");
	
		if (FirebaseApp.getApps().isEmpty())
			FirebaseApp.initializeApp(WBservices.optionFireBase);
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(getServletContext().getRealPath("/")
					+ "img/inconnu.jpg"));
			String photoStr = Outils.encodeToString(img, "jpg");
			CacheValueDAO.addPhotoCache(TypePhoto.Inconnu, photoStr);
		} catch (IOException e) {
			
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
		LogDAO.prepareStatPerf();
//		
		LOG.info("Redemerrage application - Lancement du cron ");
		cronTache=new Thread(tacheFond);
		cronTache.setName("THREAD-CRON");
		cronTache.start();  
		
		
		
		
	}

}
