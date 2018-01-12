package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;

public class ListActivitesRV {

	private Erreur[] erreurs;
	private Activite[] activites;

	public ListActivitesRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public Activite[] getActivites() {
		return activites;
	}

	public void setActivites(Activite[] activites) {
		this.activites = activites;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initActivite(ArrayList<Activite> listActivite) {
	activites=	(Activite[]) listActivite.toArray(new Activite[listActivite
		                           					.size()]);
	}
}
