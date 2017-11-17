package website.metier.admin;

public class EtatProfil {

	public final static int TOUS = 0, ACTIF = 1, INACTIF = 2;
	
	int id;
	String libelle;
	public EtatProfil(int id, String libelle) {
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
	public static int getTous() {
		return TOUS;
	}
	public static int getActif() {
		return ACTIF;
	}
	public static int getInactif() {
		return INACTIF;
	}

	
}
