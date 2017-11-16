package website.html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import website.enumeration.AlertJsp;
import website.metier.AuthentificationSite;
import website.metier.ProfilBean;

public class AlertDialog {
	MessageAlertDialog messageAlertDialog;
	public static String ALERT_DIALOG = "messageAlertDialog";
	private AlertJsp typeAlert;

	public static String getAlertInfo(String titre, String message) {

		if (message == null)
			return "";
		return "affichePoPup('" + titre + "','" + message + "');";

	}

	public AlertDialog(HttpServletRequest requete) {

		messageAlertDialog = (MessageAlertDialog) requete
				.getAttribute(ALERT_DIALOG);
		requete.setAttribute(ALERT_DIALOG, null);
	}

	public AlertDialog(AuthentificationSite authentificationSite) {

		typeAlert = null;

		if (authentificationSite.getMessageAlertDialog() != null)
			// On fait un clone du message stocke dans la sessionc
			messageAlertDialog = new MessageAlertDialog(
					authentificationSite.getMessageAlertDialog());
		else
			messageAlertDialog = null;
		// on efface le MessageAlertDIalog de la session
		authentificationSite.setAlertMessageDialog(null);
	}
	
	
	public AlertDialog(String messeAlert, AlertJsp typeMessage) {
		
		if (messeAlert!= null)
			// On fait un clone du message stocke dans la sessionc
			messageAlertDialog = new MessageAlertDialog("Titre",messeAlert,null,typeMessage);
			
		else
			messageAlertDialog = null;
		
	}

	public AlertDialog(AuthentificationSite authentificationSite,
			AlertJsp typeAlert) {
		this.typeAlert = typeAlert;

		if (authentificationSite.getMessageAlertDialog() != null)
			// On fait un clone du message stocke dans la sessionc
			messageAlertDialog = new MessageAlertDialog(
					authentificationSite.getMessageAlertDialog());
		else
			messageAlertDialog = null;
		// on efface le MessageAlertDIalog de la session
		authentificationSite.setAlertMessageDialog(null);
	}

	@Override
	public String toString() {
		return "AlertDialog [messageAlertDialog=" + messageAlertDialog + "]";
	}

	public String getMessage() {

	
		if (messageAlertDialog == null)
			return "";
	

		if (messageAlertDialog.getTypeMessage() == AlertJsp.Sucess) {

			return "BootstrapDialog.success(\""+messageAlertDialog.getMessage()+"\");";
		}
		
		
		if (messageAlertDialog.getTypeMessage() == AlertJsp.danger) {

			return "BootstrapDialog.danger(\""+messageAlertDialog.getMessage()+"\");";
		}
		
		if (messageAlertDialog.getTypeMessage() == AlertJsp.warning) {

			return "BootstrapDialog.warning(\""+messageAlertDialog.getMessage()+"\");";
		}

		if (messageAlertDialog.getAction() == null)
			return "affichePoPup('" + messageAlertDialog.getTitre() + "','"
					+ messageAlertDialog.getMessage() + "');";

		if (messageAlertDialog.getAction() != null)
			
			return "affichePoPupAction('" + messageAlertDialog.getTitre()
					+ "','" + messageAlertDialog.getMessage() + "','"
					+ messageAlertDialog.getAction() + "');";

		return "";
	}

}
