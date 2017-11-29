package wayde.bean;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import wayde.beandatabase.TypeActiviteDb;

public class TypeActivite {
	
	private static final Logger LOG = Logger.getLogger(TypeActivite.class);

	long id;
	long idcategorie;
	String nom;
	private boolean ischecked;
	private int typeUser;
	  
	public TypeActivite(long id, long idcategorie, String nom) {
		super();
		this.id = id;
		this.idcategorie = idcategorie;
		this.nom = nom;
		this.ischecked = false;
		
	
	}
	public TypeActivite(long id, long idcategorie, String nom,boolean ischecked) {
		super();
		this.id = id;
		this.idcategorie = idcategorie;
		this.nom = nom;
		this.ischecked = ischecked;
	
	}
	public TypeActivite() {
		super();
		
	}
	
	public int getTypeUser() {
		return typeUser;
	}
	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}
	public TypeActivite(TypeActiviteDb souscategoriedb) {
		super();
		this.id =souscategoriedb.getId();
		this.idcategorie = souscategoriedb.getIdcategorie();
		this.nom = souscategoriedb.getNom();// TODO Auto-generated constructor stub
		this.typeUser=souscategoriedb.getTypeUser();
	}
	public long getId() {
		return id;
	}
		
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	public long getIdcategorie() {
		return idcategorie;
	}
	public void setIdcategorie(long idcategorie) {
		this.idcategorie = idcategorie;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public static void isPreference(ArrayList<Preference> listpreference,TypeActivite typeactivite)

	{
	    for (Preference preference:listpreference){
	         if (
	              preference.getIdtypeactivite()==typeactivite.getId()  )
	        	 typeactivite.setIschecked(true);


	    }

	}
	
}
