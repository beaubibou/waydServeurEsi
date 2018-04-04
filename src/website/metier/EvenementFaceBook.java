package website.metier;

import java.util.ArrayList;

public class EvenementFaceBook {

	String idEvent;
	String nom, ville;
	String urlPhoto;
	double lat, lng;

	ArrayList<ActiviteCarpeDiem> listActivite = new ArrayList<ActiviteCarpeDiem>();

	public EvenementFaceBook(String idEvent) {

		this.idEvent = idEvent;
	}

	public void addActiviteEchance(ActiviteCarpeDiem activite) {

		listActivite.add(activite);

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getUrlPhoto() {
		return urlPhoto;
	}

	public void setUrlPhoto(String urlPhoto) {
		this.urlPhoto = urlPhoto;
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

	public String getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(String idEvent) {
		this.idEvent = idEvent;
	}

	public ArrayList<ActiviteCarpeDiem> getListActivite() {
		return listActivite;
	}

	public void setListActivite(ArrayList<ActiviteCarpeDiem> listActivite) {
		this.listActivite = listActivite;
	}

}
