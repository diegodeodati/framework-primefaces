package it.betplus.web.framework.managedbeans;

import it.betplus.web.framework.messages.Messages;

import java.io.Serializable;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class LanguageBean extends GeneralBean implements Serializable {
	
	private static final long serialVersionUID = -5613449192333828555L;
	
	public Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	public String selectedLanguage;
	public String datePatternSelected;
	public String dateTimePatternSelected;
	public String monthYearPatternSelected;
	public char 	localDecimalSeparator;
	public char 	localThousandSeparator;
    protected List<SelectItem> languages;
    
	public LanguageBean() {
		
		// generate languages combo values
		languages = new ArrayList<SelectItem>();
		languages.add(new SelectItem("en", "English"));
		languages.add(new SelectItem("it", "Italiano"));
		
		setLocaleDefault();
		
	}

	/* action event listener on combo language*/ 
	public void changeLanguage() {
		
		Locale newLocale = new Locale(selectedLanguage);
		Messages.setViewRootLocale(newLocale);
		Messages.getSessionMap().put("currentLocale", newLocale);
		this.locale = newLocale;
		selectedLanguage = this.locale.getLanguage();
		datePatternSelected = getFromBundleConfig("pattern_date_" + selectedLanguage);
		dateTimePatternSelected = getFromBundleConfig("pattern_date_" + selectedLanguage) + " " + getFromBundleConfig("pattern_time");
		monthYearPatternSelected = getFromBundleConfig("pattern_month_year_" + selectedLanguage);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(this.locale);
		localDecimalSeparator = symbols.getDecimalSeparator();
		localThousandSeparator = symbols.getGroupingSeparator();
	}
	
	private void setLocaleDefault() {
		
		if (Messages.getSessionMap().get("currentLocale") == null) {
			Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
			Messages.getSessionMap().put("currentLocale", locale);
			Messages.setViewRootLocale(locale);
			this.locale = locale;
			selectedLanguage = locale.getLanguage();
		} else {
			Messages.setViewRootLocale((Locale) Messages.getSessionMap().get("currentLocale"));
			this.locale = (Locale) Messages.getSessionMap().get("currentLocale");
		//	selectedLanguage = locale.getDisplayLanguage();
			selectedLanguage = locale.getLanguage();
		}
		
		datePatternSelected = getFromBundleConfig("pattern_date_" + selectedLanguage);
		dateTimePatternSelected = getFromBundleConfig("pattern_date_" + selectedLanguage) + " " + getFromBundleConfig("pattern_time");
		monthYearPatternSelected = getFromBundleConfig("pattern_month_year_" + selectedLanguage);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(this.locale);
		localDecimalSeparator = symbols.getDecimalSeparator();
		localThousandSeparator = symbols.getGroupingSeparator();
	}
	
	public void setDefaultLanguage() {
		
		Messages.getSessionMap().put("currentLocale", null);
		
		setLocaleDefault();
		changeLanguage();
		
	}
	
	// get default locale decimal separator
	public String getDecimalSeparator() {
		
		return Character.toString((new DecimalFormatSymbols(Locale.getDefault())).getDecimalSeparator());
		
	}
	
	public String getTimePattern() {
		
		//return datePatternSelected + " " + getFromBundleConfig("pattern_time");
		return getFromBundleConfig("pattern_time");
		
	}
	
	public String getTimeMinutesPattern() {
		return getFromBundleConfig("pattern_time_minute");
	}
	
	public Locale getItalianLocale(){
		return new Locale("it-IT");		
	}
	
	/*public char getThousandSeparator() {
		return (new DecimalFormatSymbols(this.locale)).getGroupingSeparator();
	}*/

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getSelectedLanguage() {
		return selectedLanguage;
	}

	public void setSelectedLanguage(String selectedLanguage) {
		this.selectedLanguage = selectedLanguage;
	}

	public List<SelectItem> getLanguages() {
		return languages;
	}

	public void setLanguages(List<SelectItem> languages) {
		this.languages = languages;
	}

	public String getDatePatternSelected() {
		return datePatternSelected;
	}

	public void setDatePatternSelected(String datePatternSelected) {
		this.datePatternSelected = datePatternSelected;
	}

	public String getDateTimePatternSelected() {
		return dateTimePatternSelected;
	}

	public void setDateTimePatternSelected(String dateTimePatternSelected) {
		this.dateTimePatternSelected = dateTimePatternSelected;
	}

	public String getMonthYearPatternSelected() {
		return monthYearPatternSelected;
	}

	public void setMonthYearPatternSelected(String monthYearPatternSelected) {
		this.monthYearPatternSelected = monthYearPatternSelected;
	}

	public char getLocalDecimalSeparator() {
		return localDecimalSeparator;
	}

	public void setLocalDecimalSeparator(char localDecimalSeparator) {
		this.localDecimalSeparator = localDecimalSeparator;
	}

	public char getLocalThousandSeparator() {
		return localThousandSeparator;
	}

	public void setLocalThousandSeparator(char localThousandSeparator) {
		this.localThousandSeparator = localThousandSeparator;
	}

}
