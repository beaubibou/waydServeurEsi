package wayde.bean;


public class MessageConnexion {
int reponse;
String message;
String jeton;
public static int reponseOk=0;
public static int reponseNon=1;
public static int reponseDemandeActivation=2;
	
	public MessageConnexion(){
	super();
}
	

	public MessageConnexion(int reponse, String message, String jeton) {
		super();
		this.reponse = reponse;
		this.message = message;
		this.jeton = jeton;
	}


	public int getReponse() {
		return reponse;
	}


	public void setReponse(int reponse) {
		this.reponse = reponse;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getJeton() {
		return jeton;
	}


	public void setJeton(String jeton) {
		this.jeton = jeton;
	}




	


	
	
	
	
}
