package com.hf.config.custom.csrf;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public final class CsrfTokenManager {

    // 隐藏域参数名称

    static final String CSRF_PARAM_NAME = "CSRFToken";

    // session中csrfToken参数名称

    public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CsrfTokenManager.class.getName() + ".tokenval";

    private CsrfTokenManager() {

    }

    // 在session中创建csrfToken
    public static String createTokenForSession(HttpSession session) {
        String token = null;
        synchronized (session) {
            token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }
}
