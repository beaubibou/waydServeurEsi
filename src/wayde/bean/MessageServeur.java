package wayde.bean;

import org.apache.log4j.Logger;

public class MessageServeur {
	private static final Logger LOG = Logger.getLogger(MessageServeur.class);

	boolean reponse;
String message;
	
	public MessageServeur(){
	super();
}

	public MessageServeur(boolean reponse, String message) {
		//lmklm
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
		//mlml
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
