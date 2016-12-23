package com.hf.xml.jdom;

import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


public class JdomDemo {
	
	public static void main(String[] args) throws JDOMException, IOException {
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(JdomDemo.class.getResourceAsStream("/xml/dom/book.xml"));
		Element rootElement = document.getRootElement();
		List<Element> childrenElements = rootElement.getChildren();
	}
}
