package org.springframework.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class Slf4jRequestLoggingFilter extends AbstractRequestLoggingFilter {
	
	private static Logger logger=LoggerFactory.getLogger(Slf4jRequestLoggingFilter.class);
	
	@Override
	protected boolean isIncludeQueryString() {
		return true;
	}

	@Override
	protected boolean isIncludeClientInfo() {
		return true;
	}

	@Override
	public boolean isIncludeHeaders() {
		return true;
	}

	@Override
	protected boolean isIncludePayload() {
		return true;
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		logger.info(message);
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		logger.info(message);
	}

}
