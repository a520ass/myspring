package com.hf.xml.sax;

import java.io.IOException;
import java.io.InputStream;

//JAXP
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
//SAX
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * SAX是一个解析速度快并且占用内存少的xml解析器，非常适合用于Android等移动设备。 
 * SAX解析XML文件采用的是事件驱动，也就是说，它并不需要解析完整个文档，在按内容顺序解析文档的过程中，SAX会判断当前读到的字符是否合法XML语法中的某部分，如果符合就会触发事件。
 * 所谓事件，其实就是一些回调（callback）方法，这些方法(事件)定义在ContentHandler接口
 * @author 520
 *
 */
public class SaxHandler extends DefaultHandler {
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// 1.实例化SAXParserFactory对象  默认使用jdk自带的
		//(jdk自带的实现com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl
        //SAXParserFactory factory = SAXParserFactory.newInstance(); 
		//apache xerces实现
        SAXParserFactory factory = SAXParserFactory.newInstance("org.apache.xerces.jaxp.SAXParserFactoryImpl",null); 
        
        // 2.创建解析器 
        SAXParser parser = factory.newSAXParser(); 
        // 3.获取需要解析的文档，生成解析器,最后解析文档 
       // File f = new File("books.xml"); 
        InputStream inputStream = SaxHandler.class.getResourceAsStream("/xml/dom/book.xml");
        SaxHandler dh = new SaxHandler(); 
        parser.parse(inputStream, dh); 
	}

	@Override
	public void startDocument() throws SAXException {
		System.out.println("开始解析");
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		System.out.println(s);
	}

	@Override
	public void endDocument() throws SAXException {
		System.out.println("\n结束解析");
	}
	
	/**
	 * 当读到一个开始标签的时候，会触发这个方法。
	 * namespaceURI就是命名空间，localName是不带命名空间前缀的标签名，qName是带命名空间前缀的标签名。通过atts可以得到所有的属性名和相应的值。
	 * 要注意的是SAX中一个重要的特点就是它的流式处理，当遇到一个标签的时候，它并不会纪录下以前所碰到的标签，也就是说，在startElement()方法中，所有你所知道的信息，就是标签的名字和属性，至于标签的嵌套结构，上层标签的名字，是否有子元属等等其它与结构相关的信息，都是不得而知的，都需要你的程序来完成。这使得SAX在编程处理上没有DOM来得那么方便。 
	 */
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		System.out.print("<");
		System.out.print(qName);

		if (attributes != null) {
			for (int i = 0; i < attributes.getLength(); i++) {
				System.out.print(" " + attributes.getQName(i) + "=\""
						+ attributes.getValue(i) + "\"");
			}
		}
		System.out.print(">");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.print("</");
		System.out.print(qName);
		System.out.print(">");
	}
	
	/**
	 * 这个方法用来处理在XML文件中读到的内容，第一个参数为文件的字符串内容，后面两个参数是读到的字符串在这个数组中的起始位置和长度，使用new String(ch,start,length)就可以获取内容
	 */
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		System.out.print(new String(ch, start, length));
	}

}
