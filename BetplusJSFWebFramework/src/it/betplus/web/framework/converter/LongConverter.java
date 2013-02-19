package it.betplus.web.framework.converter;

import it.betplus.web.framework.managedbeans.GeneralBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.primefaces.context.RequestContext;

//@FacesConverter("it.betplus.moneyphone.convertor.LongConverter")

public class LongConverter extends GeneralBean implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		RequestContext currentContext = RequestContext.getCurrentInstance();
		//log.debug("getAsObject Converter for component: " + component.getClientId() + " value = " + value);
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		Long rValue = null;
		if (value.trim().equals("")) {
			return value;
			//return rValue;
		} else {
			try {

				rValue = Long.parseLong(value);

			} catch(NumberFormatException exception) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}
		}

		return rValue;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		//log.debug("getAsString Converter for component: " + component.getClientId());
		//if (value == null || value.equals("")) {
		
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return "";
		}
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		
		if (value == null) {
			/*if (component instanceof SelectOneMenu) {
				log.debug("getAsString Converter for component: " + component.getClientId() + " is null!");
				currentContext.execute("validationResultCb('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
				//throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
				//return null;
			}*/
			return "";
		}
		
		/*if (component instanceof SelectOneMenu) {
			currentContext.execute("validationResultCb('" + component.getClientId() + "', '" + true + "', '" + getFromBundleMsgs("field_is_required") + "');");
		}*/

		if (currentContext == null || component == null || component.getClientId() == null) {
			return "";
		}
		if (value instanceof Long) {
			try {
				Long.parseLong(value.toString());
			} catch(NumberFormatException exception) {
				
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}

		}
		return String.valueOf(value);  
	}


}
