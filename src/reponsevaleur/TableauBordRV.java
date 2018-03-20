package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.TableauBord;

public class TableauBordRV {

	private Erreur[] erreurs;
	private TableauBord tableauBord;

	
	public void initErreurs(ArrayList<Erreur> listErreurs) {
		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}
	
	public TableauBordRV(){
		
	}

	public void initTableauBord(TableauBord tableauBord) {
		this.tableauBord = tableauBord;
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public TableauBord getTableauBord() {
		return tableauBord;
	}

	public void setTableauBord(TableauBord tableauBord) {
		this.tableauBord = tableauBord;
	}
	

	

	
	

}
