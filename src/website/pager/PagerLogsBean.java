package website.pager;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import website.dao.ActiviteDAO;
import website.dao.LogDAO;
import website.dao.PersonneDAO;
import website.metier.ActiviteBean;
import website.metier.AmiBean;
import website.metier.LogBean;
import website.metier.ProfilBean;
import website.metier.admin.FitreAdminLogs;
import website.metier.admin.FitreAdminProfils;

public class PagerLogsBean {

	private static final Logger LOG = Logger.getLogger(PagerLogsBean.class);
	   
	private ArrayList<LogBean> listLogs;
	private int pageEnCours = 0;
	private final int maxResult = 35;
	private boolean hasNext = false;
	private boolean hasPrevious = false;

	public PagerLogsBean(FitreAdminLogs filtre, int pageEnCours) {

		this.pageEnCours = pageEnCours;

		// On recherhce les maxresult+1 si on
		listLogs =LogDAO.getListLog(filtre, pageEnCours,maxResult);

		if (listLogs.size()==maxResult) {
			hasNext = true;
			
		
		} else
			hasNext = false;

		if (pageEnCours > 0)
			hasPrevious = true;
		else
			hasPrevious = false;

	}



	

	public ArrayList<LogBean> getListLogs() {
		return listLogs;
	}





	public void setListLogs(ArrayList<LogBean> listLogs) {
		this.listLogs = listLogs;
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
			return "ListLogs?page=" + suivant;
		} else
			return "#";
	}

	public String getLienPrevioustHtml() {
		if (hasPrevious) {
			int previous = pageEnCours - 1;
			return "ListLogs?page=" + previous;
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
