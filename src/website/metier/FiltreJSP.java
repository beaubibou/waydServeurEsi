package website.metier;

// Classe utilisée pour garder en memoire le filtre

public class FiltreJSP {

	int rayon,typeactivite;
	String ville;
	double latitude,longitude;
	
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
		return "FiltreJSP [rayon=" + rayon + ", typeactivite=" + typeactivite
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
	public FiltreJSP(int rayon, int typeactivite, String ville,
			double latitude, double longitude) {
		super();
		this.rayon = rayon;
		this.typeactivite = typeactivite;
		this.ville = ville;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	
	
}
