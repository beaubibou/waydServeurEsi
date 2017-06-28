package website.metier;

public class TypeActiviteBean {
	
	public int id;
	public String libelle;
	public int getId() {
		return id;
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

	
}
