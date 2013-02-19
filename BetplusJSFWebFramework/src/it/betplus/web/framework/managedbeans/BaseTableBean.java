package it.betplus.web.framework.managedbeans;

import it.betplus.web.framework.exceptions.DataLayerException;
import it.betplus.web.framework.export.ExcelExporter;
import it.betplus.web.framework.utils.Constants;

import java.io.Serializable;  
import java.util.ArrayList;  
import java.util.List;  

import javax.faces.model.ListDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
  
public abstract class BaseTableBean extends GeneralBean implements Serializable  {  
	
	private static final long serialVersionUID = 4561952504914117949L;
	
	protected static final Log log = LogFactory.getLog(BaseTableBean.class);
	
	private int selectionType; // type of table selection row (single - multiple)
	
	// to be ovverridden
	protected List<Object> tableList;            // list of objects in table (rows) no get & set
	protected Object[]     selectedObjs;         // objects selected in multiple mode no get & set
	protected Object       selectedObj;          // object selected in single mode no get & set
	protected ListDataModel<Object> dataModel;   // data model object
    
	protected ExcelExporter excelExporter;
	
    public BaseTableBean() {  
    
    	tableList = new ArrayList<Object>();  
    	selectionType = Constants.TABLE_SELECTION_SINGLE;
    	excelExporter = new ExcelExporter();
		        
    }  
      
    // populate table data with params or not (passing wmpty list or null)
    public void populateTable(ArrayList<Object> params) {
        
    	try {
    	
    		// reset bean variables
    		resetAllValues();
    	
    		setListDataFromSource(params);
    			
    	} catch(DataLayerException dle) {
			sendErrorMessageToUser(getFromBundleMsgs("error_type_data"), getFromBundleMsgs("error_noDataRetrieved"));
			log.error("Unhandled exception ", dle); 
		} catch(Exception e) {
			sendErrorMessageToUser(getFromBundleMsgs("error_type_system"), getFromBundleMsgs("error_contactAdmin"));
			log.error("Unhandled exception ", e); 
		}	
    		
    }
    
    // retrieve data from source (DB or others) with param
    public abstract void setListDataFromSource(ArrayList<Object> params) throws DataLayerException;
    
    // reset value of tableBean
    public void resetAllValues() {
    	
    	selectedObjs = null;
    	selectedObj = null;
    	tableList = new ArrayList<Object>();  
    	
    }
    
    

    // get & set
	public void setSelectedObj(Object selectedObj) {
		this.selectedObj = selectedObj;
	}

	public int getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(int selectionType) {
		this.selectionType = selectionType;
	}

	public ExcelExporter getExcelExporter() {
		return excelExporter;
	}

	public void setExcelExporter(ExcelExporter excelExporter) {
		this.excelExporter = excelExporter;
	}

}  
