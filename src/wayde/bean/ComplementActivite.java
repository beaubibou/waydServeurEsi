package wayde.bean;

public class ComplementActivite {

	boolean interet;
	boolean favori;
	
	public ComplementActivite(){
		
	}

	

	public ComplementActivite(boolean interet, boolean favori) {
		super();
		this.interet = interet;
		this.favori = favori;
	}



	public boolean isInteret() {
		return interet;
	}

	public void setInteret(boolean interet) {
		this.interet = interet;
	}

	public boolean isFavori() {
		return favori;
	}

	public void setFavori(boolean favori) {
		this.favori = favori;
	}
	
	
}
