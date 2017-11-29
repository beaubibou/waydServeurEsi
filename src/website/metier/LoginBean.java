package website.metier;

import org.apache.log4j.Logger;


	public class LoginBean {  
		private static final Logger LOG = Logger.getLogger(LoginBean.class);
		   
		private String email,pass;  
		  
		public String getEmail() {  
		    return email;  
		}  
		  
		public void setEmail(String email) {  
		    this.email = email;  
		}  
		  
		public String getPass() {  
		    return pass;  
		}  
		  
		public void setPass(String pass) {  
		    this.pass = pass;  
		}  
		  
		  
		  
}
