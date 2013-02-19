package it.betplus.web.framework.validator;

import it.betplus.web.framework.managedbeans.GeneralBean;
import it.betplus.web.framework.utils.Validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class ValidatorCommon extends GeneralBean {

	private Integer minimum;
	private Integer maximum;
	private Long 	maximumValue;
	private Long 	minimumValue;
	private Double 	maximumDoubleValue;
	private Double	minimumDoubleValue;
	private Integer	decimalPlaces;
	private	Integer minimunAge;
	private Boolean	unsigned;
	
	
	public void SetMinMaxValues (UIComponent component) {
		String minimumLength = (String)component.getAttributes().get("minimumLength");
		String maximumLength = (String)component.getAttributes().get("maximumLength");
		String minimumVal = (String)component.getAttributes().get("minimumValue");
		String maximumVal = (String)component.getAttributes().get("maximumValue");
		String minimumDblVal = (String)component.getAttributes().get("minimumDoubleValue");
		String maximumDblVal = (String)component.getAttributes().get("maximumDoubleValue");
		String decimalPlacesVal = (String)component.getAttributes().get("decimalPlaces");
		String mminAge = (String)component.getAttributes().get("minimunAge");
		String unsignedVal = (String)component.getAttributes().get("unsigned");

		minimum = 0;
		if (minimumLength != null) {
			minimum = Integer.valueOf(minimumLength);
		}
		
		maximum = 0;
		if (maximumLength != null) {
			maximum = Integer.valueOf(maximumLength);
		}
		
		minimumValue = 0L;
		if (minimumVal != null) {
			minimumValue = Long.valueOf(minimumVal);
		}
		
		maximumValue = 0L;
		if (maximumVal != null) {
			maximumValue = Long.valueOf(maximumVal);
		}
		
		minimumDoubleValue = 0d;
		if (minimumDblVal != null) {
			minimumDoubleValue = Double.valueOf(minimumDblVal);
		}
		
		maximumDoubleValue = 0d;
		if (maximumDblVal != null) {
			maximumDoubleValue = Double.valueOf(maximumDblVal);
		}
		
		decimalPlaces = 0;
		if (decimalPlacesVal != null) {
			decimalPlaces = Integer.valueOf(decimalPlacesVal);
		}
		
		minimunAge = 0;
		if (mminAge != null) {
			minimunAge = Integer.valueOf(mminAge);
		}
		
		unsigned = false;
		if (unsignedVal != null) {
			unsigned = Boolean.valueOf(unsignedVal);
		}
	}
	
	public boolean isRequired(FacesContext context, UIComponent component, Object value) {
		return minimum > 0 && convertToString(value).isEmpty();
		
	}
	
	public boolean chekLength(FacesContext context, UIComponent component, Object value) {
		return (minimum > 0 && !Validators.checkStringMinMaxLength(convertToString(value), minimum, maximum));
	}

	private String convertToString (Object value) {
		String strValue = "";
		if (value instanceof String) {
			strValue = (String) value;
		}else {
			strValue = value.toString();
		}
		return strValue; 
	}
	
	public Integer getMinimum() {
		return minimum;
	}

	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}

	public Integer getMaximum() {
		return maximum;
	}

	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}

	public Long getMaximumValue() {
		return maximumValue;
	}

	public void setMaximumValue(Long maximumValue) {
		this.maximumValue = maximumValue;
	}

	public Long getMinimumValue() {
		return minimumValue;
	}

	public void setMinimumValue(Long minimumValue) {
		this.minimumValue = minimumValue;
	}

	public Double getMaximumDoubleValue() {
		return maximumDoubleValue;
	}

	public void setMaximumDoubleValue(Double maximumDoubleValue) {
		this.maximumDoubleValue = maximumDoubleValue;
	}

	public Double getMinimumDoubleValue() {
		return minimumDoubleValue;
	}

	public void setMinimumDoubleValue(Double minimumDoubleValue) {
		this.minimumDoubleValue = minimumDoubleValue;
	}

	public Integer getDecimalPlaces() {
		return decimalPlaces;
	}

	public void setDecimalPlaces(Integer decimalPlaces) {
		this.decimalPlaces = decimalPlaces;
	}

	public Integer getMinimunAge() {
		return minimunAge;
	}

	public void setMinimunAge(Integer minimunAge) {
		this.minimunAge = minimunAge;
	}

	public Boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(Boolean unsigned) {
		this.unsigned = unsigned;
	}

}
