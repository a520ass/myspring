package com.hf.office;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Maps;
import org.junit.Test;

public class PoiTest {


	@Test
	public void test1() throws IOException {
		InputStream s = new FileInputStream("C:\\Users\\krt\\Documents\\Tencent Files\\280202540\\FileRecv\\1号令集体土地房屋征收及表格及协议2.13\\1号令集体土地房屋征收及表格及协议2.12\\房屋表(最终).xls") ;//new File(pathname).get;
		Workbook workbook=new HSSFWorkbook(s);
		Sheet sheet1 = workbook.getSheetAt(4);//表一
		Row row = sheet1.getRow(1);
		Cell cell = row.getCell(0);
		//System.out.println(cell.getStringCellValue());
		/*cell.setCellValue("赣州经开区某某xxx项目房屋安置人口认定表（表一）");
		Cell cell20 = sheet1.getRow(2).getCell(0);
		cell20.setCellValue("被征迁人姓名：何锋  潭东镇  桥兰村  组 2017年 9月 1日");*/
		//insertRow(sheet1,6,2);
		workbook.write(new FileOutputStream("C:\\Users\\krt\\Documents\\Tencent Files\\280202540\\FileRecv\\1号令集体土地房屋征收及表格及协议2.13\\1号令集体土地房屋征收及表格及协议2.12\\房屋表(最终)-new.xls"));
	}
	
	public static void main(String[] args) throws IOException {
		InputStream s = new FileInputStream("d:/Users/520/Documents/Tencent Files/280202540/FileRecv/408 薪资发放单--需OA审批-161206.xlsx") ;//new File(pathname).get;
		Workbook workbook=new XSSFWorkbook(s);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(2);
		Cell cell = row.getCell(0);
		System.out.println(cell.getStringCellValue());
		
		Map<String, Cell> maphead = new HashMap<>();
		
		List<HashMap<String, Cell>> listMaps1 = new ArrayList<HashMap<String,Cell>>();
		
		//smaphead.
		
		
		
		//获取页签所在的行数
		int basic=0;
		int yff=0;
		int dkmx=0;
		for(int i=0;i<sheet.getLastRowNum();i++){
			String string = sheet.getRow(i).getCell(0).getStringCellValue();
			if("基本信息".equals(string)){
				basic=i;
				continue;
			}
			if("应发放与代发放明细页签".equals(string)){
				yff=i;
				continue;
			}
			if("代扣明细页签".equals(string)){
				dkmx=i;
				break;
			}
		}
		
		System.out.println("基本信息"+basic+"应发放与代发放明细页签"+yff+"代扣明细页签"+dkmx);		//处于第几行 从0开始计算
		
		//解析表头数据
		for(int i=basic+1;i<yff;i++){
			Row rowi = sheet.getRow(i);
			short lastCellNum = rowi.getLastCellNum();
			for(int j=0;i<lastCellNum;j=j+2){
				/*if(StringUtils.isBlank(rowi.getCell(j).getStringCellValue())&&StringUtils.isBlank(rowi.getCell(j+1).getStringCellValue())){
					break;
				}*/
				if(j+1>lastCellNum){	//防止null指针异常
					break;	//结束当前for循环，这里已经到了某行的末尾列
				}
				String currentCellValue = rowi.getCell(j).getStringCellValue();	//key
				if(StringUtils.isNotBlank(currentCellValue)){
					maphead.put(currentCellValue, rowi.getCell(j+1));	//key value
				}else{
					continue;
				}
				
			}
		}
		
		System.out.println(maphead);
		
		//解析 应发放与代发放明细页签
		for(int i=yff+1;i<dkmx-2;i++){	//应发及代发小计 不知道属于哪里，先过滤掉
			HashMap<String, Cell> hashMap = Maps.newHashMap();
			for(int j=0;j<sheet.getRow(i).getLastCellNum();j++){
				hashMap.put(sheet.getRow(yff+1).getCell(j).getStringCellValue(), sheet.getRow(i+1).getCell(j));
			}
			listMaps1.add(hashMap);
		}
		
		System.out.println(listMaps1);
	}
}
