package it.betplus.web.framework.converter;

import it.betplus.web.framework.managedbeans.GeneralBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.primefaces.context.RequestContext;

//@FacesConverter("it.betplus.moneyphone.convertor.DoubleConverter")

public class DoubleConverter extends GeneralBean implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		RequestContext currentContext = RequestContext.getCurrentInstance();
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		Double rValue = null;
		if (value.trim().equals("")) {
			return value;
		} else {
			try {

				rValue = Double.parseDouble(value);

			} catch(NumberFormatException exception) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_with_decimal") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_with_decimal")));
			}
		}

		return rValue;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null || value.equals("")) {
			return "";
		}
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null || component == null || component.getClientId() == null) {
			return "";
		}
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		if (value instanceof Double) {
			try {
				Double.parseDouble(value.toString());
			} catch(NumberFormatException exception) {
				
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_with_decimal") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_with_decimal")));
			}

		}
		return String.valueOf(value);  
	}


}
