package tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import website.dao.TypeActiviteDAO;
import website.metier.Pagination;
import website.metier.TypeActiviteBean;

public class PaginationTag extends SimpleTagSupport {

	int nbrTotalLigne,pageAafficher;
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
	System.out.println("rentre dans pagination tag"+nbrTotalLigne);
	//
	}
	public int getNbrTotalLigne() {
		return nbrTotalLigne;
	}
	public void setNbrTotalLigne(int nbrTotalLigne) {
		this.nbrTotalLigne = nbrTotalLigne;
	}
	public int getPageAafficher() {
		return pageAafficher;
	}
	public void setPageAafficher(int pageAafficher) {
		this.pageAafficher = pageAafficher;
	}
	
	
}