package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Profil;

public class ProfilRV {

	private Erreur[] erreurs;
	private Profil profil;

	
	public void initErreurs(ArrayList<Erreur> listErreurs) {
		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public ProfilRV(){
		
	}

	public void initProfil(Profil profil) {
		this.profil = profil;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}
	public Profil getProfil() {
		return profil;
	}
	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	

	
	

}
