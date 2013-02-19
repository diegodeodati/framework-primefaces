package it.betplus.web.framework.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

//@FacesValidator("it.betplus.moneyphone.validators.UnsignedLongValidator")

public class UnsignedLongValidator extends ValidatorCommon implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		/*if (component instanceof SelectOneMenu) {
			log.debug("validating .. " + component.getClientId());
		}*/
		SetMinMaxValues(component);
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return;
		}
		currentContext.update(component.getClientId());
		currentContext.update(component.getClientId() + "_msg");
		
		//TODO: for some reason convertor doesnot work properly, perhaps because i am updating the child component via ajax
		if (component instanceof SelectOneMenu && value == null) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}
		
		if(isRequired(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (chekLength(context, component, value)) {
			currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum()) + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum())));
		}
		
		if (!value.toString().isEmpty()) {
			try {
				Long longValue = Long.valueOf(value.toString());
				if (longValue < 0) {
					currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_must_be_greater_than_zero") + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_must_be_greater_than_zero")));
				}
				
				if (getMinimumValue() != 0 && longValue < getMinimumValue()) {
					currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue(), getMaximumValue()) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue(), getMaximumValue())));
				}
				
				if (getMaximumValue() > 0 && longValue > getMaximumValue()) {
					currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue(), getMaximumValue()) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue(), getMaximumValue())));
				}
				
			}catch(NumberFormatException exception) {
				currentContext.execute("validationResult('" + component.getClientId() + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}
		}
	}

}
