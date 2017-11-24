package website.metier.admin;

import java.util.Date;

import website.metier.TypeUser;

public class FitreAdminProfils {

	Date dateCreationDebut;
	Date dateCreationFin;
	String pseudo;
	int typeUser;
	int etatProfil;
	
	
	public FitreAdminProfils(){
	
		pseudo=null;
		dateCreationDebut=new Date();
		dateCreationFin=new Date();
		typeUser=TypeUser.TOUS;
		etatProfil=EtatProfil.TOUS;
			
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
