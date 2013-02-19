package it.betplus.web.framework.validator;

import it.betplus.web.framework.utils.Validators;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

//@FacesValidator("it.betplus.moneyphone.validators.BirthDayValidator")

public class BirthDayValidator extends ValidatorCommon implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		SetMinMaxValues(component);
		
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return;
		}
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_input");
		currentContext.update(component.getClientId() + "_input_msg");
		
		if(value == null || isRequired(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "_input" + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (!Validators.checkBirthDateMajorAge((Date) value, getMinimunAge())) {
			currentContext.execute("validationResult('" + component.getClientId() + "_input" + "', '" + false + "', '" + String.format(getFromBundleMsgs("invalid_person_birth_date"), getMinimunAge()) + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("invalid_person_birth_date"), getMinimunAge())));
		}
	}

}
