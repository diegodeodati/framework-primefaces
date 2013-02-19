package it.betplus.web.framework.frontend.beans;

import java.io.Serializable;

public class BeanBase extends GenericFrontendBean implements Serializable {
	
	private static final long serialVersionUID = 5662569832366928997L;

	public BeanBase() {}
	
	public BeanBase(Object dto, boolean useReflection) {
		
		try {
			
			if(useReflection) {
			
				beanReflectionFromDto(dto);		
		  
			} 
			    
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}	