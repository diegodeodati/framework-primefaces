package it.betplus.web.framework.converter;

import it.betplus.web.framework.managedbeans.GeneralBean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;

//@FacesConverter("it.betplus.moneyphone.convertor.IntegerConverter")

public class IntegerConverter extends GeneralBean implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (component instanceof InputNumber) {
			currentContext.update(component.getClientId() + "_input");
		}else {
			currentContext.update(component.getClientId());
		}
		currentContext.update(component.getClientId() + "_msg");
		Integer rValue = null;
		if (value.trim().equals("") && component instanceof InputNumber) {
			currentContext.execute("validationResult('" + component.getClientId() + "_input" + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
		}else
			if (value.trim().equals("")) {
				return value;
			} else {
				try {

					rValue = Integer.parseInt(value);

				} catch(NumberFormatException exception) {
					currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
					throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
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
		if (component instanceof InputNumber) {
			currentContext.update(component.getClientId() + "_input");
		}else {
			currentContext.update(component.getClientId());
		}
		currentContext.update(component.getClientId() + "_msg");
		if (value instanceof Integer) {
			try {
				Integer.parseInt(value.toString());
			} catch(NumberFormatException exception) {
				if (component instanceof InputNumber) {
					currentContext.execute("validationResult('" + component.getClientId() + "_input ', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				}
				else {
					currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				}
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}

		}
		return String.valueOf(value);  
	}

}
