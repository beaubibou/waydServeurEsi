package website.metier;

public class TypeUser {
	public final static int TOUS = 0, PRO = 1, WAYDEUR = 3;
	
	int id;
	String libelle;
	public TypeUser(int id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}
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
	
}
