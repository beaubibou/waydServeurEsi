package website.metier;

import org.apache.log4j.Logger;

import fcm.ServeurMethodes;

public class UserAjax implements Comparable<UserAjax> {
	private static final Logger LOG = Logger.getLogger(UserAjax.class);

	int id;

	String pseudo,photo;

	double latitude;

	double longitude;

	public double distancePoint;
	
	
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

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public UserAjax(int id,String pseudo, double latitude, double longitude,String photo) {
		// TODO Auto-generated constructor stub
	this.pseudo=pseudo;
	this.photo=photo;
	this.latitude=latitude;
	this.longitude=longitude;
	this.id=id;

	}

	public void calculDistancePoint(double malatitude,double malongitude){
		
		 distancePoint = ServeurMethodes.getDistance(malatitude, latitude,
				malongitude, longitude);
	}

	@Override
	public int compareTo(UserAjax userAjax) {
		
		if (this.distancePoint>userAjax.distancePoint)
			return 0;
	
		return 1;
	}

	
}
