package it.betplus.web.framework.frontend.beans;

import it.betplus.web.framework.utils.Constants;
import it.betplus.web.framework.utils.DateUtils;
import it.betplus.web.framework.utils.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

public class GenericFrontendBean {

	public void beanReflectionFromDto(Object dto) throws Exception {
		
		Class<?> c = this.getClass();
	    Field[] fields = c.getDeclaredFields();
	    
	    Class<?> dtoClass = dto.getClass();
	       
	    for(int i = 0; i < fields.length; i++) {
	    
	    	Field currentField = fields[i];
	    	currentField.setAccessible(true);

	    	// exclude static fields
	    	if (!Modifier.isStatic(currentField.getModifiers())) {
	    		
	    		Field dtoCurrentField;
	    		
	    		try {
	    			dtoCurrentField = dtoClass.getDeclaredField(currentField.getName());
	    		} catch(NoSuchFieldException nsfe){
	    			dtoCurrentField = null;
	    		}
	    		
	    		if(dtoCurrentField != null) {
	    			dtoCurrentField.setAccessible(true);
		    		currentField.set(this, dtoCurrentField.get(dto));		 
	    		}
	    		
	    	}
	    	
	    }
	    
	}
	
	public static Object dtoReflectionFromBean(Object bean, Object dtoNew) throws Exception {
		
		Class<?> c = dtoNew.getClass();
	    Field[] fields = c.getDeclaredFields();
	    
	    Class<?> beanClass = bean.getClass();
	       
	    for(int i = 0; i < fields.length; i++) {
	    
	    	Field currentField = fields[i];
	    	currentField.setAccessible(true);

	    	// exclude static fields
	    	if (!Modifier.isStatic(currentField.getModifiers())) {
	    		
	    		Field beanCurrentField;
	    		
	    		try {
	    			beanCurrentField = beanClass.getDeclaredField(currentField.getName());
	    		} catch(NoSuchFieldException nsfe){
	    			beanCurrentField = null;
	    		}
	    		
	    		if(beanCurrentField != null) {
	    			beanCurrentField.setAccessible(true);
		    		currentField.set(dtoNew, beanCurrentField.get(bean));		 
	    		}
	    		
	    	}
	    	
	    }
	    
	    return dtoNew;
	    
	}

	public String getDateFormatted(Date dateToFormat, String formatPattern) {
		
		if (dateToFormat != null)
			return DateUtils.dateToString(dateToFormat, formatPattern);
		else 
			return null;
		
	}
	
	public String getAmountFormatted(Double amount, String symbol) {
		
		String formattedAmount;
		String symbolToUse = "";
		
		if(symbol != null && !symbol.equals(""))
			symbolToUse = symbol + " ";
		
		if(amount != null && amount != 0.0) {
			
			formattedAmount = Utils.formatCurrency(amount, symbolToUse + Constants.CURRENCY_STRING_LOCAL_FORMAT);		
			
		} else {
			
			formattedAmount = Utils.formatCurrency(new Double(0), symbolToUse + Constants.CURRENCY_STRING_LOCAL_FORMAT);
					
		}
		
		return formattedAmount;	
		
	}

}
