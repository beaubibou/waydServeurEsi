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
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import wayd.ws.WBservices;
import website.dao.PersonneDAO;
import website.metier.AuthentificationSite;
import website.metier.Outils;
import website.metier.ProfilBean;

/**
 * Servlet implementation class ChargePhotoPro
 */
public class ChargePhotoPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ChargePhotoPro.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChargePhotoPro() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		AuthentificationSite authentification = new AuthentificationSite(
				request, response);

		if (!authentification.isAuthentifiePro())
			return;
		
		ProfilBean profil=authentification.getProfil();

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
						BufferedImage tmp = ImageIO
								.read(fi.getInputStream());

						BufferedImage imBuff=resize(tmp,100,100);
						
						String stringPhoto = Outils.encodeToString(imBuff,
								"jpeg");
						new PersonneDAO().updatePhoto(stringPhoto, profil.getId());
						profil.setPhotostr(stringPhoto);
						response.sendRedirect("ComptePro");

					}
				}

			} catch (Exception ex) {
				System.out.println(ex);
			}
		} else {

		}

	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) throws IOException {
		  return Thumbnails.of(img).forceSize(newW, newH).asBufferedImage();
		}
	
	}

