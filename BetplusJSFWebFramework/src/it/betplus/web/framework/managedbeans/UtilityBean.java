package it.betplus.web.framework.managedbeans;

import it.betplus.web.framework.utils.Constants;
import it.betplus.web.framework.utils.DateUtils;
import it.betplus.web.framework.utils.Utils;

import java.io.Serializable;
import java.util.Date;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

public class UtilityBean extends GeneralBean implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -4143465889828834062L;

	public UtilityBean() {
		
	}
	
	public String getDateFormatted(Date dateToFormat, String formatPattern) {
		
		if (dateToFormat != null)
			return DateUtils.dateToString(dateToFormat, formatPattern);
		else 
			return null;
		
	}
	
	public String getAmountFormatted(Double amount, String symbol, boolean returnEmptyString) {
		
		String formattedAmount;
		
		if(amount != null && amount != 0.0) {
			
			formattedAmount = Utils.formatCurrency(amount, symbol + " " + Constants.CURRENCY_STRING_LOCAL_FORMAT);		
			
		} else {
			
			if(returnEmptyString)
				formattedAmount = "";
			else
				formattedAmount = Utils.formatCurrency(new Double(0), symbol + " " + Constants.CURRENCY_STRING_LOCAL_FORMAT);
					
		}
	
		return formattedAmount;	
		
	}

	public String getDoubleFromIntegerFormatted(Integer amount, String symbol) {
		
		Double doubleAmount = amount / 100.0;
		
		return getAmountFormatted (doubleAmount, symbol, false);
	}
	
	public String getMessage(String msg) {
		return getFromBundleMsgs(msg);
	}
	
	/* return numeric value SPELLED in words
	* ULocale.US       US english
	* ULocale.ITALIAN  Italian
	* ULocale.ENGLISH  English
	*/
	public String convertNumberToWords(String toConvert, ULocale selectedLocale, boolean showWordSeparator) throws Exception {
		
		RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(selectedLocale, RuleBasedNumberFormat.SPELLOUT);		
		String retValue = rbnf.format(Double.parseDouble(toConvert));
			
		if(!showWordSeparator)
			return retValue.replaceAll("­", " ");
		
		return retValue;
		
	}
	
	public String convertNumberToWordsAndCents(String toConvertDoubleValue, String litteralSeparator, String litteralCurrencyName, ULocale selectedLocale, boolean showWordSeparator, String centsWord, boolean upperCase) throws Exception {
		
		String retValue = "";
		
		// split value with separator
		String[] splitConvert = toConvertDoubleValue.split("\\.");
		
		RuleBasedNumberFormat rbnf = new RuleBasedNumberFormat(selectedLocale, RuleBasedNumberFormat.SPELLOUT);		
		retValue = rbnf.format(Double.parseDouble(splitConvert[0]));

		if(splitConvert.length > 1 && !(splitConvert[1].equals("00") || splitConvert[1].equals("0"))) {
			
			retValue +=  " " + litteralCurrencyName + " " + litteralSeparator + " " + rbnf.format(Double.parseDouble(splitConvert[1])) + " " + centsWord;
				
		} else {
			
			retValue += " " + litteralCurrencyName + " " + litteralSeparator + " NO " + centsWord;
			
		}
		
		if(!showWordSeparator)
			retValue = retValue.replaceAll("­", " ");
		
		if(upperCase)	
			return retValue.toUpperCase();
		
		return retValue;
		
	}
	
	public String multipliAndFormatCurrency(Double amount, String symbol, boolean returnEmptyString, int quantity) {
		
		Double amountTotal = amount * quantity;
		
		return getAmountFormatted (amountTotal, symbol, returnEmptyString);
		
	}
	
}
