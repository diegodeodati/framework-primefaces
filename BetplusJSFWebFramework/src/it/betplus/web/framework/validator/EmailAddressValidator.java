package it.betplus.web.framework.validator;

import it.betplus.web.framework.utils.Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

//@FacesValidator("it.betplus.moneyphone.validators.EmailAddressValidator")

public class EmailAddressValidator extends ValidatorCommon implements Validator {

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
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (chekLength(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum()) + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum())));
		}
		
		if (!Validators.checkEmailAddress((String) value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("invalid_email_address_format") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("invalid_email_address_format")));
		}
	}
}
