package wayde.beandatabase;

public class TypeActiviteDb {
	long id;
	long idcategorie;
	String nom;
	int typeUser;
	
	public int getTypeUser() {
		return typeUser;
	}
	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}
	public TypeActiviteDb(long id, long idcategorie, String nom,int typeUser) {
		super();
		this.id = id;
		this.idcategorie = idcategorie;
		this.nom = nom;
		this.typeUser=typeUser;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
}
