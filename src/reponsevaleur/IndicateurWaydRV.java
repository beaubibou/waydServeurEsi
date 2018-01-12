package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.IndicateurWayd;
import wayde.bean.Version;

public class IndicateurWaydRV {

	private Erreur[] erreurs;
	private IndicateurWayd indicateur;


	public void initErreurs(ArrayList<Erreur> listErreurs) {
		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public IndicateurWaydRV(){
		
	}

	public void initIndicateurWaydRV(IndicateurWayd indicateur) {
		this.indicateur = indicateur;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public IndicateurWayd getIndicateur() {
		return indicateur;
	}

	public void setIndicateur(IndicateurWayd indicateur) {
		this.indicateur = indicateur;
	}

	

	

	
	

}
