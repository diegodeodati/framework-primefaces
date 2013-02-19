package it.betplus.web.framework.messages;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public class Messages {

	public static String getMessage(String id, Locale locale) {
		ResourceBundle bundle = getResourceBundle(locale);
		return bundle.getString(id);
	}

	public static ResourceBundle getResourceBundle(Locale locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
		ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msgs");
		return bundle;
	}

	public static Map<String, Object> getSessionMap() {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	}

	public static void setViewRootLocale(Locale locale) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(locale);
		getSessionMap().put("currentLocale", locale);
	}
	
}
