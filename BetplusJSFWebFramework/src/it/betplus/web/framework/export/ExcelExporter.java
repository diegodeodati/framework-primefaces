package it.betplus.web.framework.export;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelExporter implements Serializable {

	private static final long serialVersionUID = -6297492059970090367L;

	ArrayList<Integer> excludedColumns;
	int columnCut=0;

	public ExcelExporter() {		
		super();
		excludedColumns = new ArrayList<Integer>();
		
	}

	public void postProcessXLS(Object document) {  
	    HSSFWorkbook wb = (HSSFWorkbook) document;  
	    HSSFSheet sheet = wb.getSheetAt(0);  

	    HSSFRow header = sheet.getRow(0);  
	    
	    
	    	      
	    HSSFCellStyle styleHeader = wb.createCellStyle();   
	    styleHeader.setBorderBottom((short)1);
	    styleHeader.setBorderTop((short)1);
	    styleHeader.setBorderLeft((short)1);
	    styleHeader.setBorderRight((short)1);
	    styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    styleHeader.setFillForegroundColor(HSSFColor.BLUE.index);  
	    styleHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFCellStyle styleInternalCell = wb.createCellStyle();   
	    styleInternalCell.setBorderBottom((short)1);
	    styleInternalCell.setBorderTop((short)1);
	    styleInternalCell.setBorderLeft((short)1);
	    styleInternalCell.setBorderRight((short)1);
	    
	    HSSFCellStyle styleColumnCell = wb.createCellStyle();   
	    styleColumnCell.setBorderBottom((short)1);
	    styleColumnCell.setBorderTop((short)1);
	    styleColumnCell.setBorderLeft((short)1);
	    styleColumnCell.setBorderRight((short)1);
	    styleColumnCell.setFillForegroundColor(HSSFColor.AQUA.index);  
	    styleColumnCell.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    
	    HSSFFont font = wb.createFont();
	    font.setFontName(HSSFFont.FONT_ARIAL);
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    font.setColor(HSSFColor.WHITE.index);
	    styleHeader.setFont(font);
	    
	    sheet.createFreezePane(0,1);
	    sheet.createFreezePane(columnCut, 1);
		    
	    for(int i=0; i < header.getPhysicalNumberOfCells();i++) {  
	        HSSFCell cell = header.getCell(i);  
	        cell.setCellStyle(styleHeader);
	        sheet.autoSizeColumn(i);
	    }
	    
	    System.out.println("Column Cutt: "+columnCut);
	    System.out.println("Not Numeric columns: "+excludedColumns);
	    
	    for(int i=1; i < sheet.getPhysicalNumberOfRows();i++){
	    	HSSFRow actRow = sheet.getRow(i);  
	    	for(int j=0; j < actRow.getPhysicalNumberOfCells();j++) {  
	    		
	    		HSSFCell cell = actRow.getCell(j);  
	    		cell.setCellStyle(styleInternalCell);
	    		
	    		if(j<columnCut)		        
		        cell.setCellStyle(styleColumnCell);
		        
		        
		        if(!excludedColumns.contains(j)){
		        	cell.setCellStyle(styleInternalCell);
		        	String app = cell.getStringCellValue().replace(".","");	
		        	cell.setCellValue(Double.parseDouble(app.replace(",",".")));
		        }
		        
		    }
	    }    
	    
	}

	public ArrayList<Integer> getExcludedColumns() {
		return excludedColumns;
	}

	public void setExcludedColumns(ArrayList<Integer> excludedColumns) {
		this.excludedColumns = excludedColumns;
	}


	public void setNotNumericColumns(String listOfNotNumericColumns){
		excludedColumns= new ArrayList<Integer>();
		String[] columnList = listOfNotNumericColumns.split("\\|");
		
		for(int i=0;i<columnList.length;i++){
			excludedColumns.add(Integer.parseInt(columnList[i])-1);			
		}
				
	}
	
	public int getColumnCut() {
		return columnCut;
	}

	public void setColumnCut(String columnCut) {
		int c = Integer.parseInt(columnCut);		
		this.columnCut = c;
	}
	
}
