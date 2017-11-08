package website.html;

public class MessageAlertDialog {

	String message;
	String titre;
	String action;
	public MessageAlertDialog(String message, String titre, String action) {
		super();
		this.message = message;
		this.titre = titre;
		this.action = action;
	}
	public MessageAlertDialog(MessageAlertDialog messageAlertDialog) {
		// TODO Auto-generated constructor stub
	this.message=messageAlertDialog.getMessage();
	this.titre=messageAlertDialog.getTitre();
	this.action=messageAlertDialog.getAction();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "MessageAlertDialog [message=" + message + ", titre=" + titre
				+ ", action=" + action + "]";
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
