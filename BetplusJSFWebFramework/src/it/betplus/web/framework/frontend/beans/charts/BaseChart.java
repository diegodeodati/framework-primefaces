package it.betplus.web.framework.frontend.beans.charts;

import java.io.Serializable;

import org.primefaces.model.chart.ChartModel;

public class BaseChart implements Serializable {

	private static final long serialVersionUID = -4875003756217315198L;
	
	private String chartName;
	private ChartModel chartModel;
	private String chartType;
	
	public BaseChart() {
		
	}
	
	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public ChartModel getChartModel() {
		return chartModel;
	}
	
	public void setChartModel(ChartModel chartModel) {
		this.chartModel = chartModel;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

}
