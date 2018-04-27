package carpediem;

public class VillePosition {

String nom;
double lat=0;
double lng=0;
public VillePosition(String nom, double lat, double lng) {
	super();
	this.nom = nom;
	this.lat = lat;
	this.lng = lng;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public double getLat() {
	return lat;
}
public void setLat(double lat) {
	this.lat = lat;
}
public double getLng() {
	return lng;
}
public void setLng(double lng) {
	this.lng = lng;
}

public String getPosition(){
		
	return String.valueOf(lat)+","+String.valueOf(lng);
}


}
