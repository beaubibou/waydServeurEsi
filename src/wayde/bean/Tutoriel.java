package wayde.bean;

public class Tutoriel {
	public static int PROPOSE_ACTIVITE=1;
	public static int MA_SPHERE=2;
	public static int DETAIL_ACTIVITE=3;
	public static int MES_PREFERENCES=4;
	public static int RECHERCE_ACTIVITE=5;
	public static int ENNUIE=6;
	
	
	  private int id;
	  private String titre;
	  private String conseil;
	
	public Tutoriel(int id, String titre, String conseil) {
		super();
		this.id = id;
		this.titre = titre;
		this.conseil = conseil;
		
	}
	public Tutoriel(){
		
	}
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
	public String getConseil() {
		return conseil;
	}
	public void setConseil(String conseil) {
		this.conseil = conseil;
	}
	
	
	  
}
