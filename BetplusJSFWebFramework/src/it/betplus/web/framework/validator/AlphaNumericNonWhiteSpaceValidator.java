package it.betplus.web.framework.validator;

import it.betplus.web.framework.utils.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

//@FacesValidator("it.betplus.moneyphone.validators.AlphaNumericNonWhiteSpaceValidator")

public class AlphaNumericNonWhiteSpaceValidator extends ValidatorCommon implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		SetMinMaxValues(component);
		
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return;
		}
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		
		if(isRequired(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			//log.debug(String.format("field %s is required", component.getClientId()));
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (chekLength(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum()) + "');");
			//log.debug(String.format("invalid length for field %s", component.getClientId()));
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum())));
		}
		
		if (!Validators.checkAlphaNumericNoWhiteSpaces((String) value)) {
			//log.debug(String.format("invalid range for field %s", component.getClientId()));
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_alpha_numeric_only") + ", " + getFromBundleMsgs("field_does_not_allow_spaces") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_alpha_numeric_only") + ", " + getFromBundleMsgs("field_does_not_allow_spaces")));
		}
	}

}
