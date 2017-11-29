package website.metier;

import org.apache.log4j.Logger;

public class SignalementCount {
	private static final Logger LOG = Logger.getLogger(SignalementCount.class);
	   
 private int idpersonne=0;
 private int nbrSignalement;
 private String pseudo;
public int getIdpersonne() {
	return idpersonne;
}
public void setIdpersonne(int idpersonne) {
	this.idpersonne = idpersonne;
}
public int getNbrSignalement() {
	return nbrSignalement;
}
public void setNbrSignalement(int nbrSignalement) {
	this.nbrSignalement = nbrSignalement;
}
public SignalementCount(int idpersonne, int nbrSignalement,String pseudo) {
	super();
	this.idpersonne = idpersonne;
	this.nbrSignalement = nbrSignalement;
	this.pseudo=pseudo;
}
public String getPseudo() {
	return pseudo;
}
public void setPseudo(String pseudo) {
	this.pseudo = pseudo;
}

}
