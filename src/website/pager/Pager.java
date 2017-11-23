package website.pager;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import website.dao.ProblemeDAO;
import website.metier.ProblemeBean;

public class Pager {

	private ArrayList<?> listGenerique;
	private int pageEnCours = 0;
	private final int maxResult = 5;
	private boolean hasNext = false;
	private boolean hasPrevious = false;

	public Pager(int etatProbleme, DateTime debut, DateTime fin,
			int pageEnCours,ArrayList<?> listGenerique) {

		this.pageEnCours = pageEnCours;

		// On recherhce les maxresult+1 si on
		//listGenerique = ProblemeDAO.getListProbleme(etatProbleme, debut, fin,
		//		pageEnCours, maxResult + 1);
		this.listGenerique=listGenerique;

		if (listGenerique.size() == maxResult + 1) {
			hasNext = true;
			// on efface le dernier
			listGenerique.remove(maxResult);
		} else
			hasNext = false;

		if (pageEnCours > 0)
			hasPrevious = true;
		else
			hasPrevious = false;

	}

	public ArrayList<?> getListProbleme() {
		return listGenerique;
	}

	public void setListProbleme(ArrayList<ProblemeBean> listProbleme) {
		this.listGenerique = listProbleme;
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
	
	public String getLienNextHtml(){
	
		if (hasNext){
			this.pageEnCours++;

		return "ListProbleme?page="+pageEnCours;
		}
		else return "#";
	}
	
	public String getLienPrevioustHtml(){
		if (hasPrevious){
			this.pageEnCours--;

		return "ListProbleme?page="+pageEnCours;
		}
		else return "#";
	}
	
	public String isNextHtml(){
		
	if (!hasNext)
			return "class='disabled'";
	
	return "";
	
	}
	
public String isPreviousHtml(){
		
	if (!hasPrevious)
			return "class='disabled'";
		
	return "";
	
	}

	
	

}
