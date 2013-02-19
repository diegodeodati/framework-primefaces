package it.betplus.web.framework.utils;

import it.betplus.web.framework.frontend.beans.charts.BaseChart;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;

public class CollectionUtils {

	
	/* Generate a list of SelectItem objects with ID and DESCRIPTION from an ArrayList of OBJS
	/* Id filed can be an object Label Filed must be a String
	 */
	public static <T> List<SelectItem> generateSelectItemList(ArrayList<T> objList, String idFieldname, String labelFieldName) throws NoSuchFieldException, SecurityException, IllegalAccessException {
		
		List<SelectItem> itemList = new ArrayList<SelectItem>();
		
		for(Object obj : objList) {
				
			Class<? extends Object> c = obj.getClass();

			Field idField = c.getDeclaredField(idFieldname);	
			idField.setAccessible(true);
			
			Field descField = c.getDeclaredField(labelFieldName);
			descField.setAccessible(true);
			
			Object idValue = (Object) idField.get(obj);			
			String descValue = (String) descField.get(obj);
			
			itemList.add(new SelectItem(idValue, descValue));
		    
		}		
		
		return itemList;
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> HashMap<Object, T> generateHashMapFromList(ArrayList<T> objList, String keyFieldName) throws IllegalAccessException, NoSuchFieldException {
		
		HashMap<Object, T> returnMap = new HashMap<Object, T>();
		
		for(Object obj : objList) {
			
			Class<? extends Object> c = obj.getClass();

			Field idField = c.getDeclaredField(keyFieldName);	
			idField.setAccessible(true);
			
			returnMap.put((Object) idField.get(obj), (T)obj);
			
		}
		
		return (HashMap<Object, T>) returnMap;
				
	}
		
	public static void main(String[] args) {
		
		BaseChart chart = new BaseChart();
		chart.setChartType("pippo");
		
		ArrayList<BaseChart> listchart = new ArrayList<BaseChart>();
		
		listchart.add(chart);
		
		try {
			
			HashMap<Object, BaseChart> chartmap = (HashMap<Object, BaseChart>) generateHashMapFromList(listchart, "chartType");
			
			for(BaseChart bc : chartmap.values()) {
				
				System.out.println(bc.getChartType());
				
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
