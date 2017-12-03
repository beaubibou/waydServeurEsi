package website.metier;

import java.util.Date;

import org.apache.log4j.Logger;

public class SignalementProfilBean {

	private static final Logger LOG = Logger.getLogger(SignalementProfilBean.class);
	   
	public int idpersonneSignalee;
	public int idpersonneInformateur;
	public String pseudoSignale,pseudoInformateur;
	public Date d_creation;
	public int idMotif;
	private String motif,libelle;
	
	
	public SignalementProfilBean(int idpersonneSignalee, int idpersonneInformateur,
			String pseudoSignale, String pseudoInformateur, Date d_creation,
			int idMotif, String motif,String libelle) {
		super();
		this.idpersonneSignalee = idpersonneSignalee;
		this.idpersonneInformateur = idpersonneInformateur;
		this.pseudoSignale = pseudoSignale;
		this.pseudoInformateur = pseudoInformateur;
		this.d_creation = d_creation;
		this.idMotif = idMotif;
		this.motif = motif;
		this.libelle=libelle;
	}

	public String getDateCreationStr() {
		return Outils.getStringWsFromDate(d_creation);
		}

	public String getLibelle() {
		return libelle;
	}


	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


	public int getIdpersonneSignalee() {
		return idpersonneSignalee;
	}


	public void setIdpersonneSignalee(int idpersonneSignalee) {
		this.idpersonneSignalee = idpersonneSignalee;
	}


	public int getIdpersonneInformateur() {
		return idpersonneInformateur;
	}


	public void setIdpersonneInformateur(int idpersonneInformateur) {
		this.idpersonneInformateur = idpersonneInformateur;
	}


	public String getPseudoSignale() {
		return pseudoSignale;
	}


	public void setPseudoSignale(String pseudoSignale) {
		this.pseudoSignale = pseudoSignale;
	}


	public String getPseudoInformateur() {
		return pseudoInformateur;
	}


	public void setPseudoInformateur(String pseudoInformateur) {
		this.pseudoInformateur = pseudoInformateur;
	}


	public Date getD_creation() {
		return d_creation;
	}


	public void setD_creation(Date d_creation) {
		this.d_creation = d_creation;
	}


	public int getIdMotif() {
		return idMotif;
	}


	public void setIdMotif(int idMotif) {
		this.idMotif = idMotif;
	}


	public String getMotif() {
		return motif;
	}


	public void setMotif(String motif) {
		this.motif = motif;
	}
	
	
	
	
}
