package website.metier;


import java.io.Serializable;
import java.util.Date;

import wayde.bean.Parametres;



public class AvisBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int idnoter;
	int idactivite;
	int idpersonnenotee;
	int idpersonnenotateur;
	String nomnotateur,prenomnotateur;
	String photonotateur;
	String nomnote,prenomnote;
	
	String titre;

	String libelle;

	String titreactivite;
	boolean fait;

	double note;

	private String datenotationstr;
	
	
	
	
	public void setNote(double note) {
		this.note = note;
	}

	public AvisBean(int idnoter, int idactivite, int idpersonnenotee,
			int idpersonnenotateur, String nomnotateur, String prenomnotateur,
			String photonotateur, String nomnote, String prenomnote,
			String titre, String libelle, Date datenotation, boolean fait,
			double note) {
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.idpersonnenotateur = idpersonnenotateur;
		this.nomnotateur = nomnotateur;
		this.prenomnotateur = prenomnotateur;
		this.photonotateur = photonotateur;
		this.nomnote = nomnote;
		this.prenomnote = prenomnote;
		this.titre = titre;
		this.libelle = libelle;
		this.datenotationstr = Parametres.getStringWsFromDate(datenotation);;
		this.fait = fait;
		this.note = note;
	}




	public String getDatenotationstr() {
		return datenotationstr;
	}

	public void setDatenotationstr(String datenotationstr) {
		this.datenotationstr = datenotationstr;
	}

	public String getNomnotateur() {
		return nomnotateur;
	}


	public void setNomnotateur(String nomnotateur) {
		this.nomnotateur = nomnotateur;
	}




	public String getPrenomnotateur() {
		return prenomnotateur;
	}




	public void setPrenomnotateur(String prenomnotateur) {
		this.prenomnotateur = prenomnotateur;
	}




	public String getPhotonotateur() {
		return photonotateur;
	}




	public void setPhotonotateur(String photonotateur) {
		this.photonotateur = photonotateur;
	}




	public String getNomnote() {
		return nomnote;
	}




	public void setNomnote(String nomnote) {
		this.nomnote = nomnote;
	}




	public String getPrenomnote() {
		return prenomnote;
	}




	public void setPrenomnote(String prenomnote) {
		this.prenomnote = prenomnote;
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




	public int getIdpersonnenotateur() {
		return idpersonnenotateur;
	}




	public void setIdpersonnenotateur(int idpersonnenotateur) {
		this.idpersonnenotateur = idpersonnenotateur;
	}




	public String getTitre() {
		return titre;
	}




	public void setTitre(String titre) {
		this.titre = titre;
	}




	public String getLibelle() {
		return libelle;
	}




	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}




	public boolean isFait() {
		return fait;
	}




	public void setFait(boolean fait) {
		this.fait = fait;
	}




	public double getNote() {
		return note;
	}




	public AvisBean(int idnoter, int idactivite, int idpersonnenotee,
			int idpersonnenotateur, String titre, String libelle,
			Date datenotation, double note, String nomnotateur,
			String prenomnotateur, String photonotateur,String titreactivite) {
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.idpersonnenotateur = idpersonnenotateur;
		this.titre = titre;
		this.libelle = libelle;
		this.datenotationstr = Parametres.getStringWsFromDate(datenotation);
		this.note = note;
		this.nomnotateur = nomnotateur;
		this.prenomnotateur = prenomnotateur;
		this.photonotateur = photonotateur;
		this.titreactivite=titreactivite;
		
	}
	
	public AvisBean(int idnoter, int idactivite, int idpersonnenotee,
			int idpersonnenotateur, String titre, String libelle,
			Date datenotation, boolean fait, double note) {
		
		super();
		this.idnoter = idnoter;
		this.idactivite = idactivite;
		this.idpersonnenotee = idpersonnenotee;
		this.idpersonnenotateur = idpersonnenotateur;
		this.titre = titre;
	
		this.libelle = libelle;
		if (libelle.isEmpty())libelle= " ";
		this.datenotationstr = Parametres.getStringWsFromDate(datenotation);
		this.note = note;
	
	}

	public String getTitreactivite() {
		return titreactivite;
	}

	public void setTitreactivite(String titreactivite) {
		this.titreactivite = titreactivite;
	}


}
