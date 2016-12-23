
package com.hf.ws.jdk.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.hf.ws.jdk.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DoSomethingResponse_QNAME = new QName("http://jdk.ws.hf.com/", "doSomethingResponse");
    private final static QName _Value_QNAME = new QName("http://hf.jdk.ws/", "value");
    private final static QName _DoSomething2_QNAME = new QName("http://jdk.ws.hf.com/", "doSomething2");
    private final static QName _DoSomething_QNAME = new QName("http://jdk.ws.hf.com/", "doSomething");
    private final static QName _DoSomething2Response_QNAME = new QName("http://jdk.ws.hf.com/", "doSomething2Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.hf.ws.jdk.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DoSomething }
     * 
     */
    public DoSomething createDoSomething() {
        return new DoSomething();
    }

    /**
     * Create an instance of {@link DoSomething2Response }
     * 
     */
    public DoSomething2Response createDoSomething2Response() {
        return new DoSomething2Response();
    }

    /**
     * Create an instance of {@link DoSomethingResponse }
     * 
     */
    public DoSomethingResponse createDoSomethingResponse() {
        return new DoSomethingResponse();
    }

    /**
     * Create an instance of {@link DoSomething2 }
     * 
     */
    public DoSomething2 createDoSomething2() {
        return new DoSomething2();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomethingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jdk.ws.hf.com/", name = "doSomethingResponse")
    public JAXBElement<DoSomethingResponse> createDoSomethingResponse(DoSomethingResponse value) {
        return new JAXBElement<DoSomethingResponse>(_DoSomethingResponse_QNAME, DoSomethingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://hf.jdk.ws/", name = "value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomething2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jdk.ws.hf.com/", name = "doSomething2")
    public JAXBElement<DoSomething2> createDoSomething2(DoSomething2 value) {
        return new JAXBElement<DoSomething2>(_DoSomething2_QNAME, DoSomething2 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomething }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jdk.ws.hf.com/", name = "doSomething")
    public JAXBElement<DoSomething> createDoSomething(DoSomething value) {
        return new JAXBElement<DoSomething>(_DoSomething_QNAME, DoSomething.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DoSomething2Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jdk.ws.hf.com/", name = "doSomething2Response")
    public JAXBElement<DoSomething2Response> createDoSomething2Response(DoSomething2Response value) {
        return new JAXBElement<DoSomething2Response>(_DoSomething2Response_QNAME, DoSomething2Response.class, null, value);
    }

}
