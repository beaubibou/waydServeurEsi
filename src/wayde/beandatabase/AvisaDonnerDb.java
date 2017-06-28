package wayde.beandatabase;

import java.util.Date;

public class AvisaDonnerDb {

	int idnoter;
	
	int idactivite;
	
	int idpersonnenotee;
	
	String nom;
	
	String prenom;
	
	String photo;
	
	String titre;
	
	Date dateDebutActivite;
	
	
	
	public AvisaDonnerDb(int idnoter, int idactivite, int idpersonnenotee,
			String nom, String prenom, String photo, String titre,Date dateDebutActivite) {
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.nom = nom;
		this.prenom = prenom;
		this.photo = photo;
		this.titre = titre;
		this.dateDebutActivite=dateDebutActivite;
	}

	public Date getDateDebutActivite() {
		return dateDebutActivite;
	}

	public void setDateDebutActivite(Date dateDebutActivite) {
		this.dateDebutActivite = dateDebutActivite;
	}

	public int getIdnoter() {
		return idnoter;
	}

	public AvisaDonnerDb(int idnoter, int idactivite, int idpersonnenotee,
			String titre) {
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.titre = titre;
	}

	public void setIdnoter(int idnoter) {
		this.idnoter = idnoter;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getIdactivite() {
		return idactivite;
	}

	public void setIdactivite(int idactivite) {
		this.idactivite = idactivite;
	}

	public int getIdpersonnenotee() {
		return idpersonnenotee;
	}

	public void setIdpersonnenotee(int idpersonnenotee) {
		this.idpersonnenotee = idpersonnenotee;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	
}
