package reponsevaleur;

import java.util.ArrayList;

import wayde.bean.Activite;
import wayde.bean.Notification;
import wayde.bean.Participant;
import wayde.bean.PhotoWaydeur;

public class ListPhotoWaydeurRV {

	private Erreur[] erreurs;
	private PhotoWaydeur[] photoWaydeurs;

	public ListPhotoWaydeurRV() {
		super();
	}

	public Erreur[] getErreurs() {
		return erreurs;
	}

	public void setErreurs(Erreur[] erreurs) {
		this.erreurs = erreurs;
	}

		
	
	public PhotoWaydeur[] getPhotoWaydeurs() {
		return photoWaydeurs;
	}

	public void setPhotoWaydeurs(PhotoWaydeur[] photoWaydeurs) {
		this.photoWaydeurs = photoWaydeurs;
	}

	public void initErreurs(ArrayList<Erreur> listErreurs) {

	erreurs=	(Erreur[]) listErreurs.toArray(new Erreur[listErreurs
		                           					.size()]);
	}

	public void initPhotoWaydeurs(ArrayList<PhotoWaydeur> list) {
		
	photoWaydeurs=	(PhotoWaydeur[]) list.toArray(new PhotoWaydeur[list.size()]);
	
	}
}
