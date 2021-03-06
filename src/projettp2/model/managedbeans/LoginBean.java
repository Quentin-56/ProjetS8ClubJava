package projettp2.model.managedbeans;

import java.io.Serializable;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;

import projettp2.model.services.CommentService;
import projettp2.model.services.MemberService;


@Named /* ("loginBean") */
@SessionScoped // On précise que la portée du bean est limitée à la session en cours
public class LoginBean implements Serializable {

    private static final long serialVersionUID = -5433850275008415405L;
    
    private String login = "";
    private String password = "";
    private String connectedUserLogin = "";
    
    public boolean isUserLoggedIn() {
		return !(connectedUserLogin.equals("") || connectedUserLogin.isEmpty() || connectedUserLogin==null);
	}
    
    public String getConnectedUserLogin() {
		return connectedUserLogin;
	}

	public void setConnectedUserLogin(String connectedUserLogin) {
		this.connectedUserLogin = connectedUserLogin;
	}

	public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

	public String returnAction() {
        if(MemberService.validateMember(login, password)) {
        	connectedUserLogin = login;
        	login = "";
        	password = "";
        	return "success";
        }
        else {
        	return "failure";
        }
    }
    
    public void validateLogin(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String inputValue = (String) value;
    	Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    	if( !pattern.matcher(inputValue).matches()) {
    		FacesMessage msg = new FacesMessage("The login must be an email adress");
    		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		throw new ValidatorException(msg);
    	}
    }
    
    public void validateLoginDisponibility(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String inputValue = (String) value;
    	Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    	if( !pattern.matcher(inputValue).matches()) {
    		FacesMessage msg = new FacesMessage("The login must be an email adress");
    		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		throw new ValidatorException(msg);
    	}
    	if( MemberService.getMemberFromLogin(inputValue)!=null) {
    		FacesMessage msg = new FacesMessage("Email already in use, please use another one");
    		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		throw new ValidatorException(msg);
    	}
    }
    
    public void validatePassword(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	String inputValue = (String) value;
    	Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,8}$");
    	if( !pattern.matcher(inputValue).matches()) {
    		FacesMessage msg = new FacesMessage("Your password must contain between 4 and 8 non special characters");
    		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
    		throw new ValidatorException(msg);
    	}
    }
    
    public String logOut()
    {
    	connectedUserLogin = "";
    	login = "";
    	password = "";
    	return "logout";
    }
}