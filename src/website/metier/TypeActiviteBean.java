package website.metier;

public class TypeActiviteBean {
	
	public int id;
	public String libelle;
	public String photo;
	public int getId() {
		return id;
	}
	
	public TypeActiviteBean(int id, String libelle, String photo) {
		super();
		this.id = id;
		this.libelle = libelle;
		this.photo = photo;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public TypeActiviteBean(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
}
