package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;
import wayde.bean.Avis;
import wayde.bean.Profil;
import wayde.bean.ProfilNotation;

public class ProfilNotationRV {

	private Erreur[] erreurs;
	private ProfilNotation profil;
	
	public void initErreurs(ArrayList<Erreur> listErreurs) {
		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public ProfilNotationRV(){
		
	}

	
	public ProfilNotation getProfil() {
		return profil;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void initProfilNotation(ProfilNotation profil) {
		this.profil = profil;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}
	

	

	
	

}
