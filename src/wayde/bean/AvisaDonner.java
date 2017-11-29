package wayde.bean;

import org.apache.log4j.Logger;

import wayde.beandatabase.AvisaDonnerDb;

public class AvisaDonner {
	
	private static final Logger LOG = Logger.getLogger(AvisaDonner.class);

	int idnoter;
	
	int idactivite;
	
	int idpersonnenotee;
	
	String nom;
	
	String prenom;
	
	String photo;
	
	String titre;
	

	
	public AvisaDonner() {
		super();
	}

	public AvisaDonner(int idnoter, int idactivite, int idpersonnenotee,
			String titre) {
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.titre = titre;
	}

	public AvisaDonner(AvisaDonnerDb avisdonnerdb) {
		super();
		this.idnoter = avisdonnerdb.getIdnoter();
		this.idactivite = avisdonnerdb.getIdactivite();
		this.idpersonnenotee = avisdonnerdb.getIdpersonnenotee();
		this.titre = avisdonnerdb.getTitre();
		this.nom=avisdonnerdb.getNom();
		this.photo=avisdonnerdb.getPhoto();
		this.prenom=avisdonnerdb.getPrenom();
		
	}
	public int getIdnoter() {
		return idnoter;
	}

	public void setIdnoter(int idnoter) {
		this.idnoter = idnoter;
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


	
}
