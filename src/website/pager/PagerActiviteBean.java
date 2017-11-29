package website.pager;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import website.dao.ActiviteDAO;
import website.metier.ActiviteBean;
import website.metier.AmiBean;
import website.metier.admin.FitreAdminActivites;

public class PagerActiviteBean {

	private static final Logger LOG = Logger.getLogger(PagerActiviteBean.class);
	   
	private ArrayList<ActiviteBean> listActivite;
	private int pageEnCours = 0;
	private final int maxResult = 35;
	private boolean hasNext = false;
	private boolean hasPrevious = false;

	public PagerActiviteBean(FitreAdminActivites filtre, int pageEnCours) {

		this.pageEnCours = pageEnCours;

		// On recherhce les maxresult+1 si on
		listActivite =ActiviteDAO.getListActivite(filtre,pageEnCours, maxResult );;

		if (listActivite.size()==maxResult) {
			hasNext = true;
			
		} else
			hasNext = false;

		if (pageEnCours > 0)
			hasPrevious = true;
		else
			hasPrevious = false;

	}

	public ArrayList<ActiviteBean> getListActivite() {
		return listActivite;
	}

	public void setListActivite(ArrayList<ActiviteBean> listActivite) {
		this.listActivite = listActivite;
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
			return "ListActivite?page=" + suivant;
		} else
			return "#";
	}

	public String getLienPrevioustHtml() {
		if (hasPrevious) {
			int previous = pageEnCours - 1;
			return "ListActivite?page=" + previous;
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
