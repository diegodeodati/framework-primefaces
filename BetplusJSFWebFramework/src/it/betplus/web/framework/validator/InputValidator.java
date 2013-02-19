package it.betplus.web.framework.validator;

import it.betplus.web.framework.managedbeans.GeneralBean;
import it.betplus.web.framework.utils.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

public class InputValidator extends GeneralBean implements Validator {

	public InputValidator () {
		super();
	}

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String strValue = "";
		if (value instanceof String) {
			strValue = (String) value;
			
		}else if (value instanceof Integer) {
			strValue = value.toString();
		}
		
		String validationType = (String)component.getAttributes().get("validationType");
		String minimumLength = (String)component.getAttributes().get("minimumLength");
		String maximumLength = (String)component.getAttributes().get("maximumLength");
		 
		Integer minChars = 0;
		if (minimumLength != null) {
			minChars = Integer.valueOf(minimumLength);
		}
		
		Integer maxChars = 0;
		if (maximumLength != null) {
			maxChars = Integer.valueOf(maximumLength);
		}
		
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return;
		}
		//currentContext.update("editData");
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		if(minChars > 0 && strValue.isEmpty()) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (minChars > 0 && !Validators.checkStringMinMaxLength(strValue, minChars, maxChars)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_minimum_maximum"), minChars, maxChars) + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_minimum_maximum"), minChars, maxChars)));
		}
		
		if (validationType != null && validationType.equalsIgnoreCase("AlphaNumericNoWhiteSpace")) {
			if (!Validators.checkAlphaNumericNoWhiteSpaces(strValue)) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_alpha_numeric_only") + ", " + getFromBundleMsgs("field_does_not_allow_spaces") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_alpha_numeric_only") + ", " + getFromBundleMsgs("field_does_not_allow_spaces")));
			}
		}
		
		if (validationType != null && validationType.equalsIgnoreCase("AlphaNumericWhiteSpace")) {
			if (!Validators.checkAlphaNumericWhiteSpaces(strValue)) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_alpha_numeric_only") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_alpha_numeric_only")));
			}
		}
		
		if (validationType != null && validationType.equalsIgnoreCase("NumericOnly")) {
			if (!Validators.checkNumericOnly(strValue)) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}
		}
		
		if (validationType != null && validationType.equalsIgnoreCase("CellularNumber")) {
			if (!Validators.checkCellularNumber(strValue)) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_phone_number_format") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_phone_number_format")));
			}
		}
		
		
		/*if (!Validators.checkStringOnlyLitteral(strValue, minChars, maxChars)) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_does_not_allow_spaces") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_does_not_allow_spaces")));
			}*/
		
	}

}
