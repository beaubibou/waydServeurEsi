package website.metier;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;





public class ActiviteCarpeDiem {

	String startDate;
	String endDate;
	String image;
	String description;
	String url;
	String name;
	String address;
	String nomLieu;
	String ville;
	double lat,lng;
	int id;
	String lienFaceBook;
	private String fulldescription;
	

	
	public String getLienFaceBook() {
		return lienFaceBook;
	}

	public void setLienFaceBook(String lienFaceBook) {
		this.lienFaceBook = lienFaceBook;
	}

	public String getFulldescription() {
		return fulldescription;
	}

	public void setFulldescription(String fulldescription) {
		this.fulldescription = fulldescription;
	}

	public ActiviteCarpeDiem(String startDate, String endDate, String image,
			String description, String url, String name, String address,
			String nomLieu, String ville, double lat, double lng, int id) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.image = image;
		this.description = description;
		this.url = url;
		this.name = name;
		this.address = address;
		this.nomLieu = nomLieu;
		this.ville = ville;
		this.lat = lat;
		this.lng = lng;
		this.id = id;
	}

	public ActiviteCarpeDiem() {
		this.startDate = null;
		this.endDate = null;
		this.image = null;
		this.description = null;
		this.url = null;
		this.name = null;
		this.address = null;
		this.nomLieu = null;
		this.ville = null;
		this.lat = 0;
		this.lng = 0;
		this.id = 0;
	}
	public void reset(){
		this.startDate = null;
		this.endDate = null;
		this.image = null;
		this.description = null;
		this.url = null;
		this.name = null;
		this.address = null;
		this.nomLieu = null;
		this.ville = null;
		this.lat = 0;
		this.lng = 0;
		this.id = 0;
		
	}
	public ActiviteCarpeDiem(ActiviteCarpeDiem activite) {
		this.startDate = activite.startDate;
		this.endDate = activite.endDate;
		this.image = activite.image;
		this.description = activite.description;
		this.url = activite.url;
		this.name = activite.name;
		this.address = activite.address;
		this.nomLieu =activite.nomLieu;
		this.ville = activite.ville;
		this.lat = activite.lat;
		this.lng = activite.lng;
		this.id = activite.id;
		
		
	}
	

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getStartDate() {
		return startDate;
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
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		
		if (this.name==null){
			this.name=name;
			
		}
		else
		{
			this.nomLieu=name;
			
		}
			
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getNomLieu() {
		return nomLieu;
	}
	
	public void setNomLieu(String nomLieu) {
		this.nomLieu = nomLieu;
	}
	
	public boolean isComplete() {
	
		if (address!=null && nomLieu!=null)
			return true;
		
		return false;
	}
	
	@Override
	public String toString() {
		return "ActiviteCarpeDiem [startDate=" + startDate + ", endDate="
				+ endDate + ", image=" + image + ", description=" + description
				+ ", url=" + url + ", name=" + name + ", address=" + address
				+ ", nomLieu=" + nomLieu + ", lat=" + lat + ", lng=" + lng
				+ ", id=" + id + "]";
	}
	

	public Date getDateDebut() throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return formatter.parse(startDate);
	
	
	}
	

	public Date getDateFin() throws ParseException {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	    return formatter.parse(endDate);
	
	}

	

	
		
}
