package website.html;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import website.enumeration.AlertJsp;
public class AlertInfoJsp {
	private static final Logger LOG = Logger.getLogger(AlertInfoJsp.class);

	String message;
	AlertJsp typeAlert;
	private String redirection;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AlertJsp getTypeAlert() {
		return typeAlert;
	}

	public void setTypeAlert(AlertJsp typeAlert) {
		this.typeAlert = typeAlert;
	}

	public String getRedirectionHtml(){
		
		if (redirection==null)return "";
		else
		{	return "document.location.href="+"\""+redirection+"\""+";";
			
		}
			
		
	}
	public AlertInfoJsp(String message, AlertJsp typeAlert,String redirection) {
		super();
		this.message = message;
		this.typeAlert = typeAlert;
		this.redirection=redirection;
	}

	public String getHtml() {

		String retour = "";

		switch (typeAlert) {
		case Alert:
			retour="<div id=\"myAlert\" class='alert alert-danger text-center'>"+
			        "<a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>"+
			        "<strong>"+message+"</div>";
			break;

		case Info:
			retour="<div id=\"myAlert\" class=\"alert alert-info\">"+
			        "<a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>"+
			        "<strong>"+message+"</div>";
	
			break;
		case Sucess:
			
			retour="<div id=\"myAlert\" class='alert alert-success text-center'>"+
			        "<a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>"+
			        "<br><strong>"+message+"<br></div>";
			
			
			
			break;
			
	case warning:
			
			retour="<div id=\"myAlert\" class='alert alert-warning text-center'>"+
        "<a href=\"#\" class=\"close\" data-dismiss=\"alert\">&times;</a>"+
        "<br><strong>"+message+"<br></div>";
	
			
			
			
			break;
			

		default:
			break;
		}
		

		return retour;
	}

	public void send(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("alerte", this);
		try {
			request.getRequestDispatcher("commun/alert.jsp").forward(
					request, response);
		} catch (ServletException | IOException e) {
			LOG.error( ExceptionUtils.getStackTrace(e));
		}
	}
}
