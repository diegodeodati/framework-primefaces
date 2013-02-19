package it.betplus.web.framework.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Utils {
	
	/*
	 * Format the Double value into a String with a curerncy format
	 * Es: formatCurrency(7.00, "€ -0.00") --> € -7,00
	 * Es: formatCurrency(54.20, "$ 0,00") --> $ 54.20
	 */
	public static String formatCurrency(Double value, String format) {
		
		return new DecimalFormat(format).format(value);
		
	}
	
	// round decimal to format e.s. "#.##"
	public static double roundDecimals(double decimalToRound, int decimalPlaces) {
    
	    BigDecimal bd = new BigDecimal(decimalToRound);
	    bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_UP);
	    
	    return bd.doubleValue();
	
	}
   
}
