package it.betplus.web.framework.converter;

import it.betplus.web.framework.managedbeans.GeneralBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.primefaces.context.RequestContext;

public class DateConverter extends GeneralBean implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		RequestContext currentContext = RequestContext.getCurrentInstance();
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		Date dValue = null;
		if (value.trim().equals("")) {
			return value;
		} else {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dValue = dateFormat.parse(value);
			} catch (ParseException e) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("invalid_date") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("invalid_date")));
			}
		}

		return dValue;
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
		if (value instanceof Date) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			try {
				dateFormat.format(value);
			} catch(Exception exception) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("invalid_date") + "');");
				throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("invalid_date")));
			}

		}
		return String.valueOf(value);  
	}

}
