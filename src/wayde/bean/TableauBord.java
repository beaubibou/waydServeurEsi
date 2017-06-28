package wayde.bean;


public class TableauBord {
int nbrmessagenonlu;
int nbractiviteencours;
int nbrsuggestion;
int nbrnotification;
int nbrami;
public TableauBord() {
	
	super();
	
}



public TableauBord(int nbrmessagenonlu, int nbractiviteencours,int nbrsuggestion,int nbrnotification,int nbrami) {
	
	super();
	this.nbrmessagenonlu = nbrmessagenonlu;
	this.nbractiviteencours = nbractiviteencours;
	this.nbrsuggestion=nbrsuggestion;
	this.nbrnotification=nbrnotification;
	this.nbrami=nbrami;
}


public int getNbrnotification() {
	return nbrnotification;
}


public int getNbrami() {
	return nbrami;
}


public void setNbrami(int nbrami) {
	this.nbrami = nbrami;
}


public void setNbrnotification(int nbrnotification) {
	this.nbrnotification = nbrnotification;
}


public int getNbrsuggestion() {
	return nbrsuggestion;
}


public void setNbrsuggestion(int nbrsuggestion) {
	this.nbrsuggestion = nbrsuggestion;
}

public int getNbrmessagenonlu() {
	return nbrmessagenonlu;
}

public void setNbrmessagenonlu(int nbrmessagenonlu) {
	this.nbrmessagenonlu = nbrmessagenonlu;
}
public int getNbractiviteencours() {
	return nbractiviteencours;
}
public void setNbractiviteencours(int nbractiviteencours) {
	this.nbractiviteencours = nbractiviteencours;
}


}
