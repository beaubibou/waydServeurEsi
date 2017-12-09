package website.pager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import website.dao.ProblemeDAO;
import website.dao.SuggestionDAO;
import website.metier.AmiBean;
import website.metier.ProblemeBean;
import website.metier.SuggestionBean;
import website.metier.admin.FitreAdminSuggestions;

public class PagerSuggestionBean {

	private static final Logger LOG = Logger.getLogger(PagerSuggestionBean.class);
	   
	private ArrayList<SuggestionBean> listSuggestion;
	private int pageEnCours = 0;
	private final int maxResult = 35;
	private boolean hasNext = false;
	private boolean hasPrevious = false;

	public PagerSuggestionBean(FitreAdminSuggestions filtre	,int pageEnCours) {

		this.pageEnCours = pageEnCours;

		// On recherhce les maxresult+1 si on
		listSuggestion = SuggestionDAO.getListSugestion(filtre,  pageEnCours,maxResult);

		if (listSuggestion.size()==maxResult) {
			hasNext = true;
			
		
		} else
			hasNext = false;

		if (pageEnCours > 0)
			hasPrevious = true;
		else
			hasPrevious = false;

	}

	

	public ArrayList<SuggestionBean> getListSuggestion() {
		return listSuggestion;
	}



	public void setListSuggestion(ArrayList<SuggestionBean> listSuggestion) {
		this.listSuggestion = listSuggestion;
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
			return "ListProbleme?page=" + suivant;
		} else
			return "#";
	}

	public String getLienPrevioustHtml() {
		if (hasPrevious) {
			int previous = pageEnCours - 1;
			return "ListProbleme?page=" + previous;
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
