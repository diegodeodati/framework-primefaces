package it.betplus.web.framework.managedbeans;

import it.betplus.web.framework.frontend.beans.charts.BaseChart;
import it.betplus.web.framework.utils.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

public abstract class ChartsManagerBean extends GeneralBean implements Serializable  {  
	
	// Supported chart models
	protected HashMap<String, BaseChart> chartMap;
	
    public ChartsManagerBean() {  
    	
    	super();  	
    	chartMap = new HashMap<String, BaseChart>();
    	
    }  

    //*** Creation chart methods ***// 
    public abstract String refreshChartAction(String chartName);
    

    public void createOrUpdateChart(String chartName, LinkedHashMap<String, Number> chartValues) throws Exception {  
    
    	if(isChartPresent(chartName))
    		updateChart(chartName, chartValues);
    	else	
    		createChart(chartName, chartValues);
    	
    }
    
    public void createOrUpdateChart(String chartName, ArrayList<ChartSeries> chartValues) throws Exception {  
        
    	if(isChartPresent(chartName))
    		updateChart(chartName, chartValues);
    	else	
    		createChart(chartName, chartValues);
    	
    }
    
    public void createChart(String chartName, LinkedHashMap<String, Number> chartValues) throws Exception {  
	
    	BaseChart chart = new BaseChart();  
    	
    	chart.setChartName(chartName);
    	
		PieChartModel pieModel = new PieChartModel();
		pieModel.setData(chartValues);
    		  
		chart.setChartModel(pieModel);
		chart.setChartType(Constants.CHAR_TYPE_PIE);
		
        addChart(chartName, chart);
       
    }
    
    public void createChart(String chartName, ArrayList<ChartSeries> chartValues) throws Exception {  
    	
    	BaseChart chart = new BaseChart();  
    	
    	chart.setChartName(chartName);
    	
    	CartesianChartModel cartesianModel = createCartesianChartModelByValues(chartValues);
    	
    	chart.setChartModel(cartesianModel);
		chart.setChartType(Constants.CHAR_TYPE_CARTESIAN);
		
        addChart(chartName, chart);
       
    }

    public void updateChart(String chartName, LinkedHashMap<String, Number> chartValues) throws Exception {  
    	
    	BaseChart chart = new BaseChart();  
    	
    	chart.setChartName(chartName);
    	
		PieChartModel pieModel = new PieChartModel();
		pieModel.setData(chartValues);
    		  
		chart.setChartModel(pieModel);
		chart.setChartType(Constants.CHAR_TYPE_PIE);
		
		chartMap.put(chartName, chart);
       
    }
    
    public void updateChart(String chartName, ArrayList<ChartSeries> chartValues) throws Exception {  
    	
    	BaseChart chart = new BaseChart();  
    	
    	chart.setChartName(chartName);
    	
    	CartesianChartModel cartesianModel = createCartesianChartModelByValues(chartValues);
    	
    	chart.setChartModel(cartesianModel);
		chart.setChartType(Constants.CHAR_TYPE_CARTESIAN);
		
		chartMap.put(chartName, chart);
       
    }
    
    public void addChart(String chartName, BaseChart chart) throws Exception {
    	
    	if(chartMap.containsKey(chartName))
    		throw new Exception("BaseChart name " + chartName + " is already in use");
    	else	
    		chartMap.put(chartName, chart);
    	
    }
 
    public BaseChart getChart(String chartName) {

    	return chartMap.get(chartName);
    	
    }
    
    public ChartModel getChartModel(String chartName) {

    	return chartMap.get(chartName).getChartModel();
    	
    }
  
    // Set all ChartSeries objs into model
    public CartesianChartModel createCartesianChartModelByValues(ArrayList<ChartSeries> chartSeriesValues) {
    	
    	CartesianChartModel cartesianChartModel = new CartesianChartModel(); 
    	
    	for(ChartSeries chartSeries : chartSeriesValues)
    		cartesianChartModel.addSeries(chartSeries);
   
    	return cartesianChartModel;
    			
    }
    
    public boolean isChartPresent(String chartName) {
    	
    	if(chartMap.get(chartName) != null)
    		return true;
    	else
    		return false;
    	
    }
     
	public HashMap<String, BaseChart> getChartMap() {
		return chartMap;
	}

	public void setChartMap(HashMap<String, BaseChart> chartMap) {
		this.chartMap = chartMap;
	}
    
}  
