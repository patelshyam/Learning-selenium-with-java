package io;

import java.io.File;  
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import UI.UIManager;
import base.KidsInfo;  

public class ExcelOperation {

	public static List<KidsInfo> getKidsInfos() {
		
		List<KidsInfo> lstkidInfo = new ArrayList<KidsInfo>();
		int i = 0;
		String mobile = null,pname = null,kname = null,gender = null,url = null;
		
		try {
			String excelFileAddress = UIManager.excelPath.getText();
			FileInputStream fis=new FileInputStream(new File(excelFileAddress));
			XSSFWorkbook wb=new XSSFWorkbook(fis);
			XSSFSheet sheet=wb.getSheetAt(0);
			FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();
			
			for(Row row: sheet)
			{
				for(Cell cell:row)
				{
					String temp = "";
					switch(cell.getCellType())
					{
						case Cell.CELL_TYPE_NUMERIC:
							temp = String.valueOf((long)cell.getNumericCellValue());
							break;
						case Cell.CELL_TYPE_STRING:
							temp = cell.getStringCellValue();
							break;
					}
					
					switch(i){
						case 0:
							mobile = temp;
							break;
						case 1:
							kname = temp;
							break;
						case 2:
							pname = temp;
							break;
						case 3:
							gender = temp;
							break;
						case 4:
							url = temp;
							break;
					}
					i++;
					
				}
				
				i = 0;
				lstkidInfo.add(new KidsInfo(mobile,pname,kname,url,gender));
				
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ExcelOperation.filterList((ArrayList<KidsInfo>) lstkidInfo);
		
	
		
		
	}

	public static List<KidsInfo> filterList(ArrayList<KidsInfo> temp)
	{
		List<KidsInfo> finalLst = new ArrayList<KidsInfo>();
		for(KidsInfo k : temp)
		{
			try {
				Long tempint = Long.parseLong(k.getMobileno());
				finalLst.add(k);
			}catch(Exception ex)
			{
				
			}
		}
		
		return finalLst;
	}
}
