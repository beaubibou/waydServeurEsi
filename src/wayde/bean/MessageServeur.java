package wayde.bean;

public class MessageServeur {
boolean reponse;
String message;
	
	public MessageServeur(){
	super();
}

	public MessageServeur(boolean reponse, String message) {
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
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
