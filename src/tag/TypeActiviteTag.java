package tag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import website.dao.TypeActiviteDAO;
import website.metier.TypeActiviteBean;

public class TypeActiviteTag extends SimpleTagSupport {

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		ArrayList<TypeActiviteBean> listTypeActivite = TypeActiviteDAO
				.getListTypeActivite();
	out.println("<select class=\"form-control\" id=\"sel1\" name=\"typeactivite\">\"");
		out.println("<option value=\"-1\">Toutes</option>");
		for (TypeActiviteBean typeactivite : listTypeActivite) {

			out.println("<option value=\"" + typeactivite.getId() +"\""+ "id=\""
					+ typeactivite.getId() + "\">" + typeactivite.getLibelle()
					+ "</option>");
		

		}
		out.println("</select>");
	}
}