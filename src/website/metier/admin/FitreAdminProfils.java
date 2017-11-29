package website.metier.admin;

import java.util.Date;

import org.apache.log4j.Logger;

import website.metier.AmiBean;
import website.metier.TypeSignalement;
import website.metier.TypeUser;

public class FitreAdminProfils {
	private static final Logger LOG = Logger.getLogger(FitreAdminProfils.class);
	   
	Date dateCreationDebut;
	Date dateCreationFin;
	String pseudo;
	int typeUser;
	int etatProfil;
	int typeSignalement;
	
	
	public FitreAdminProfils(){
	
		pseudo=null;
		dateCreationDebut=new Date();
		dateCreationFin=new Date();
		typeUser=TypeUser.TOUS;
		etatProfil=EtatProfil.TOUS;
		typeSignalement=TypeSignalement.TOUS;
			
	}


	public int getTypeSignalement() {
		return typeSignalement;
	}


	public void setTypeSignalement(int typeSignalement) {
		this.typeSignalement = typeSignalement;
	}


	public Date getDateCreationDebut() {
		return dateCreationDebut;
	}


	public void setDateCreationDebut(Date dateCreationDebut) {
		this.dateCreationDebut = dateCreationDebut;
	}


	public Date getDateCreationFin() {
		return dateCreationFin;
	}


	public void setDateCreationFin(Date dateCreationFin) {
		this.dateCreationFin = dateCreationFin;
	}


	public String getPseudo() {
		if (pseudo==null|| pseudo.isEmpty())
				return"";
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public int getTypeUser() {
		return typeUser;
	}


	public void setTypeUser(int typeUser) {
		this.typeUser = typeUser;
	}


	public int getEtatProfil() {
		return etatProfil;
	}


	public void setEtatProfil(int etatProfil) {
		this.etatProfil = etatProfil;
	}
	
	
}
