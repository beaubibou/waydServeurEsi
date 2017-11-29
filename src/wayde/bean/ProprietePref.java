package wayde.bean;

import org.apache.log4j.Logger;

public class ProprietePref {
	private static final Logger LOG = Logger.getLogger(ProprietePref.class);

int rayon;
double latitude,longitude;
String adresse;


public ProprietePref(int rayon, double latitude, double longitude,
		String adresse) {
	super();
	this.rayon = rayon;
	this.latitude = latitude;
	this.longitude = longitude;
	this.adresse = adresse;
}

public ProprietePref() {
	super();
	

}

public String getAdresse() {
	return adresse;
}

public void setAdresse(String adresse) {
	this.adresse = adresse;
}

public int getRayon() {
	return rayon;
}

public void setRayon(int rayon) {
	this.rayon = rayon;
}

public double getLatitude() {
	return latitude;
}

public void setLatitude(double latitude) {
	this.latitude = latitude;
}

public double getLongitude() {
	return longitude;
}

public void setLongitude(double longitude) {
	this.longitude = longitude;
}

}
