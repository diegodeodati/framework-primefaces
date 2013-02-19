package it.betplus.web.framework.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;

public class IntegerValidator extends ValidatorCommon implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		SetMinMaxValues(component);
		String componentName = component.getClientId();
		if (component instanceof InputNumber) {
			componentName = componentName + "_input";
		}
		RequestContext currentContext = RequestContext.getCurrentInstance();
		if (currentContext == null) {
			return;
		}
		
		currentContext.update(componentName);
	
		currentContext.update(component.getClientId() + "_msg");
		
		if(isRequired(context, component, value)) {
			currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + getFromBundleMsgs("field_is_required") + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_required")));
		}

		if (chekLength(context, component, value)) {
			currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum()) + "');");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_minimum_maximum"), getMinimum(), getMaximum())));
		}
		
		if (!value.toString().isEmpty()) {
			try {
				Integer intValue = 0;
				if (component instanceof InputNumber) {
					intValue = Double.valueOf(value.toString()).intValue();
				}else {
					intValue = Integer.valueOf(value.toString());
				}
				if (intValue < 0 && getUnsigned()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + getFromBundleMsgs("field_must_be_greater_than_zero") + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_must_be_greater_than_zero")));
				}
				
				if (getMinimumValue() != 0 && intValue < getMinimumValue()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue().toString(), getMaximumValue().toString()) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue().toString(), getMaximumValue().toString())));
				}
				
				if (getMaximumValue() > 0 && intValue > getMaximumValue()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue().toString(), getMaximumValue().toString()) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), getMinimumValue().toString(), getMaximumValue().toString())));
				}
				
			}catch(NumberFormatException exception) {
				currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_only") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_only")));
			}
		}
	}


}
