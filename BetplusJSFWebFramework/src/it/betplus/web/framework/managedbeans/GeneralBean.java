package it.betplus.web.framework.managedbeans;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class GeneralBean {

	protected static final Log log = LogFactory.getLog(GeneralBean.class);
	
	private List<SelectItem> dateTypes;
	private String userDateTypeSelected;
	
	public GeneralBean() {
		
		super();
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T findBean(String beanName) {
		
	    FacesContext context = FacesContext.getCurrentInstance();
	    return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	    
	}

	public static UIComponent findComponentInRoot(String id) {
		
	    UIComponent component = null;

	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    if (facesContext != null) {
	      UIComponent root = facesContext.getViewRoot();
	      component = findComponent(root, id);
	    }

	    return component;
	    
	}
	    
	public static UIComponent findComponent(UIComponent base, String id) {
		
	    if (id.equals(base.getId()))
	      return base;
	  
	    UIComponent kid = null;
	    UIComponent result = null;
	    Iterator<UIComponent> kids = base.getFacetsAndChildren();
	    while (kids.hasNext() && (result == null)) {
	      kid = (UIComponent) kids.next();
	      if (id.equals(kid.getId())) {
	        result = kid;
	        break;
	      }
	      result = findComponent(kid, id);
	      if (result != null) {
	        break;
	      }
	    }
	    
	    return result;
	    
	}
	
	public void sendErrorMessageToUser(String messageSummary, String message) {
		
		// Error message to user GUI
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageSummary, message);  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
		
	}
	
	public void sendInfoMessageToUser(String messageSummary, String message) {
		
		// Info message to user GUI
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, messageSummary, message);
		FacesContext.getCurrentInstance().addMessage(null, msg); 
		
	}

	public String getFromBundleMsgs(String msgKey) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs"); 
		return bundle.getString(msgKey);
		
	}
	
	public String getFromBundleConfig(String msgKey) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "config");
		return bundle.getString(msgKey);
		
	}
	
	public ResourceBundle getMsgsBundleResource() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getApplication().getResourceBundle(context, "msgs"); 
	}
	
	// redirect to navigationurl outcome
	public void sendRedirectToOutcome(String outcome) {
	
		FacesContext facesContext = FacesContext.getCurrentInstance();	    
	    facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, outcome);
	    
	}
	
	// redirect to url/pagename url
	public void sendRedirectToUrl(String url) throws IOException{
			
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    facesContext.getExternalContext().redirect(url);
	    
	}
	
	// return the current request URI
	public String getRequestURI(){
		return ((HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest())
				.getRequestURI();
	}
	
	// return the current application root folder
	public String getApplicationPath() {

		ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
		return ext.getRealPath("/");
		
	}
	
	
	
	
	public List<SelectItem> getDateTypes() {
		return dateTypes;
	}

	public void setDateTypes(List<SelectItem> dateTypes) {
		this.dateTypes = dateTypes;
	}

	public String getUserDateTypeSelected() {
		return userDateTypeSelected;
	}

	public void setUserDateTypeSelected(String userDateTypeSelected) {
		this.userDateTypeSelected = userDateTypeSelected;
	}


}
