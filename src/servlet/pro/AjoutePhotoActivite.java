package servlet.pro;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.dao.ActiviteDAO;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.ProfilBean;

/**
 * Servlet implementation class AjoutePhotoActivite
 */
public class AjoutePhotoActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(AjoutePhotoActivite.class);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutePhotoActivite() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifiePro())
			return;
		
		ProfilBean profil=authentification.getProfil();
		
		
		
		int idactivite = Integer.parseInt(request.getParameter("idActivite"));

		if (ActiviteDAO.getNbrPhoto(idactivite)>=3)
		{
			response.sendRedirect("ModifierActivite?idactivite="+idactivite);
			return;
		}
			
		File file;
		int maxFileSize = 6000 * 1024;
		int maxMemSize = 6000 * 1024;
		// String filePath = "c:/apache-tomcat/webapps/data/";

		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
		//	factory.setRepository(new File("c:\\temp"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			try {
				List fileItems = upload.parseRequest(request);
				Iterator i = fileItems.iterator();

				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
				//		String fieldName = fi.getFieldName();
					//	String fileName = fi.getName();
				//		boolean isInMemory = fi.isInMemory();
					//	long sizeInBytes = fi.getSize();
						BufferedImage tmp = ImageIO
								.read(fi.getInputStream());

						BufferedImage imBuff=resize(tmp,800,600);
						
						String stringPhoto = Outils.encodeToString(imBuff,
								"jpeg");
						ActiviteDAO.addPhotoActivite(stringPhoto, idactivite);
						response.sendRedirect("ModifierActivite?idactivite="+idactivite);

					}
				}

			} catch (Exception ex) {
				LOG.error(ExceptionUtils.getStackTrace(ex));
				request.setAttribute("message",ex.getMessage());	
				request.getRequestDispatcher("pro/messageInfo.jsp").forward(request, response);
			
			}
		} else {

		}
	
	}

	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		  return Thumbnails.of(img).forceSize(newW, newH).outputQuality(1).asBufferedImage();
		}

}
