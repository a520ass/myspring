package com.hf.config.custom.interceptor;

import com.hf.config.custom.csrf.CsrfTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashSet;

public class CsrfInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory
            .getLogger(CsrfInterceptor.class);

    private final HashSet<String> allowedMethods = new HashSet<String>(
            Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String s = request.getMethod().toUpperCase();
        if (!allowedMethods.contains(s)) {
            String CsrfToken = CsrfTokenManager.getTokenFromRequest(request);
            if (CsrfToken == null
                    || !CsrfToken.equals(request.getSession().getAttribute(
                    CsrfTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
                /*String reLoginUrl = "/login?backurl="
                        + URLEncoder.encode(getCurrentUrl(request), "utf-8");
                response.sendRedirect(reLoginUrl);*/
                return false;
            }
        }
        return true;
    }

}
