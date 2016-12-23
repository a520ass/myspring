package com.hf.config.custom.view;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.google.common.collect.Sets;

public class ExcelView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//....处理object数据
		Set<Entry<String, Object>> entrySet = model.entrySet();
		int rowNum = 1;
		for (Entry<String, Object> entry : entrySet) {
			Sheet sheet = workbook.createSheet(entry.getKey());
			//entry.getValue()
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue("第一列第一个数据");
			row.createCell(1).setCellValue("第二列第一个数据");
		}
	}

}
