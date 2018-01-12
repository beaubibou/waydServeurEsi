package reponsevaleur;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class MessageServeurRV {
	private static final Logger LOG = Logger.getLogger(MessageServeurRV.class);

	boolean reponse;
	String message;
	private Erreur[] erreurs;

	public MessageServeurRV() {
		super();
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

		erreurs = (Erreur[]) listErreurs
				.toArray(new Erreur[listErreurs.size()]);
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

	public MessageServeurRV(boolean reponse, String message) {
		// lmklm
		super();
		this.reponse = reponse;
		this.message = message;
	}

	public boolean isReponse() {
		return reponse;
	}

	public void setReponse(boolean reponse) {
		this.reponse = reponse;
	}

	public String getMessage() {
		// mlml
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
