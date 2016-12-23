package org.springframework.remoting.thrift;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;

public class ThriftHttpServiceExporter extends ThriftExporter implements
		HttpRequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!"POST".equals(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(
					request.getMethod(), new String[] { "POST" },
					"ThriftServiceExporter only supports POST requests");
		}
		ServletInputStream in = request.getInputStream();
		ServletOutputStream out = response.getOutputStream();
		try {
			response.setContentType(CONTENT_TYPE_THRIFT);
			invoke(in, out);
		} catch (Exception e) {
			response.setContentType("text/plain; charset=UTF-8");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			e.printStackTrace(new PrintWriter(out, true));
			if (logger.isErrorEnabled()) {
				logger.error("Thrift server direct error", e);
			}
		}
	}
}
