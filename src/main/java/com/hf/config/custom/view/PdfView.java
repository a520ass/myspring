package com.hf.config.custom.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfViewFix;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfView extends AbstractPdfViewFix{

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

}
