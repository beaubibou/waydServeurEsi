package wayde.bean;

public class ProfilDiscussion {

	private int id;
	private String photo;
	public ProfilDiscussion() {
		super();
		
	}
	public ProfilDiscussion(int id, String photo) {
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
