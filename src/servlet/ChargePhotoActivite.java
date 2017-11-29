package servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import website.dao.CacheValueDAO;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.TypeActiviteBean;

/**
 * Servlet implementation class ChargePhotoActivite
 */
public class ChargePhotoActivite extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ChargePhotoActivite.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChargePhotoActivite() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		
		if (!authentification.isAuthentifieAdmin())
			return;
	
		LOG.info("doGet");
		ArrayList<TypeActiviteBean> listPhotoTypeActivite=CacheValueDAO.getListTypeActiviteBeanFull();
		
		request.setAttribute("listPhotoTypeActivite", listPhotoTypeActivite);
		request.getRequestDispatcher("admin/chargePhotoActivite.jsp").forward(
				request, response);
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		AuthentificationSite authentification = new AuthentificationSite(
				request, response);
		if (!authentification.isAuthentifieAdmin())
			return;
	
	
		int id = Integer.parseInt(request
				.getParameter("idTypeActivite"));
		String libelle=request.getParameter("libelle");
		File file;
		int maxFileSize = 5000 * 1024;
		int maxMemSize = 5000 * 1024;
		// String filePath = "c:/apache-tomcat/webapps/data/";

		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") >= 0)) {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(maxMemSize);
			factory.setRepository(new File("c:\\temp"));
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(maxFileSize);
			try {
				List fileItems = upload.parseRequest(request);
				Iterator i = fileItems.iterator();

				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();
					if (!fi.isFormField()) {
						String fieldName = fi.getFieldName();
						String fileName = fi.getName();
						boolean isInMemory = fi.isInMemory();
						long sizeInBytes = fi.getSize();
					
						BufferedImage imBuff = ImageIO
								.read(fi.getInputStream());

						String stringPhoto = Outils.encodeToString(imBuff,
								"jpeg");
							
						CacheValueDAO.updateCachePhoto(id, stringPhoto, libelle);
						CacheValueDAO.updatePhotoTypeActivite(id, stringPhoto);
						response.sendRedirect("ChargePhotoActivite");

					}
				}

			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {

		}

	}

}
