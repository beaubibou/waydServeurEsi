package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Preference;

public class ListPreferenceRV {

	private Erreur[] erreurs;
	private Preference[] preferences;

	public ListPreferenceRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

		

	public Preference[] getPreferences() {
		return preferences;
	}

	public void setPreferences(Preference[] preferences) {
		this.preferences = preferences;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initPreference(ArrayList<Preference> list) {
		
	preferences=	(Preference[]) list.toArray(new Preference[list.size()]);
	
	}
}
