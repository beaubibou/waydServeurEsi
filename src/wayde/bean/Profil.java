package wayde.bean;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import texthtml.pro.Erreur_HTML;


public class Profil {
	private static final Logger LOG = Logger.getLogger(Profil.class);

	int id;
	private String nom;
	private String prenom;
	private String ville;
//	private String datecreationstr;
	//private String datenaissancestr;
	private int nbravis;
	private int sexe;
	private int nbractivite;
	private int nbrparticipation;
	private int nbrami;
	private double note;
	private String photostr;
	private String age;
	private String commentaire;
	
	public Profil() {

	}

	

	public String getCommentaire() {
		return commentaire;
	}



	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}



	public Profil(int id, String nom, String prenom, String ville,
			Date datecreation, Date datenaissance, int nbravis,
			int sexe, int nbractivite, int nbrparticipation, int nbrami,
			double note, String photostr, boolean affichesexe,boolean afficheage,String commentaire) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.ville = ville;
		this.nbravis = nbravis;
		this.sexe = sexe;
		this.nbractivite = nbractivite;
		this.nbrparticipation = nbrparticipation;
		this.nbrami = nbrami;
		this.note = note;
		this.photostr = photostr;
		this.age=getAgeStr(datenaissance,afficheage);
		if (affichesexe)this.sexe=3;
		this.commentaire=commentaire;
	
	}
	public  String getAgeStr(Date datenaissance,boolean afficheage)
	{
		if (afficheage)return Erreur_HTML.MASQUE;
		if (datenaissance!=null){
			Calendar curr = Calendar.getInstance();
			Calendar birth = Calendar.getInstance();
			birth.setTime(datenaissance);
			int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
			curr.add(Calendar.YEAR,-yeardiff);
			if(birth.after(curr))
			{
				yeardiff = yeardiff - 1;
			}
			if (yeardiff<0)return Erreur_HTML.PAS_AGE_INDIQUE;
			if (yeardiff==0)return Erreur_HTML.PAS_AGE_INDIQUE;
			return Integer.toString(yeardiff) +" ans";
		}

		return Erreur_HTML.PAS_AGE_INDIQUE;
	}


	
	public int getNbractivite() {
		return nbractivite;
	}

	public void setNbractivite(int nbractivite) {
		this.nbractivite = nbractivite;
	}

	public int getNbrparticipation() {
		return nbrparticipation;
	}

	public void setNbrparticipation(int nbrparticipation) {
		this.nbrparticipation = nbrparticipation;
	}

	public int getNbrami() {
		return nbrami;
	}

	public void setNbrami(int nbrami) {
		this.nbrami = nbrami;
	}

	public Profil(Personne personne) {

		nom = personne.getNom();
		prenom = personne.getPrenom();
		ville = personne.getVille();
		nbravis=personne.getNbravis();
		note=personne.getNote();
		photostr=personne.getPhoto();
		sexe=personne.getSexe();
		id=personne.getId();
		this.age=getAgeStr(personne.datenaissance,personne.isAfficheage());
		if (personne.isAffichesexe())this.sexe=3;
		
		// TODO Auto-generated constructor stub
	}

	public int getSexe() {
		return sexe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSexe(int sexe) {
		this.sexe = sexe;
	}

	public String getNom() {
		return nom;
	}

	public String getPhotostr() {
		return photostr;
	}

	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}

	//public void setDatecreationstr(String datecreationstr) {
//		this.datecreationstr = datecreationstr;
	//}

	//public void setDatenaissancestr(String datenaissancestr) {
	//	this.datenaissancestr = datenaissancestr;
	//}

	public void setNbravis(int nbravis) {
		this.nbravis = nbravis;
	}

	public void setNote(double note) {
		this.note = note;
	}

	//public String getDatecreationstr() {
	//	return datecreationstr;
	//}

	//public String getDatenaissancestr() {
	//	return datenaissancestr;
	//}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getNbravis() {
		return nbravis;
	}

	public double getNote() {
		return note;
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

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	
}
