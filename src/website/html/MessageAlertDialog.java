package website.html;

import org.apache.log4j.Logger;

import website.enumeration.AlertJsp;

public class MessageAlertDialog {
	private static final Logger LOG = Logger.getLogger(MessageAlertDialog.class);

	String message;
	String titre;
	String action;
	AlertJsp typeMessage=null;
	
	public MessageAlertDialog(String titre,String message,  String action) {
		super();
		this.message = message;
		this.titre = titre;
		this.action = action;
	}
	
	public AlertJsp getTypeMessage() {
		return typeMessage;
	}

	public void setTypeMessage(AlertJsp typeMessage) {
		this.typeMessage = typeMessage;
	}

	public MessageAlertDialog(String titre,String message,  String action,AlertJsp typeMessage) {
		super();
		this.message = message;
		this.titre = titre;
		this.action = action;
		this.typeMessage=typeMessage;
	}
	public MessageAlertDialog(MessageAlertDialog messageAlertDialog) {
		// TODO Auto-generated constructor stub
	this.message=messageAlertDialog.getMessage();
	this.titre=messageAlertDialog.getTitre();
	this.action=messageAlertDialog.getAction();
	this.typeMessage=messageAlertDialog.getTypeMessage();
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
