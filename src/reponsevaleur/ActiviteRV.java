package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;

public class ActiviteRV {

	private Erreur[] erreurs;
	private Activite activite;

	public void initErreurs(ArrayList<Erreur> listErreurs) {

		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public ActiviteRV(){
		
	}

	public void initActivite(Activite activite) {
		this.activite = activite;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Activite getActivite() {
		return activite;
	}

	public void setActivite(Activite activite) {
		this.activite = activite;
	}
	
	

}
