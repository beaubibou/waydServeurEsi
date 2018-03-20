package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Personne;

public class PersonneRV {

	private Erreur[] erreurs;
	private Personne personne;

	public void initErreurs(ArrayList<Erreur> listErreurs) {

		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public PersonneRV(){
		
	}

	public void initPersonne(Personne personne) {
		this.personne = personne;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	
	
	

}
