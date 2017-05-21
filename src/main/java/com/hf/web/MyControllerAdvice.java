package com.hf.web;


import com.hf.utils.web.WebUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler{

	private static Logger log = LoggerFactory
			.getLogger(MyControllerAdvice.class);

	@ModelAttribute
	public void sysData(Model model) {
		// 应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model
		model.addAttribute("sysroot", "myspring");
	}

	/*@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ResponseEntity<Object> objectResponseEntity = super.handleExceptionInternal(ex, body, headers, status, request);
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		if (isAjax(servletWebRequest.getRequest())) {
			log.error(servletWebRequest.getRequest().getRequestURI() + " Ajax调用发生错误:"
					+ ex.getLocalizedMessage());
		}else{
			log.error(servletWebRequest.getRequest().getRequestURI() + " 链接访问发生错误:"
					+ ex.getLocalizedMessage());
		}
		return objectResponseEntity;
	}*/

	/**
	 * ExceptionHandlerExceptionResolver 用来处理
	 * 处理 Handler 中用 @ExceptionHandler 注解定义的方法。
	 * @ExceptionHandler 注解定义的方法优先级问题：例如发生的是NullPointerException，但是声明的异常有RuntimeException 和 Exception，此候会根据异常的最近继承关系找到继承深度最浅的那个 @ExceptionHandler注解方法，即标记了 RuntimeException 的方法
	 * ExceptionHandlerMethodResolver 内部若找不到 @ExceptionHandler 注解的话，会找 @ControllerAdvice 中的 @ExceptionHandler 注解方法
	 *
	 * 默认的异常处理器有如下
	 * <li>{@link ExceptionHandlerExceptionResolver}
	 * for handling exceptions through @{@link ExceptionHandler} methods.
	 * <li>{@link ResponseStatusExceptionResolver}
	 * for exceptions annotated with @{@link ResponseStatus}.
	 * <li>{@link DefaultHandlerExceptionResolver}
	 * for resolving known Spring exception types
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Exception> processException(Exception e, HttpServletRequest request) {
		if (WebUtils.isAjax(request)) {
			log.error(request.getRequestURI() + " Ajax调用发生错误:"
					+ e.getLocalizedMessage(),e);
		}else{
			log.error(request.getRequestURI() + " 链接访问发生错误:"
					+ e.getLocalizedMessage(),e);
		}

		return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class,new CustomDataEscapeEditor(true));
	}

}
class CustomDataEscapeEditor extends PropertyEditorSupport{
	private final boolean allowEmpty;


	public CustomDataEscapeEditor(boolean allowEmpty){
		this.allowEmpty=allowEmpty;
	}

	/**
	 * 从字符串 ==> 想要的类型
	 * @param text
	 * @throws IllegalArgumentException
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else {
			String value = StringEscapeUtils.escapeHtml4(text);
			value = org.apache.commons.lang.StringEscapeUtils.escapeJavaScript(value);
			setValue(value);
		}
	}

	/**
	 * 把要转换的类型  ==> 字符串
	 * @return
	 */
	@Override
	public String getAsText() {
		Object value = getValue();
		return (value != null ? value.toString(): "");
	}

}

