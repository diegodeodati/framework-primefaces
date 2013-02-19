package it.betplus.web.framework.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

	public static final String NON_NEGATIVE_MONEY_FIELD =  "(\\d){1,15}(\\.(\\d){2})?";
	private static final String NON_NEGATIVE_INTEGER_FIELD = "(\\d){1," + Constants.MAX_LEFT_AMOUNT_DIGITS + "}";
	private static final String STRING_ONLY_LITTERAL = "[a-zA-Z]+\\.?";
	private static final String STRING_LITTERAL_SPACE = "[a-zA-Z ]+\\.?";
	private static final String CELLULAR_NUMBER = "[+]?[0-9]{7,15}";
	private static final String MAC_ADDRESS = ("[((([0-9a-fA-F]){1,2}[-:]?){1,5}([0-9a-fA-F]){1,2})]{12,17}");
	private static final String ALPHA_NUMERIC_NO_WHITESPACE = "(\\w)(\\S*)";
	private static final String ALPHA_NUMERIC_WHITESPACE = "(\\w.*)";
	private static final String ANY_CHARACTER_NO_WHITESPACE = "(\\S+)";
	private static final String NUMERIC_ONLY = "(^[-+]){0,1}(\\d+)";
	private static final String IP_ADDRESS = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static final String EMAIL_ADDRESS = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-+]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String STRING_NUMERIC_ONLY = "((-|\\+){0,1}[0-9]{1,})";
	
	// validate string have value and not terminate with separator , . and amount > 0
	public static boolean validateFormattedAmount(String formattedAmount, String currencySeparator, boolean noDecimalPart) {
		
		boolean result = false;
		
		if(formattedAmount != null && !formattedAmount.equals("")) {
			
			result = true;
								
			// check if value terminate with separator
			if(formattedAmount.substring(formattedAmount.length() - 1).equals(currencySeparator)) {
				
				return false;
				
			} 
			
			// check if amount contains copied not numer or currency separator values
			if(!validateMoneyAmount(formattedAmount, currencySeparator, Constants.MAX_LEFT_AMOUNT_DIGITS)) {
				
				return false;
				
			}
				
			// check if Double value is > 0
			if(getAmountFromString(formattedAmount).doubleValue() <= 0) {
				
				return false;
				
			} 		
			
			// if is activated chek no decimal part
			if(noDecimalPart) {
				
				// split at currencySeparator
				String[] splittedAmount = formattedAmount.split(currencySeparator);
				
				if(splittedAmount.length > 1) {
					if(!splittedAmount[1].equals("00"))
						return false;
				}
				
			}
			
		}
		
		return result;
		
	}

	public static Double getAmountFromString(String formattedAmount) {
		
		return new Double(formattedAmount.replaceAll(",", "."));
		
	}
	
	// check if amount is in format xxxxx.xx by separator
	public static boolean validateMoneyAmount(String formattedAmount, String currencySeparator, int maxLeftLength) {

		// check decimal part
		if(formattedAmount.indexOf(currencySeparator) != -1) {
			
			if(checkLeftPart(formattedAmount, currencySeparator, maxLeftLength) && checkDecimalDigits(formattedAmount, currencySeparator))
				return checkMoney(formattedAmount.replaceAll(",", "."));
			else
				return false;
			
		} else {
			
			// check max length of not decimal part
			if(formattedAmount.length() <= Constants.MAX_LEFT_AMOUNT_DIGITS && checkIntegerValue(formattedAmount))
				return checkMoney(formattedAmount += ".00");
			else 
				return false;
			
		}

	}
	
	public static boolean checkMoney(String value) {
	
		Pattern pattern = Pattern.compile(NON_NEGATIVE_MONEY_FIELD);
		Matcher matcher = pattern.matcher(value);
 
		if (matcher.matches())
			return true;
		else 
			return false;
		
	}
	
	public static boolean checkIntegerValue(String value) {
		
		if(value != null && !value.equals("")) {
			
			Pattern pattern = Pattern.compile(NON_NEGATIVE_INTEGER_FIELD);
			Matcher matcher = pattern.matcher(value);
		 
			if (matcher.matches())
		    	return true;
		    else 
		    	return false;
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean checkDecimalDigits(String amount, String currencySeparator) {
		
		String decimalPart = amount.substring(amount.indexOf(currencySeparator) + 1, amount.length());
		
		if (decimalPart.length() == 2)
	    	return true;
	    else 
	    	return false;
		
	}
	
	public static boolean checkLeftPart(String amount, String currencySeparator, int maxLeftLength) {
		
		String leftPart = amount.substring(0, amount.indexOf(currencySeparator));
		
		if (leftPart.length() <= maxLeftLength && checkIntegerValue(leftPart))
	    	return true;
	    else 
	    	return false;
		
	}
	
	public static boolean checkStringMinMaxLength(String toCheck, int minLength, int maxLength) {
		
		if(toCheck != null && !toCheck.equals("")) {
		
			if(toCheck.length() >= minLength) {
				
				if(maxLength > 0) {
					
					if(toCheck.length() <= maxLength) 
						return true;
					else
						return false;
					
				} else {
					
					return true;
					
				}
				
			} else {
				
				return false;
				
			}

		} else {
			
			return false;
			
		}
				
	}
	
	public static boolean checkStringOnlyLitteral(String toCheck, int minLength, int maxLength) {
		
		if(toCheck != null && !toCheck.equals("")) {
			
			if(checkStringMinMaxLength(toCheck, minLength, maxLength)) {
				
				Pattern pattern = Pattern.compile(STRING_ONLY_LITTERAL);
				Matcher matcher = pattern.matcher(toCheck);
			 
				if (matcher.matches())
			    	return true;
			    else 
			    	return false;
				
			} else {
				
				return false;
				
			}		
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean checkStringLitteralSpace(String toCheck, int minLength, int maxLength) {
		
		if(toCheck != null && !toCheck.equals("")) {
			
			if(checkStringMinMaxLength(toCheck, minLength, maxLength)) {
				
				Pattern pattern = Pattern.compile(STRING_LITTERAL_SPACE);
				Matcher matcher = pattern.matcher(toCheck);
			 
				if (matcher.matches())
			    	return true;
			    else 
			    	return false;
				
			} else {
				
				return false;
				
			}		
			
		} else {
			
			return false;
			
		}
		
	}
	
	public static boolean checkBirthDateMajorAge(Date toChek, int yearsToCheck) {
		
		if(toChek != null) {
			
			Date now = new Date();	
			toChek = DateUtils.addYears(toChek, 18);
			return !DateUtils.isDateAfter(toChek, now);
			/*if( DateUtils.isDateAfter(toChek, now) )
				return false;
			else
				return true;*/				
			
		} else {
			return true;
		} 
	}
	
	public static boolean validateMoneyAmountGreaterThanZero(String formattedAmount, String currencySeparator, int maxLeftLength) {
		boolean validated = true;
		//boolean validated = validateMoneyAmount(formattedAmount, currencySeparator, maxLeftLength);
		//if (validated) {
			if (getAmountFromString(formattedAmount) <= 0) {
				validated = false;
			}
		//}
		return validated;
	}
	
	public static boolean checkCellularNumber(String value) {
		
		if(value != null && !value.equals("")) {
			
			Pattern pattern = Pattern.compile(CELLULAR_NUMBER);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		} else {
			
			return true;
			
		}
		
	}
	
	public static boolean checkMacAddress(String value) {
		value = value.replaceAll(":", "").replaceAll("-", "");
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(MAC_ADDRESS);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		} else {
			
			return true;
			
		}
	}
	
	public static boolean checkAlphaNumericNoWhiteSpaces(String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(ALPHA_NUMERIC_NO_WHITESPACE);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkAlphaNumericWhiteSpaces(String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(ALPHA_NUMERIC_WHITESPACE);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkNumericOnly(String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(NUMERIC_ONLY);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkIpAddress (String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(IP_ADDRESS);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkEmailAddress (String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(EMAIL_ADDRESS);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkAnyCharacterNoWhiteSpace (String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(ANY_CHARACTER_NO_WHITESPACE);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
	public static boolean checkStringNumericOnly (String value) {
		if(value != null && !value.equals("")) {
			Pattern pattern = Pattern.compile(STRING_NUMERIC_ONLY);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}else {
			return true;
		}
	}
	
}
