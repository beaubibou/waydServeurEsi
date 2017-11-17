package website.metier.admin;

public class EtatProbleme {

	public final static int TOUS = 0, CLOTURE = 1, NONCLOTOURE = 2;
	
	int id;
	String libelle;
	public EtatProbleme(int id, String libelle) {
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
