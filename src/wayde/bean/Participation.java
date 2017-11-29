package wayde.bean;



import java.util.Date;

import org.apache.log4j.Logger;

public class Participation
{
	private static final Logger LOG = Logger.getLogger(Participation.class);

    static int EtatAttenteValidation = 0;

    static int EtatValidee = 1;

    static int EtatRefusee = 2;

    static int EtatAnnuleeOrganisateur = 3;

    static int EtatDesincription = 4;

    
    int id;

    int iddemandeur;

    int idorganisateur;

    int idactivite;

    int etatparticipation;

    String message;
    
    Date datecreation;

    public Participation()
    {

    }
    
    public Participation(int iddemandeur, int idorganisateur, int idactivite, String message)
    {
        super();
        this.iddemandeur = iddemandeur;
        this.idorganisateur = idorganisateur;
        this.idactivite = idactivite;
        this.message = message;
        datecreation=new Date();
        etatparticipation=Participation.EtatValidee;
        
    }
    public Participation(int iddemandeur, int idorganisateur, int idactivite)
    {
        super();
        this.iddemandeur = iddemandeur;
        this.idorganisateur = idorganisateur;
        this.idactivite = idactivite;
        datecreation=new Date();
       
    }

    public static int getEtatAttenteValidation()
    {
        return EtatAttenteValidation;
    }

    public static void setEtatAttenteValidation(int etatAttenteValidation)
    {
        EtatAttenteValidation = etatAttenteValidation;
    }

    public static int getEtatValidee()
    {
        return EtatValidee;
    }

    public static void setEtatValidee(int etatValidee)
    {
        EtatValidee = etatValidee;
    }

    public static int getEtatRefusee()
    {
        return EtatRefusee;
    }

    public static void setEtatRefusee(int etatRefusee)
    {
        EtatRefusee = etatRefusee;
    }

    public static int getEtatAnnuleeOrganisateur()
    {
        return EtatAnnuleeOrganisateur;
    }

    public static void setEtatAnnuleeOrganisateur(int etatAnnuleeOrganisateur)
    {
        EtatAnnuleeOrganisateur = etatAnnuleeOrganisateur;
    }

    public static int getEtatDesincription()
    {
        return EtatDesincription;
    }

    public static void setEtatDesincription(int etatDesincription)
    {
        EtatDesincription = etatDesincription;
    }

    public long getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getIddemandeur()
    {
        return iddemandeur;
    }

    public void setIddemandeur(int iddemandeur)
    {
        this.iddemandeur = iddemandeur;
    }

    public int getIdorganisateur()
    {
        return idorganisateur;
    }

    public void setIdorganisateur(int idorganisateur)
    {
        this.idorganisateur = idorganisateur;
    }

    public long getIdactivite()
    {
        return idactivite;
    }

    public void setIdactivite(int idactivite)
    {
        this.idactivite = idactivite;
    }

    public int getEtatparticipation()
    {
        return etatparticipation;
    }

    public void setEtatparticipation(int etatparticipation)
    {
        this.etatparticipation = etatparticipation;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Date getDatecreation()
    {
        return datecreation;
    }

    public void setDatecreation(Date datecreation)
    {
        this.datecreation = datecreation;
    }

}
