package website.metier;

public class FiltreRecherche {
	int quand=0;
	int typerUser=0;
	int typeActivite=0;
	double latitude=48.8566;
	double longitude=2.3522;
	int rayon=2;
	int typeAcces=0;
	String adresse="Paris";
	private String motCle="";
	int typeMessage=0;
	
	
	public int getTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(int typeMessage) {
		this.typeMessage = typeMessage;
	}

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
	
		if (latitude==0||longitude==0)
		return "";
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

	public String getMotCle() {
		return motCle;
	}

	public void setMotCle(String motCle) {
		this.motCle = motCle;
	}

	
	
}
