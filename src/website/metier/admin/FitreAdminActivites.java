package website.metier.admin;

import website.metier.TypeActiviteBean;
import website.metier.TypeSignalement;
import website.metier.TypeUser;

public class FitreAdminActivites {
	int rayon,typeactivite;
	String ville;
	double latitude,longitude;
	int typeUser;
	int typeSignalement;
	
	
	public int getTypeSignalement() {
		return typeSignalement;
	}
	public void setTypeSignalement(int typeSignalement) {
		this.typeSignalement = typeSignalement;
	}
	public int getTypeUser() {
		return typeUser;
	}
	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}
	public int getRayon() {
		return rayon;
	}
	public void setRayon(int rayon) {
		this.rayon = rayon;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	@Override
	public String toString() {
		return "FiltreADmin [rayon=" + rayon + ", typeactivite=" + typeactivite
				+ ", ville=" + ville + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
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
	public int getTypeactivite() {
		return typeactivite;
	}
	public void setTypeactivite(int typeactivite) {
		this.typeactivite = typeactivite;
	}
	public  FitreAdminActivites(int rayon, int typeactivite, String ville,
			double latitude, double longitude) {
		super();
		this.rayon = rayon;
		this.typeactivite = typeactivite;
		this.ville = ville;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public  FitreAdminActivites(){
		this.rayon = 3000000;
		this.typeactivite = TypeActiviteBean.TOUS;
		this.ville = "Paris";
		this.latitude = 48;
		this.longitude = 2;
		typeUser=TypeUser.TOUS;
		typeSignalement=TypeSignalement.TOUS;
		
	}
}
