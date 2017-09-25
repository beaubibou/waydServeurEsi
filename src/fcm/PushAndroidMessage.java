package fcm;

import java.util.Date;

public class PushAndroidMessage {
String id;
Date datecreation;
String text;
String titre;
static int refreshtdb=1;
static int notification=2;
public static final int NBR_MESSAGE_NONLU=3;
public static final int NBR_ACTIVITE=4;
public static final int NBR_NOTIFICATION=5;
public static final int NBR_SUGGESTION=6;
public static final int Annule_Activite=7;
public static final int Annule_PARTICIPATION=8;
public static final int NBR_AMI = 9;
public static final int TDB_REFRESH=10;
public static final int EFFACE_SUGGESTION = 11;
public static final int UPDATE_ACTIVITE = 12;
public static final int UPDATE_NOTIFICATION=13;

public PushAndroidMessage(String id, Date datecreation, String text, String titre) {
	super();
	this.id = id;
	this.datecreation = datecreation;
	this.text = text;
	this.titre = titre;
}
public PushAndroidMessage(String id) {
	super();
	this.id = id;
	
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public Date getDatecreation() {
	return datecreation;
	//
}
public void setDatecreation(Date datecreation) {
	this.datecreation = datecreation;
}
public String getTexte() {
	return text;
}
public void setTexte(String text) {
	this.text = text;
}
public String getTitre() {
	return titre;
}
public void setTitre(String titre) {
	this.titre = titre;
}
}
