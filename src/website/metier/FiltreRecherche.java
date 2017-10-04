package website.metier;

public class FiltreRecherche {
	int quand=3;
	int typerUser=1;
	int typeActivite=3;
	double latitude=48.85661400000001,longitude=2.3522219000000177;
	int rayon=2;
	int typeAcces=0;
	String adresse="Paris";
	int typeEtatActivite=TypeEtatActivite.TOUTES;
	
	public int getTypeEtatActivite() {
		return typeEtatActivite;
	}

	public void setTypeEtatActivite(int typeEtatActivite) {
		this.typeEtatActivite = typeEtatActivite;
	}

	public FiltreRecherche(){
		
		
	}

	public int getTypeAcces() {
		return typeAcces;
	}

	public void setTypeAcces(int typeAcces) {
		this.typeAcces = typeAcces;
	}

	public int getQuand() {
		return quand;
	}

	public void setQuand(int quand) {
		this.quand = quand;
	}

	public int getTyperUser() {
		return typerUser;
	}

	public void setTyperUser(int typerUser) {
		this.typerUser = typerUser;
	}

	public int getTypeActivite() {
		return typeActivite;
	}

	public void setTypeActivite(int typeActivite) {
		this.typeActivite = typeActivite;
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
	
}
