package website.metier.admin;

import org.apache.log4j.Logger;

import website.metier.TypeActiviteBean;
import website.metier.TypeEtatActivite;
import website.metier.TypeGratuitActivite;
import website.metier.TypeSignalement;
import website.metier.TypeUser;

public class FitreAdminActivites {
	private static final Logger LOG = Logger.getLogger(FitreAdminActivites.class);
	   
	int rayon,typeactivite;
	String ville;
	double latitude,longitude;
	int typeUser;
	int typeSignalement;
	int etatActivite;
	int gratuit;
	boolean actif,masque;
	
	
	public boolean isActif() {
		return actif;
	}
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	public boolean isMasque() {
		return masque;
	}
	public void setMasque(boolean masque) {
		this.masque = masque;
	}
	public int getGratuit() {
		return gratuit;
	}
	public void setGratuit(int gratuit) {
		this.gratuit = gratuit;
	}
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
	
	public int getEtatActivite() {
		return etatActivite;
	}
	public void setEtatActivite(int etatActivite) {
		this.etatActivite = etatActivite;
	}
	public  FitreAdminActivites(){
		this.rayon = 3000000;
		this.typeactivite = TypeActiviteBean.TOUS;
		this.ville = "Paris";
		this.latitude = 48;
		this.longitude = 2;
		typeUser=TypeUser.TOUS;
		typeSignalement=TypeSignalement.TOUS;
		this.etatActivite=TypeEtatActivite.TOUTES;
		this.gratuit=TypeGratuitActivite.GRATUITE_INCONNU;
		this.actif=false;
		this.masque=false;
	}
}
