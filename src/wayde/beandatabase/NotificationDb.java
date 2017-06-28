package wayde.beandatabase;

import java.util.Date;

public class NotificationDb {
	
	int idestinataire;
	int idtype; 
	int idactivite; 
	int idpersonne;
	Date d_creation;

public NotificationDb (int idestinataire,	int idtype,	int idactivite,	int idpersonne,	Date d_creation){
	
	this.idestinataire=idestinataire;
	this.idtype=idtype;
	this.idactivite=idactivite;
	this.idpersonne=idpersonne;
	this.d_creation=d_creation;
}

public int getIdestinataire() {
	return idestinataire;
}

public void setIdestinataire(int idestinataire) {
	this.idestinataire = idestinataire;
}

public int getIdtype() {
	return idtype;
}

public void setIdtype(int idtype) {
	this.idtype = idtype;
}

public int getIdactivite() {
	return idactivite;
}

public void setIdactivite(int idactivite) {
	this.idactivite = idactivite;
}

public int getIdpersonne() {
	return idpersonne;
}

public void setIdpersonne(int idpersonne) {
	this.idpersonne = idpersonne;
}

public Date getD_creation() {
	return d_creation;
}

public void setD_creation(Date d_creation) {
	this.d_creation = d_creation;
}

}
