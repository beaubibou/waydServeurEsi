package wayde.bean;

public class Droit {

	private int idpersonne;
	private boolean admin, verrouille, actif;

	public Droit() {

	}

	public Droit(int idpersonne, boolean admin, boolean verrouille,
			boolean actif) {
		super();
		this.idpersonne = idpersonne;
		this.admin = admin;
		this.verrouille = verrouille;
		this.actif = actif;
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
	
	//	return new MessageServeur(true, "Ok");
		if (!actif)
			return new MessageServeur(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeur(false, "Votre compte est plus verrouille");

		return new MessageServeur(true, "Ok");
	}

	public MessageServeur isDefautAccess() {// Test uniquement que le compte,est
											// actif est pas verrouille

	//	return new MessageServeur(true, "Ok");
		
	//	System.out.println("actif "+actif);
		
		if (!actif)
			return new MessageServeur(false, "Votre compte n'est plus actif");
		if (verrouille)
			return new MessageServeur(false, "Votre compte est plus verrouille");

		return new MessageServeur(true, "Ok");
	}

	public MessageServeur isEffaceActivite(Activite activite, int idpersonne) {

	//	return new MessageServeur(true, "Ok");
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
