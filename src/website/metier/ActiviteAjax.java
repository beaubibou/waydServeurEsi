package website.metier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import fcm.ServeurMethodes;

public class ActiviteAjax implements Comparable<ActiviteAjax> {
	private static final Logger LOG = Logger.getLogger(ActiviteAjax.class);
	int id;

	public static int GRATUIT=1;
	public static int PAYANT=2;

	String titre;

	String libelle;

	int idorganisateur;

	double latitude;

	double longitude;

	String photo;

	private String nomorganisateur;

	private String pseudo;

	private int typeactivite;

	private int typeUser;

	public double distancePoint;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getIdorganisateur() {
		return idorganisateur;
	}

	public void setIdorganisateur(int idorganisateur) {
		this.idorganisateur = idorganisateur;
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getNomorganisateur() {
		return nomorganisateur;
	}

	public void setNomorganisateur(String nomorganisateur) {
		this.nomorganisateur = nomorganisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getTypeactivite() {
		return typeactivite;
	}

	public void setTypeactivite(int typeactivite) {
		this.typeactivite = typeactivite;
	}

	public int getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}

	public ActiviteAjax(int id, String titre, String libelle,
			int idorganisateur, double latitude, double longitude,
			String photo, String nomorganisateur, String pseudo,
			int typeUser,int typeactivite) {
		super();
		this.id = id;
		this.titre = titre;
		this.libelle = libelle;
		this.idorganisateur = idorganisateur;
		this.latitude = latitude;
		this.longitude = longitude;
		this.photo = photo;
		this.nomorganisateur = nomorganisateur;
		this.pseudo = pseudo;
		this.typeactivite = typeactivite;
		this.typeUser = typeUser;
	}
	

	public void calculDistancePoint(double malatitude,double malongitude){
		
		 distancePoint = ServeurMethodes.getDistance(malatitude, latitude,
				malongitude, longitude);
	}

	@Override
	public int compareTo(ActiviteAjax activiteAjax) {
		// TODO Auto-generated method stub
		if (this.distancePoint>activiteAjax.distancePoint)
			return 0;
	
		return 1;
	}

	
}
