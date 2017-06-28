package website.metier;

public class SignalementCount {
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
