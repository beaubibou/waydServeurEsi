package wayde.bean;

import org.apache.log4j.Logger;



public class Preference {
	private static final Logger LOG = Logger.getLogger(Preference.class);

	int idpersonne;
	int idtypeactivite;
	boolean active;
	boolean always;
	String nom;
	public Preference(int idpersonne, int idtypeactivite, boolean active,String nom) {
		super();
		this.idpersonne = idpersonne;
		this.idtypeactivite = idtypeactivite;
		this.active = active;
		this.always = true;
		this.nom=nom;
		
	}
	public Preference(int idpersonne, int idtypeactivite) {
		super();
		this.idpersonne = idpersonne;
		this.idtypeactivite = idtypeactivite;
		
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isAlways() {
		return always;
	}
	public void setAlways(boolean always) {
		this.always = always;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Preference() {
		super();
		
	}
	public long getIdpersonne() {
		return idpersonne;
	}
	public void setIdpersonne(int idpersonne) {
		this.idpersonne = idpersonne;
	}
	public long getIdtypeactivite() {
		return idtypeactivite;
	}
	public void setIdtypeactivite(int idtypeactivite) {
		this.idtypeactivite = idtypeactivite;
	}

	
}
