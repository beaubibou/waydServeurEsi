package wayde.bean;

import org.apache.log4j.Logger;

public class PhotoWaydeur {
	private static final Logger LOG = Logger.getLogger(PhotoWaydeur.class);

	private int id;
	private String photo;
	public PhotoWaydeur() {
		super();
		
	}
	public PhotoWaydeur(int id, String photo) {
		super();
		this.id = id;
		this.photo = photo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	

}
