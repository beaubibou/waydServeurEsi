package wayde.beandatabase;

import java.util.Date;

import org.apache.log4j.Logger;

import wayde.bean.MessageConnexion;

public class CategorieDb
{
	private static final Logger LOG = Logger.getLogger(CategorieDb.class);

	long id;
    String nom;
    Date datecreation;
    boolean active;
    
    public CategorieDb(){
        
        
    }
    public CategorieDb(String nom)
    {
        super();
        this.nom = nom;
        this.datecreation=new Date();
    }
        
	public CategorieDb(long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
	}
	
	public long getId()
    {
        return id;
    }
   
    public String getNom()
    {
        return nom;
    }
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    public Date getDatecreation()
    {
        return datecreation;
    }
    
    public boolean isActive()
    {
        return active;
    }
    

    
}
