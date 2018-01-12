package wayde.bean;

import org.apache.log4j.Logger;

import reponsevaleur.MessageServeurRV;

public class Droit {

	private static final Logger LOG = Logger.getLogger(Droit.class);

	private int idpersonne;
	private boolean admin, verrouille, actif;
	private String jeton;

	public Droit() {

	}

	public Droit(int idpersonne, boolean admin, boolean verrouille,
			boolean actif, String jeton) {
		super();
		this.idpersonne = idpersonne;
		this.admin = admin;
		this.verrouille = verrouille;
		this.actif = actif;
		this.jeton = jeton;
	}

	public boolean isJetonOk(String jeton) {

		if (this.jeton.equals(jeton))
			return true;

		return false;

	}

	public String getJeton() {
		return jeton;
	}

	public void setJeton(String jeton) {
		this.jeton = jeton;
	}

	public boolean isActif() {
		return actif;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public int getIdpersonne() {
		return idpersonne;
	}

	public void setIdpersonne(int idpersonne) {
		this.idpersonne = idpersonne;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isVerrouille() {
		return verrouille;
	}

	public void setVerrouille(boolean verrouille) {
		this.verrouille = verrouille;
	}

	public MessageServeur isAddActivite() {

		// return new MessageServeur(true, "Ok");
		if (!actif)
			return new MessageServeur(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeur(false, "Votre compte est plus verrouille");

		return new MessageServeur(true, "Ok");
	}

	public MessageServeur isDefautAccess() {// Test uniquement que le compte,est
											// actif est pas verrouille

		if (!actif)
			return new MessageServeur(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeur(false, "Votre compte est plus verrouille");

		return new MessageServeur(true, "Ok");
	}

	public MessageServeurRV isDefautAccessRV() {// Test uniquement que le
												// compte,est
		// actif est pas verrouille

		if (!actif)
			return new MessageServeurRV(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeurRV(false, "Votre compte est plus verrouille");

		return new MessageServeurRV(true, "Ok");
	}

	public MessageServeur isEffaceActivite(Activite activite, int idpersonne) {

		// return new MessageServeur(true, "Ok");
		if (!actif)
			return new MessageServeur(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeur(false, "Votre compte est plus verrouille");

		if (admin)
			return new MessageServeur(true, "Ok");

		if (!admin && activite.isOganisateurActivite(idpersonne))
			return new MessageServeur(true, "Ok");

		return new MessageServeur(false, "Ok");

	}

}
