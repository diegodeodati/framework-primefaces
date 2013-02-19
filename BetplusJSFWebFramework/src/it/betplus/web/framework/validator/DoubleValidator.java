package it.betplus.web.framework.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;

public class DoubleValidator extends ValidatorCommon implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		SetMinMaxValues(component);
		RequestContext currentContext = RequestContext.getCurrentInstance();
		
		String componentName = component.getClientId();
		if (component instanceof InputNumber) {
			componentName = componentName + "_input";
		}
		
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
		Double doubleValue = 0d;
		if (!value.toString().isEmpty()) {
			try {
				doubleValue = Double.valueOf(value.toString());

				if (doubleValue < 0 && getUnsigned()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + getFromBundleMsgs("field_must_be_greater_than_zero") + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_must_be_greater_than_zero")));
				}
				
				/*if (getMinimumDoubleValue() > 0 && doubleValue < getMinimumDoubleValue()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_double_must_be_greater_than_value"), getMinimumDoubleValue(), getMaximumDoubleValue()) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_double_must_be_greater_than_value"), getMinimumDoubleValue(), getMaximumDoubleValue())));
				}*/
				
				if (getMinimumDoubleValue() != 0 && doubleValue < getMinimumDoubleValue()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), String.format("%3." + getDecimalPlaces().toString() + "f", getMinimumDoubleValue()), String.format("%3." + getDecimalPlaces().toString() + "f", getMaximumDoubleValue())) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), String.format("%3." + getDecimalPlaces().toString() + "f", getMinimumDoubleValue()), String.format("%3." + getDecimalPlaces().toString() + "f", getMaximumDoubleValue()))));
				}
				
				if (getMinimumDoubleValue() > 0 && doubleValue > getMaximumDoubleValue()) {
					currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + String.format(getFromBundleMsgs("field_out_of_range"), String.format("%3." + getDecimalPlaces().toString() + "f", getMinimumDoubleValue()), String.format("%3." + getDecimalPlaces().toString() + "f", getMaximumDoubleValue())) + "');");
					throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", String.format(getFromBundleMsgs("field_out_of_range"), String.format("%3." + getDecimalPlaces().toString() + "f", getMinimumDoubleValue()), String.format("%3." + getDecimalPlaces().toString() + "f", getMaximumDoubleValue()))));
				}
				
			}catch(NumberFormatException exception) {
				currentContext.execute("validationResult('" + componentName + "', '" + false + "', '" + getFromBundleMsgs("field_is_numeric_with_decimal") + "');");
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fail", getFromBundleMsgs("field_is_numeric_with_decimal")));
			}
		}
		
		/*if (getFee() > 0) {
			Double fee = getFee() / 100d;
			fee = doubleValue * fee;
			currentContext.execute("setFieldDouble('"+ getFieldToUpdate() + "', '" + fee + "');");
			currentContext.update(getFieldToUpdate());
			
			//entityTransactionEdit.getTransaction().getTransactionFee().setFeeAmount((int) Math.round(fee * 100));
		}*/
	}

}
