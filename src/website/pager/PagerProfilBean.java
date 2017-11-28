package website.pager;

import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import website.dao.ActiviteDAO;
import website.dao.PersonneDAO;
import website.metier.ActiviteBean;
import website.metier.ProfilBean;
import website.metier.admin.FitreAdminProfils;

public class PagerProfilBean {

	private ArrayList<ProfilBean> listProfils;
	private int pageEnCours = 0;
	private final int maxResult = 35;
	private boolean hasNext = false;
	private boolean hasPrevious = false;

	public PagerProfilBean(FitreAdminProfils filtre, int pageEnCours) {

		this.pageEnCours = pageEnCours;

		// On recherhce les maxresult+1 si on
		listProfils =PersonneDAO.getListProfil(filtre, pageEnCours,maxResult);

		if (listProfils.size()==maxResult) {
			hasNext = true;
			
		
		} else
			hasNext = false;

		if (pageEnCours > 0)
			hasPrevious = true;
		else
			hasPrevious = false;

	}

	

	public ArrayList<ProfilBean> getListProfils() {
		return listProfils;
	}





	public void setListProfils(ArrayList<ProfilBean> listProfils) {
		this.listProfils = listProfils;
	}





	public int getPageEnCours() {
		return pageEnCours;
	}

	public void setPageEnCours(int pageEnCours) {
		this.pageEnCours = pageEnCours;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public String getLienNextHtml() {

	
		if (hasNext) {
			int suivant = pageEnCours + 1;
			return "ListProfil?page=" + suivant;
		} else
			return "#";
	}

	public String getLienPrevioustHtml() {
		if (hasPrevious) {
			int previous = pageEnCours - 1;
			return "ListProfil?page=" + previous;
		} else
			return "#";
	}

	public String isNextHtml() {

		if (!hasNext)
			return "class='disabled'";

		return "";

	}

	public String isPreviousHtml() {

		if (!hasPrevious)
			return "class='disabled'";

		return "";

	}

}
