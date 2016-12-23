package com.hf.xml.dom;

import java.util.ArrayList;
import java.util.List;
//JAXP
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//DOM
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.hf.xml.XmlDocument;

public class DomDemo implements XmlDocument{
	
	public static void main(String[] args) {
		new DomDemo().parserXml(DomDemo.class.getResource("/xml/dom/book.xml").getPath());
	}

	@Override
	public void createXml(String fileName) {
		// TODO Auto-generated method stub
	}

	@Override
	public void parserXml(String fileName) {
		try {
			List<Book> list = new ArrayList<Book>();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(fileName);
			Element element = document.getDocumentElement(); 
			 
	        NodeList bookNodes = element.getElementsByTagName("book"); 
	        for(int i=0;i<bookNodes.getLength();i++){ 
	            Element bookElement = (Element) bookNodes.item(i); 
	            Book book = new Book(); 
	            book.setId(Integer.parseInt(bookElement.getAttribute("id"))); 
	            NodeList childNodes = bookElement.getChildNodes(); 
//	          System.out.println("*****"+childNodes.getLength()); 
	            for(int j=0;j<childNodes.getLength();j++){ 
	                if(childNodes.item(j).getNodeType()==Node.ELEMENT_NODE){ 
	                    if("name".equals(childNodes.item(j).getNodeName())){ 
	                        book.setName(childNodes.item(j).getFirstChild().getNodeValue()); 
	                    }else if("price".equals(childNodes.item(j).getNodeName())){ 
	                        book.setPrice(Float.parseFloat(childNodes.item(j).getFirstChild().getNodeValue())); 
	                    } 
	                } 
	            }//end for j 
	            list.add(book); 
	        }//end for i 
	        System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
