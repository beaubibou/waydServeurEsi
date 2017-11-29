package wayde.beandatabase;

import org.apache.log4j.Logger;

import wayde.bean.MessageConnexion;

public class DefinitionPreferenceDb {
	
	private static final Logger LOG = Logger.getLogger(DefinitionPreferenceDb.class);
	int jour;
	int heuredebut;
	int heurefin;
	int typeactivite;
	int idpersonne;
	boolean active;
	boolean always;

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

	public DefinitionPreferenceDb(int jour, int heuredebut, int heurefin,
			int typeactivite, int idpersonne, boolean active, boolean always) {
		super();

		this.jour = jour;
		this.heuredebut = heuredebut;
		this.heurefin = heurefin;
		this.typeactivite = typeactivite;
		this.idpersonne = idpersonne;
		this.active = active;
		this.always = always;
	}

	public DefinitionPreferenceDb(int idpersonne2, boolean active,
			boolean always) {
		this.active = active;
		this.always = always;

	}

	public int getJour() {
		return jour;
	}

	public void setJour(int jour) {
		this.jour = jour;
	}

	public int getHeuredebut() {
		return heuredebut;
	}

	public void setHeuredebut(int heuredebut) {
		this.heuredebut = heuredebut;
	}

	public int getHeurefin() {
		return heurefin;
	}

	public void setHeurefin(int heurefin) {
		this.heurefin = heurefin;
	}

	public int getTypeactivite() {
		return typeactivite;
	}

	public void setTypeactivite(int typeactivite) {
		this.typeactivite = typeactivite;
	}

	public int getIdpersonne() {
		return idpersonne;
	}

	public void setIdpersonne(int idpersonne) {
		this.idpersonne = idpersonne;
	}

}
