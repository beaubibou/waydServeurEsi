package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Notification;

public class ListNotificationsRV {

	private Erreur[] erreurs;
	private Notification[] notifications;

	public ListNotificationsRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}


	public Notification[] getNotifications() {
		return notifications;
	}

	public void setNotifications(Notification[] notifications) {
		this.notifications = notifications;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initNotifications(ArrayList<Notification> list) {
		
	notifications=	(Notification[]) list.toArray(new Notification[list.size()]);

	}
}
