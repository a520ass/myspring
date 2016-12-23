package com.hf.config.custom.request;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class GetHttpServletRequestWrapper extends HttpServletRequestWrapper{
	
	private String encoding = "UTF-8";
	
	public GetHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	/**  
     * 获得被装饰对象的引用和采用的字符编码  
     * @param request  
     * @param charset  
     */  
    public GetHttpServletRequestWrapper(HttpServletRequest request,   
            String encoding) {   
        super(request);   
        this.encoding = encoding;   
    }   
  
    /**  
     * 实际上就是调用被包装的请求对象的getParameter方法获得参数，然后再进行编码转换  
     */  
    public String getParameter(String name) {   
        String value = super.getParameter(name);   
        value = value == null ? null : convert(value);   
        return value;   
    }   
  
    private String convert(String target) {   
        /*try {   
        	String fis = URLDecoder.decode(target, "gb2312");
            String sec = new String(fis.getBytes("gb2312"), "gb2312");
            if (fis.equals(sec))
                return fis;
            else {
                return URLDecoder.decode(code, "utf-8");
            }
            return new String(target.trim().getBytes("ISO-8859-1"), encoding);   
        } catch (UnsupportedEncodingException e) {   
            return target;   
        }   */
    	return target; 
    }   

}
