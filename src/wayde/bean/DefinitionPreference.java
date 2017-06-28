package wayde.bean;

import wayde.beandatabase.DefinitionPreferenceDb;


/**
 * Created by bibou on 27/10/2016.
 */

public class DefinitionPreference {
    
    int jour;
    int heuredebut;
    int heurefin;
    int typeactivite;
    boolean active;
    boolean always;
  
    public DefinitionPreference() {
       
    }

    public int getTypeactivite() {
		return typeactivite;
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

	public void setTypeactivite(int typeactivite) {
		this.typeactivite = typeactivite;
	}

	public DefinitionPreference(DefinitionPreferenceDb defPrefdb) {
	
    	
    	this.jour=defPrefdb.getJour();
    	this.heuredebut=defPrefdb.getHeuredebut();
    	this.heurefin=defPrefdb.getHeurefin();
    	this.typeactivite=defPrefdb.getTypeactivite();
    	this.active=defPrefdb.isActive();
    	this.always=defPrefdb.isAlways();
    	// TODO Auto-generated constructor stub
	}

	public int getHeurefin() {
        return heurefin;
    }

    public void setHeurefin(int heurefin) {
        this.heurefin = heurefin;
    }

    public int getHeuredebut() {
        return heuredebut;
    }

    public void setHeuredebut(int heuredebut) {
        this.heuredebut = heuredebut;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

}
