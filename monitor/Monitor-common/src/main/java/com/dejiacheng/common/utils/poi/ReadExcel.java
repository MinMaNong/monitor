package com.dejiacheng.common.utils.poi;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * @author pcy
 * 读取xml文件
 * @version 创建时间:2019年9月24日-下午9:17:26
 */
public class ReadExcel {
 
    public static void main(String[] args) {
	readExcel("D:\\dahua\\dahuaexception.xlsx");
    }
    
	static HashMap<String, String> dahua = new HashMap<>();
	private static String keyValue;
 
    public static HashMap<String, String> readExcel(String path) {
	File file = new File(path);
	FileInputStream fis = null;
	Workbook workBook = null;
	if (file.exists()) {
	    try {
		fis = new FileInputStream(file);
		workBook = WorkbookFactory.create(fis);
		int numberOfSheets = workBook.getNumberOfSheets();
		// sheet工作表
		for (int s = 0; s < numberOfSheets; s++) {
		    Sheet sheetAt = workBook.getSheetAt(s);
		    //获取工作表名称
//		    String sheetName = sheetAt.getSheetName();
//		    System.out.println("工作表名称：" + sheetName);
		    // 获取当前Sheet的总行数
		    int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
		    System.out.println("当前表格的总行数:" + rowsOfSheet);
		    // 第一行
		    Row row0 = sheetAt.getRow(0);
		    int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();
		    String[] title = new String[physicalNumberOfCells];
		    for (int i = 0; i < physicalNumberOfCells; i++) {
		        title[i] = row0.getCell(i).getStringCellValue();
		    }
		    for (int r = 1; r < rowsOfSheet; r++) {
		        Row row = sheetAt.getRow(r);
			if (row == null) {
			    continue;
		        } else {
			    int rowNum = row.getRowNum() + 1;
//			    System.out.println("当前行:" + rowNum);
			    // 总列(格)
			    Cell cell0 = row.getCell(0);
			    Cell cell1 = row.getCell(1);
			    Cell cell2 = row.getCell(2);
//			    Cell cell3 = row.getCell(3);
//			    Cell cell4 = row.getCell(4);
			    if(rowNum < rowsOfSheet) {
			    	if ((cell0.getCellTypeEnum() == CellType.NUMERIC) && (!DateUtil.isCellDateFormatted(cell0))) {
			    		String numericCellValue = (int) cell0.getNumericCellValue()+"";
//			    		System.out.println(numericCellValue);
			    		keyValue = numericCellValue;
			    	} else {
			    		System.out.println("第" + rowNum + "行，第一列[" + title[0] + "]数据错误！");
			    	}
			    	if (cell1.getCellTypeEnum() == CellType.STRING) {
			    		String stringCellValue = cell1.getStringCellValue();
//			    		System.out.println(stringCellValue);
			    	} else {
//			    		System.out.println("第" + rowNum + "行，第二列[" + title[1] + "]数据错误！");
			    	}
			    	if (cell2.getCellTypeEnum() == CellType.STRING) {
			    		String stringCellValue = cell2.getStringCellValue();
//			    		System.out.println(stringCellValue);
			    		dahua.put(keyValue, stringCellValue);
			    	} else {
//			    		System.out.println("第" + rowNum + "行，第三列[" + title[2] + "]数据错误！");
			    	}
			    }else {
//			    	System.out.println("读取结束！！！！！");
//			    	System.out.println(dahua.toString());
			    	return dahua;
			    }
//			    if ((cell3.getCellTypeEnum() == CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell3)) {
//			        Date dateCellValue = cell3.getDateCellValue();
//			        System.out.println(sdf.format(dateCellValue));
//			    } else {
//			        System.out.println("第" + rowNum + "行，第四列[" + title[3] + "]数据错误！");
//			    }
//			    if ((cell4.getCellTypeEnum() == CellType.NUMERIC) && (!DateUtil.isCellDateFormatted(cell4))) {
//			        double numericCellValue = cell4.getNumericCellValue();
//			        System.out.println(numericCellValue);
//			    } else {
//		                System.out.println("第" + rowNum + "行，第五列[" + title[4] + "]数据错误！");
//			    }
		        }
		    }
	        }
	        if (fis != null) {
		    fis.close();
	        }
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
        } else {
	    System.out.println("文件不存在!");
        }
		return dahua;
    }
}