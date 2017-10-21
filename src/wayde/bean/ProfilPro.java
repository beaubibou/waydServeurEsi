package wayde.bean;

public class ProfilPro {
	int id;
	
	private String prenom;
	private String adresse;
	private String siret;
	private String telephone;
	private Long datecreation;
	private int nbractivite;
	private String photostr;
	private String commentaire;
	private String siteweb;

	

	public ProfilPro() {
		super();
	}

	public String getSiteweb() {
		return siteweb;
	}

	public void setSiteweb(String siteweb) {
		this.siteweb = siteweb;
	}

	public ProfilPro(int id, String prenom, String adresse, String siret,
			String telephone, Long datecreation, int nbractivite,
			String photostr, String commentaire, String siteweb) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.adresse = adresse;
		this.siret = siret;
		this.telephone = telephone;
		this.datecreation = datecreation;
		this.nbractivite = nbractivite;
		this.photostr = photostr;
		this.commentaire = commentaire;
		this.siteweb = siteweb;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Long getDatecreation() {
		return datecreation;
	}

	public void setDatecreation(Long datecreation) {
		this.datecreation = datecreation;
	}

	public int getNbractivite() {
		return nbractivite;
	}

	public void setNbractivite(int nbractivite) {
		this.nbractivite = nbractivite;
	}

	public String getPhotostr() {
		return photostr;
	}

	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	
	
}
