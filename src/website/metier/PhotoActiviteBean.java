package website.metier;

public class PhotoActiviteBean {
int id;
int idActivite;
String photo;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIdActivite() {
	return idActivite;
}
public void setIdActivite(int idActivite) {
	this.idActivite = idActivite;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public PhotoActiviteBean(int id, int idActivite, String photo) {
	super();
	this.id = id;
	this.idActivite = idActivite;
	this.photo = photo;
}

public String getLienSuppression(){
	
	return "SupprimePhotoActivite?id="+id+"&idActivite="+idActivite;
}


}
