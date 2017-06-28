package wayde.bean;

import java.util.Date;



public class Ami {
    private int id;
    private String login;
    private String nom;
    private String prenom;
    private String photostr;
    private String depuisle;
    private double note;
    public final static int ETAT_ATTENTE=0;
    public final static int ETAT_ACTIF=1;
    public final static int ETAT_BANNI=2;
    
    

    public String getDepuisle() {
		return depuisle;
	}


	public double getNote() {
		return note;
	}


	public void setNote(double note) {
		this.note = note;
	}


	public void setDepuisle(String depuisle) {
		this.depuisle = depuisle;
	}


	public Ami(String photostr, String prenom, String nom, String login, int id,Date depuisle,double note) {
        this.photostr=photostr;
        this.prenom = prenom;
        this.nom = nom;
        this.login = login;
        if (login==null)this.login="";
        this.id = id;
        this.depuisle = Parametres.getStringWsFromDate(depuisle);
        this.note=note;
     
   
    }

  
public Ami(){
	
	
}



    public String getPhotostr() {
		return photostr;
	}


	public void setPhotostr(String photostr) {
		this.photostr = photostr;
	}


	public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}